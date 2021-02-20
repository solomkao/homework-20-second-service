package com.solomka.secondservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solomka.secondservice.models.Book;
import com.solomka.secondservice.models.BookDto;
import com.solomka.secondservice.models.CreateBookDto;
import com.solomka.secondservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReaderController {

    private final RestTemplate restTemplate;

    private final String urlBooks = "http://localhost:8090/books";
    private final String urlUsers = "http://localhost:8090/users";

    @Autowired
    public ReaderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping(value="/library/books")
    public List<Book>getAvailableBooks(){
        var response = restTemplate.getForEntity(urlBooks, Object[].class);
        Object[] objects = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<Book> books = Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, Book.class))
                .filter(book -> !book.isTaken())
                .collect(Collectors.toList());
        return books;
    }

    @GetMapping(value="/library/readers")
    public List<User>getAllReaders(){
        var response = restTemplate.getForEntity(urlUsers, Object[].class);
        Object[] objects = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, User.class))
                .collect(Collectors.toList());
        return users;
    }

    @GetMapping(value="/library/readers/{id}")
    public ResponseEntity<List<Book>>getReaderBooks(
            @PathVariable("id") String userId
    ){
        var response = restTemplate.getForEntity(urlUsers +"/"+ userId, Object[].class);
        Object[] objects = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<Book> books = Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, Book.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping(value="/library/take/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book takeBook(
            @PathVariable("id") String userId,
            @RequestBody BookDto bookDto
            ){
        var response = restTemplate.postForEntity(urlUsers +"/"+ userId, bookDto, Object.class);
        Object object = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.convertValue(object, Book.class);
        return book;
    }

    @PostMapping(value="/library/return/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book returnBook(
            @PathVariable("id") String userId,
            @RequestBody BookDto bookDto
    ){
        var response = restTemplate.postForEntity(urlUsers +"/return/"+ userId, bookDto, Object.class);
        Object object = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.convertValue(object, Book.class);
        return book;
    }

    @PostMapping(value="/library/books",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book addBook(
            @RequestBody CreateBookDto createBookDto
    ){
        var response = restTemplate.postForEntity(urlBooks, createBookDto, Object.class);
        Object object = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.convertValue(object, Book.class);
        return book;
    }
}
