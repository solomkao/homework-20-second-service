package com.solomka.secondservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookDto {

    private String title;

    private List<Author> authors = new ArrayList<>();

    private int yearOfPublication;
}
