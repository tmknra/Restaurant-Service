package com.example.hw_5.entity;

import java.util.Objects;

public class Restaurant {
    private Long id;
    private String name;
    private String description;
    private String phone_number;
    private String email_address;


    public Restaurant() {
    }

    public Restaurant(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Restaurant(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Restaurant(String name, String description, String phone_number, String email_address) {
        this.name = name;
        this.description = description;
        this.phone_number = phone_number;
        this.email_address = email_address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
