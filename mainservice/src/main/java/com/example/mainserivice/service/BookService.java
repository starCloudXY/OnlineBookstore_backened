package com.example.mainserivice.service;


import com.example.mainserivice.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    Book findBookById(Integer id);
    List<Book>  deleteOneBook(Integer bookId);
    Book insertNewBook(String name,String author,String isbn,Integer inventory,Double price,String description,String imageUrl);
    public List<Book> findBooksByTagRelation(String tagName);
    List<Book> getAllBooks();
    Book changeOnesInfo(Integer bookId,String name,String author,String isbn,Integer inventory,Double price,String description,String imageUrl) ;

}