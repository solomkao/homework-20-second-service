package com.solomka.secondservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solomka.secondservice.models.Book;
import com.solomka.secondservice.models.BookDto;
import com.solomka.secondservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReaderController {

    private final RestTemplate restTemplate;

//    private final String url = "http://homework-20-library-service/books";
    private final String urlGetAllBooks = "http://localhost:8090/books";
    private final String urlGetAllReaders = "http://localhost:8090/users";

    @Autowired
    public ReaderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping(value="/readers/books")
    public List<Book>getAvailableBooks(){
        var response = restTemplate.getForEntity(urlGetAllBooks, Object[].class);
        Object[] objects = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<Book> books = Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, Book.class))
                .filter(book -> !book.isTaken())
                .collect(Collectors.toList());
        return books;
    }

    @GetMapping(value="/readers")
    public List<User>getAllReadersBooks(){
        var response = restTemplate.getForEntity(urlGetAllReaders, Object[].class);
        Object[] objects = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, User.class))
                .collect(Collectors.toList());
        return users;
    }

    @GetMapping(value="/readers/{id}")
    public List<Book>getReaderBooks(
            @PathVariable("id") String userId
    ){
        var response = restTemplate.getForEntity(urlGetAllReaders +"/"+ userId, Object[].class);
        Object[] objects = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<Book> books = Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, Book.class))
                .collect(Collectors.toList());
        return books;
    }

    @PostMapping(value="/readers/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book getReaderBooks(
            @PathVariable("id") String userId,
            @RequestBody BookDto bookDto
            ){
        var response = restTemplate.postForEntity(urlGetAllReaders +"/"+ userId, bookDto, Object.class);
        Object object = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.convertValue(object, Book.class);
        return book;
//        return "OK";
    }
}
