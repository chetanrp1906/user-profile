package com.chetan.userprofile.user.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;


    @CreatedDate
    @Column(name = "insert_ts", updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "update_ts")
    private LocalDateTime updatedTime;

    @PrePersist
    public void setCreatedTime() {
        this.createdTime = LocalDateTime.now();
    }
}
