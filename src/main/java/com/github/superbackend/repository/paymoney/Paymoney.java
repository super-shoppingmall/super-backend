package com.github.superbackend.repository.paymoney;

import com.github.superbackend.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Paymoney {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long paymoneyId;

     @ManyToOne
     @JoinColumn(name = "member_id")
     private Member member;

     private Integer paymoney;
}
