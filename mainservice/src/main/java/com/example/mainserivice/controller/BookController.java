package com.example.mainserivice.controller;

import com.example.mainserivice.entity.Book;
import com.example.mainserivice.entity.BookTag;
import com.example.mainserivice.repository.BookTagRepository;
import com.example.mainserivice.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
    private BookTagRepository bookTagRepository;
    @QueryMapping
    public Book bookByName(@Argument String name) {
        return bookService.getAllBooks().stream().
                filter(
                book -> book.getName().equals(name)
        ).findFirst().
                orElse(null);
    }
    // 获取所有书籍信息的接口
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        final Logger log = LoggerFactory.getLogger(BookController.class);
        return bookService.getAllBooks();
    }

    // 根据id获取书籍信息的接口

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable("id") int id) {
        // 在books中查找id匹配的书籍
        return bookService.findBookById(id);
    }
    @GetMapping("/books/findRelatebyTags/{TagName}")
    public List<Book> findRelatebyTags(@PathVariable("TagName") String tagname) {
        // 在books中查找id匹配的书籍
        return bookService.findBooksByTagRelation(tagname);
    }
    @PostMapping("/books/deleteOneBook/{id}")
    public  List<Book>  deleteOneBook(@PathVariable("id") int id){
        final Logger log = LoggerFactory.getLogger(BookController.class);
        return bookService.deleteOneBook(id);
    }
    @RequestMapping("/neo4j/init")
    public String testNeo4j() {
        bookTagRepository.deleteAll();
        BookTag bk1 = new BookTag("人文");
        BookTag bk2 = new BookTag("社科");
        BookTag bk3 = new BookTag("传记");
        BookTag bk4 = new BookTag("哲学");
        BookTag bk5 = new BookTag("数学");
        BookTag bk6 = new BookTag("心理");
        BookTag bk7 = new BookTag("自传");
        bk1.addRelateBookTag(bk2);
        bk1.addRelateBookTag(bk4);
        bk2.addRelateBookTag(bk6);
        bk3.addRelateBookTag(bk7);
        bk4.addRelateBookTag(bk3);
        bk6.addRelateBookTag(bk5);
        bk7.addRelateBookTag(bk4);

        bk1.addBookID(1);
        bk2.addBookID(5);
        bk3.addBookID(2);
        bk4.addBookID(3);
        bk5.addBookID(6);
        bk6.addBookID(6);
        bk6.addBookID(7);
        bk7.addBookID(4);
        bookTagRepository.save(bk1);
        bookTagRepository.save(bk2);
        bookTagRepository.save(bk3);
        bookTagRepository.save(bk4);
        bookTagRepository.save(bk5);
        bookTagRepository.save(bk6);
        bookTagRepository.save(bk7);
        return "Init Successful!!\n";
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
