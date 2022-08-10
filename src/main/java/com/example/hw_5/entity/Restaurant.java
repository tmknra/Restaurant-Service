package com.example.hw_5.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String phone_number;

    @Column
    private String email_address;

    @Column
    private LocalDate foundation_date;


    // public Restaurant(String name, String description) {
    //     this.name = name;
    //     this.description = description;
    // }
    //
    // public Restaurant(String name, String description, LocalDate foundation_date) {
    //     this.name = name;
    //     this.description = description;
    //     this.foundation_date = foundation_date;
    // }
    //
    // public Restaurant(String name, String description, String phone_number, String email_address, LocalDate foundation_date) {
    //     this.name = name;
    //     this.description = description;
    //     this.phone_number = phone_number;
    //     this.email_address = email_address;
    //     this.foundation_date = foundation_date;
    // }

}
