package com.master.practica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.master.practica.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.user.id = ?1")
    List<Review> findByUser(long id);
    
    @Modifying
    @Query(value = "DELETE FROM Review r WHERE r.id = :id")
    int deleteById(@Param("id") long id);
}
