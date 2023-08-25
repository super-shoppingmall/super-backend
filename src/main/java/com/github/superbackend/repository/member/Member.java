package com.github.superbackend.repository.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String email;
    private String password;
    private Integer phone; // String으로 받는게 나을것같은데..
    private String address;
    private String gender;
    private String aboutMe;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
