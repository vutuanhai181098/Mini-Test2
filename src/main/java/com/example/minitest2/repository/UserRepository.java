package com.example.minitest2.repository;

import com.example.minitest2.dto.UserDto;
import com.example.minitest2.entity.User;
import com.example.minitest2.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Method query
    Optional<User> findById(Long id);

    // JPQL Query

    @Query("select new com.example.minitest2.dto.UserDto(u.id, u.name, u.email) from User u where u.id = :id")
    UserDto getUserByIdJPQL(@Param("id") Long id);

    // Native Query
    @Query(nativeQuery = true, name = "getUserByIdNQ")
    UserDto getUserByIdNQ(Long id);

    // Projection
    <T> T findById(Long id, Class<T> type);

}
