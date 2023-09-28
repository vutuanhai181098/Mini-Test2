package com.example.minitest2.repository;

import com.example.minitest2.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // 1. Tìm tất cả các Employee theo emailAddress và lastName

    // Method query
    List<Employee> findByEmailAndLastName(String email, String lastName);

    // Native query
    @Query(nativeQuery = true, value = "Select * from employee e where e.email = :email and e.lastName = :lastName")
    List<Employee> getByEmailAndLastNameNQ(@Param("email") String email,@Param("lastName") String lastName);

    // JPQL query
    @Query("select e from Employee e where e.email = :email and e.lastName = :lastName")
    List<Employee> getByEmailAndLastNameJPQL(@Param("email") String email,@Param("lastName") String lastName);

    // 2. Tìm tất cả các Employee khác nhau theo firstName hoặc lastName
    // Method query
    List<Employee> findDistinctByFirstNameOrLastName(String firstName, String lastName);

    // Native query
    @Query(nativeQuery = true, value = "select distinct * from employee e where e.firstName = ?1 or e.lastName = ?2")
    List<Employee> getDistinctByFirstNameOrLastNameNQ(String firstName, String lastName);

    // JPQL query
    @Query("select distinct e from Employee e where e.firstName = ?1 or e.lastName = ?2")
    List<Employee> getDistinctByFirstNameOrLastNameJPQL(String firstName, String lastName);

    // 3. Tìm tất cả các Employee theo lastName và sắp xếp thứ tự theo firstName tăng dần
    // Method query
    List<Employee> findByLastNameOrOrderByFirstNameAsc(String lastName);

    // Native query
    @Query(nativeQuery = true, value = "select * from employee e where e.lastName = ?1 order by e.firstName asc")
    List<Employee> getByLastNameOrOrderByFirstNameAscNQ(String lastName);

    // JPQL query
    @Query("select e from Employee e where e.lastName = ?1 order by e.firstName asc")
    List<Employee> getByLastNameOrOrderByFirstNameAscJPQL(String lastName);

    // 4. Tìm tất cả các Employee theo fistName không phân biệt hoa thường.
    // Method query

    // Theo firstName
    List<Employee> findByFirstNameIgnoreCase(String firstName);
    // Chứa firstName
    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);
    // Native query
    @Query(nativeQuery = true, value = "select * from employee e where lower(e.firstName) = lower(?1)")
    List<Employee> getByFirstNameIgnoreCaseNQ(String firstName);
    @Query(nativeQuery = true, value = "select * from employee e where lower(e.firstName) like lower(?1)")
    List<Employee> getByFirstNameContainingIgnoreCaseNQ(String firstName);

    // JPQL query
    @Query("select e from Employee e where lower(e.firstName) = lower(?1)")
    List<Employee> getByFirstNameIgnoreCaseJPQL(String firstName);

    @Query("select e from Employee e where lower(e.firstName) like lower(?1)")
    List<Employee> getByFirstNameContainingIgnoreCaseJPQL(String firstName);

    // Named query
    Employee getEmployeeById(Long id);
}
