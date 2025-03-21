package com.example.newsfeed.domain.user.entity;

import com.example.newsfeed.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "users")
@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String username;
    @Column
    private String address;

    public User(String password, String email, String username, String address) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.address = address;
    }
    public void updatePassword(String password) {
        this.password = password;
    }
}
