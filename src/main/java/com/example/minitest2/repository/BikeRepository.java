package com.example.minitest2.repository;

import com.example.minitest2.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Long> {
    @Query(nativeQuery = true, value = "select * from bike")
    List<Bike> getAllBike();
}
