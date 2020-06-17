package com.edu.cinebot.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie implements Serializable {
    private String image;
    private String name;
    private String description;
}
