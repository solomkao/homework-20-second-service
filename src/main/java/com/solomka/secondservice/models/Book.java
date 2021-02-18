package com.solomka.secondservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long bookId;

    private String name;

    private List<Author> authors;

    private int yearOfPublication;

    private boolean taken;

}
