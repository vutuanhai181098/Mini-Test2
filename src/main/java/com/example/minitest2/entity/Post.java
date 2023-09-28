package com.example.minitest2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    private String id;
    private String title;

    // Constructor
    public Post(String title){
        this.id = generateUniqueId();
        this.title = title;
    }

    private String generateUniqueId(){
        return UUID.randomUUID().toString();
    }
    /*
    ---Sử dụng @PrePersist

    public Post(String title){
        this.title = title;
    }
    @PrePersist
    void beforeSave(){
        id = generateUniqueId();
    }
    */
}
