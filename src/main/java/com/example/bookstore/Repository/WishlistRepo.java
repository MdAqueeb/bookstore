package com.example.bookstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.Entities.User;
import com.example.bookstore.Entities.Wishlist;


@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Long> {

    @Query(value = "SELECT * FROM wishlist WHERE user = :userid AND book = :bookid", nativeQuery = true)
    List<Wishlist> findByUserBook(@Param("userid") long userid, @Param("bookid") long bookid);

    @Query(value = "SELECT * FROM wishlist WHERE user = :userid", nativeQuery = true)
    List<Wishlist> findByUserId(@Param("userid") long userid);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wishlist WHERE user = :userid AND book = :bookid", nativeQuery = true)
    void RemoveByEmailAndId(@Param("userid") long userid,@Param("bookid") long bookid);
    



}

