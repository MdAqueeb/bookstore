package com.example.bookstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookstore.Entities.SalesOverview;

@Repository
public interface SalesOverView extends JpaRepository<SalesOverview,Long> {

    @Query(value = "SELECT * FROM sales_overview WHERE sellerid = :userid", nativeQuery=true)
    SalesOverview findByUserid(@Param("userid") long userid);

    
}
