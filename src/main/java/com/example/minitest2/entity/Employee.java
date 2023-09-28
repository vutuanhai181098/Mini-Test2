package com.example.minitest2.entity;

import jakarta.persistence.*;
@NamedQuery(
        name = "getEmployeeById",
        query = "from Employee where id = ?1"
)
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
