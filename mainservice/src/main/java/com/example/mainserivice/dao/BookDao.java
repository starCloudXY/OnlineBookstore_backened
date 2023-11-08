package com.example.mainserivice.dao;



import com.example.mainserivice.entity.Book;

import java.util.List;

public interface BookDao {
    Book changeInventory(Integer id, Integer amount);
    Book findbyID(int id);
    List<Book> getAllBooks();
    List<Book> deleteOneBook(Integer bookId);
    Book changeOnesInfo(Integer bookId,String name,String author,String isbn,Integer inventory,Double price,String description,String imageUrl);
    Book insertNewBook(String name,String author,String isbn,Integer inventory,Double price,String description,String imageUrl);
}
