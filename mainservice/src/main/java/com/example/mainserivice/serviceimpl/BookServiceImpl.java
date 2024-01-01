package com.example.mainserivice.serviceimpl;


import com.example.mainserivice.dao.BookDao;
import com.example.mainserivice.dao.CartDao;
import com.example.mainserivice.dao.OrderDao;
import com.example.mainserivice.entity.Book;
import com.example.mainserivice.entity.BookDetail;
import com.example.mainserivice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CartDao cartDao;

    @Override
    public Book findBookById(Integer id) {
        return bookDao.findbyID(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public List<Book> deleteOneBook(Integer bookId) {
        Book book = bookDao.findbyID(bookId);
        orderDao.deleteOrderItemsByBook(book);
        cartDao.deleteCartItemsByBook(book);
        return bookDao.deleteOneBook(bookId);
    }
    @Override
    public List<Book> findBooksByTagRelation(String tagName){
        return bookDao.findBooksByTagRelation(tagName);
    }
    @Override
    public Book insertNewBook(String name, String author, String isbn, Integer inventory, Double price, String description, String imageUrl) {
        return bookDao.insertNewBook(name, author, isbn, inventory, price, description, imageUrl);
    }

    ;

    @Override
    public Book changeOnesInfo(Integer bookId, String name, String author, String isbn, Integer inventory, Double price, String description, String imageUrl) {
            return bookDao.changeOnesInfo(bookId, name, author, isbn, inventory, price, description, imageUrl);
        }

}