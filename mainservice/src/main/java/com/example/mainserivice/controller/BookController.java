package com.example.mainserivice.controller;

import com.example.mainserivice.entity.Book;
import com.example.mainserivice.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    // 获取所有书籍信息的接口
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        final Logger log = LoggerFactory.getLogger(BookController.class);
        log.info("Querying Books");
        return bookService.getAllBooks();
    }

    // 根据id获取书籍信息的接口

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable("id") int id) {
        // 在books中查找id匹配的书籍
        return bookService.findBookById(id);
    }
    @PostMapping("/books/deleteOneBook/{id}")
    public  List<Book>  deleteOneBook(@PathVariable("id") int id){
        final Logger log = LoggerFactory.getLogger(BookController.class);
        return bookService.deleteOneBook(id);
    }
    @PostMapping("/books/insertNewBook")
    public Book insertNewBook(@RequestBody Map<String, String> params){

        String name = params.get("name");
        String author = params.get("author");
        String isbn = params.get("isbn");

        Integer inventory = Integer.valueOf(params.get("inventory"));
        Double price = Double.valueOf(params.get("price"));
        String description = params.get("description");
        String imageUrl = params.get("imageUrl");
        return bookService.insertNewBook(name, author, isbn, inventory, price, description,imageUrl);
    }
    @PostMapping("/books/changeOnesInfo")
    public Book changeOnesInfo(@RequestBody Map<String, String> params){
        System.out.println("I'm controller in change info");
        Integer bookId = Integer.valueOf(params.get("bookId"));
        String imageUrl = params.get("image");
        String name = params.get("name");
        String author = params.get("author");
        String isbn = params.get("isbn");
        Integer inventory = Integer.valueOf(params.get("inventory"));
        Double price = Double.valueOf(params.get("price"));
        String description = params.get("description");
        return bookService.changeOnesInfo(bookId,name, author, isbn, inventory, price, description,imageUrl);
    }
}
