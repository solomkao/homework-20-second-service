package com.solomka.secondservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String name;
    private List<Author> authors = new ArrayList<>();
}
