package com.example.bookstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.bookstore.Entities.RequestSellerRole;
import com.example.bookstore.Entities.User;

@Repository
public interface RequestSellerRepo extends JpaRepository<RequestSellerRole,Long>{

    @Query(value = "SELECT * FROM request_role WHERE userid = :userid ", nativeQuery = true)
    List<RequestSellerRole> findByUser(@Param("userid") long userid);
    
}
