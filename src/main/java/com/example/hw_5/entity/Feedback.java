package com.example.hw_5.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Long restaurantid;
    @Column
    private String feedback;
    @Column
    private Integer rating;

}

