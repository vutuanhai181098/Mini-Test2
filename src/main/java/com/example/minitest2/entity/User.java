package com.example.minitest2.entity;

import com.example.minitest2.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NamedNativeQuery(
        name = "getUserByIdNQ",
        query = "SELECT u.id, u.name, u.email FROM user u where u.id = ?1",
        resultClass = UserDto.class
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
}
