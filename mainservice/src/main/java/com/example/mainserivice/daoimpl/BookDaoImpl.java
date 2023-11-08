package com.example.mainserivice.daoimpl;

import com.alibaba.fastjson.JSONArray;


import com.example.mainserivice.component.RedisUtil;
import com.example.mainserivice.dao.BookDao;
import com.example.mainserivice.entity.Book;
import com.example.mainserivice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Book findbyID(int id){
        Book book;
        Object o = redisUtil.get("book"+id);
        if(o == null){
            book = bookRepository.getReferenceById(id);
            redisUtil.set("book" + id,JSONArray.toJSON(book));
            System.out.println("通过数据库查找到书籍");
        }
        else {
            book = JSONArray.parseObject(o.toString(),Book.class);
            System.out.println("通过Redis拿到的一本书");
        }
        return book;
    }

    @Override
    public Book changeInventory(Integer id, Integer amount) {
        Book book = bookRepository.getReferenceById(id);
        System.out.println("I'm changing  inventory!");
        Integer number = book.getInventory();
        book.setInventory(number - amount);
        return bookRepository.saveAndFlush(book);
    }
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    @Override
    @Transactional
    public  List<Book>  deleteOneBook(Integer bookId) {
        redisUtil.del("book"+bookId);
        Book book = bookRepository.getReferenceById(bookId);
        bookRepository.delete(book);
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book insertNewBook(String name, String author, String isbn, Integer inventory, Double price, String description, String imageUrl) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setImage(imageUrl);
        book.setIsbn(isbn);
        book.setInventory(inventory);
        book.setPrice(BigDecimal.valueOf(price));
        book.setDescription(description);
        Book book1 = bookRepository.saveAndFlush(book);
        redisUtil.set("book"+book1.getId(),JSONArray.toJSON(book1));
        return book;
    }
    @Override
    @Transactional
    public Book changeOnesInfo(Integer bookId,String name,String author,String isbn,Integer inventory,Double price,String description,String imageUrl) {
        Object p = redisUtil.get("book"+bookId);
        if(p != null){
            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            book.setImage(imageUrl);
            book.setIsbn(isbn);
            book.setInventory(inventory);
            book.setPrice(BigDecimal.valueOf(price));
            book.setDescription(description);
            redisUtil.set("book" + bookId, JSONArray.toJSON(book));
        }
        Book book = bookRepository.getReferenceById(bookId);
        book.setName(name);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setImage(imageUrl);
        book.setInventory(inventory);
        book.setPrice(BigDecimal.valueOf(price));
        book.setDescription(description);
        bookRepository.saveAndFlush(book);
        return book;
    }
}