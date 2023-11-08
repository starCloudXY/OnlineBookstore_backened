package com.example.authorservice.controller;

import com.example.authorservice.dto.ResultDTO;
import com.example.authorservice.entity.Book;
import com.example.authorservice.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class AuthorController {
    @Autowired
    BookRepository bookRepository;
    @GetMapping(value = "/getAuthorByBookName/{BookName}")
    public ResultDTO getAuthorByBookName(@PathVariable("BookName") String bookName){
        bookName = "%" + bookName + "%";
        Book book = bookRepository.getAuthorByBookName(bookName);
        String result = "查找到《"+book.getName()+"》的作者是"+book.getAuthor();
        return ResultDTO.success(result);
    }

}
