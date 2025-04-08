package com.example.bookstore.Repository;

import com.example.bookstore.Entities.Books;
import com.example.bookstore.Entities.User;
import com.example.bookstore.Entities.User.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE name = :name", nativeQuery = true)
    User findByUserName(@Param("name") String name);

    @Query(value = "SELECT * FROM user WHERE email = :email AND password = :password", nativeQuery = true)
    User findByEmailAndPasswordNative(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM user WHERE role = :role",nativeQuery = true)
    Optional<List<User>> findByRole(@Param("role") User.Role role);
  
}

