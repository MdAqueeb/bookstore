package com.example.bookstore.Security;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Enumeration;

// import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.bookstore.Service.JWTService;
import com.example.bookstore.Service.UserService;
import com.example.bookstore.Service.UserdetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
// import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import jwtauthentication.application.Application;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Autowired
    @Lazy
    UserService user;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws IOException, ServletException{
        String authHeader = request.getHeader("Authorization");
        
        String token = "";
        String username = "";
        // System.out.println(request+" This is request"+request.getHeader(username));
        System.out.println("username");
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("Aqueeb");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + ":===> " + headerValue);
            // if(headerName.equals("authorization")){
            // }
        }

        System.out.println(authHeader);
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
            System.out.println("Yes token fount");
        }
        
        if(!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println("Not found the user");
            UserDetails userDetails = context.getBean(UserdetailService.class).loadUserByUsername(username);
            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authtoken  = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authtoken);
            }
            else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired please refresh your token.");
                return ;
            }
        }
        filterChain.doFilter(request, response);
    }   

}