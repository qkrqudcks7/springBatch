package com.example.springbatch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pay {

    @Id @GeneratedValue
    private Long id;

    private Long amount;

    private String txName;

    private LocalDateTime localDateTime;
}
