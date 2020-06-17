package com.edu.cinebot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
    private String image;
    private String name;
    private String description;
}
