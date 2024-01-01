package com.example.mainserivice.daoimpl;

import com.alibaba.fastjson.JSONArray;


import com.example.mainserivice.component.RedisUtil;
import com.example.mainserivice.dao.BookDao;
import com.example.mainserivice.entity.Book;
import com.example.mainserivice.entity.BookDetail;
import com.example.mainserivice.entity.BookTag;
import com.example.mainserivice.repository.BookDetailRepository;
import com.example.mainserivice.repository.BookRepository;
import com.example.mainserivice.repository.BookTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookDetailRepository bookDetailRepository;

    @Autowired
    private BookTagRepository bookTagRepository;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Book findbyID(int id){
        Book book;
        BookDetail bookDetail;
//        Object o = redisUtil.get("book"+id);
//        if(o == null){
            book = bookRepository.getReferenceById(id);
            bookDetail = bookDetailRepository.getBookDetailByIsbn(book.getIsbn());
            System.out.println(bookDetail);
            book.setTags(bookDetail.getTags());
            book.setDescription(bookDetail.getDescription());
            redisUtil.set("book" + id,JSONArray.toJSON(book));
            System.out.println("通过数据库查找到书籍");
//        }
//        else {
//            book = JSONArray.parseObject(o.toString(),Book.class);
//            System.out.println("通过Redis拿到的一本书");
//        }
        return book;
    }
    @Override
    public List<Book> findBooksByTagRelation(String tagName){
        // 先根据标签的名字取找对应的节点
        List<BookTag> list0 = bookTagRepository.findBookTagsByTagNameLike(tagName);
        HashMap<Integer, Integer> result = new HashMap<>();
        List<Book> resultBook = new ArrayList<>();

            // 对于上面找到的节点，把所有相关的BookID放入HashSet
        for (BookTag bookType : list0) {
            for (int j = 0; j < bookType.getBookIDs().size(); j++) {
                int id = bookType.getBookIDs().get(j);
                result.put(id, 1);
            }
        }
            // 再查找一跳之内的list1保存，两跳的用list2保存，然后手动合并
        for (BookTag type : list0) {
            String keyName = type.getTagName();
            List<BookTag> list1 = bookTagRepository.findRelatedBookTag1(keyName);
            List<BookTag> list2 = bookTagRepository.findRelatedBookTag2(keyName);

            for (BookTag bookType : list1) {
                for (int j = 0; j < bookType.getBookIDs().size(); j++) {
                    int id = bookType.getBookIDs().get(j);
                    result.put(id, 1);
                }
            }

            for (BookTag bookType : list2) {
                for (int j = 0; j < bookType.getBookIDs().size(); j++) {
                    int id = bookType.getBookIDs().get(j);
                    result.put(id, 1);
                }
            }
        }
            // 合并之后根据bookid把所有的书返回到上层
        for(int id: result.keySet()){
            resultBook.add(this.findbyID(id));
        }
        return resultBook;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Book findInRep(int id){
        Book book;
        book = bookRepository.getReferenceById(id);
        redisUtil.set("book" + id,JSONArray.toJSON(book));
        System.out.println("通过数据库查找到书籍");
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
        BookDetail bookDetail = bookDetailRepository.getBookDetailByIsbn(book.getIsbn());
        bookRepository.delete(book);
        bookDetailRepository.delete(bookDetail);
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
        BookDetail bookDetail = new BookDetail();
        bookDetail.setDescription(description);
        bookDetail.setTags(null);
        bookDetail.setIsbn(isbn);

        Book book1 = bookRepository.saveAndFlush(book);

        redisUtil.set("book"+book1.getId(),JSONArray.toJSON(book1));
        return book;
    }
    @Override
    @Transactional
    public Book changeOnesInfo(Integer bookId,String name,String author,String isbn,Integer inventory,Double price,String description,String imageUrl) {
        Object p = redisUtil.get("book"+bookId);
        Book book = bookRepository.getReferenceById(bookId);
        BookDetail bookDetail = bookDetailRepository.getBookDetailByIsbn(book.getIsbn());
        book.setName(name);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setImage(imageUrl);
        book.setInventory(inventory);
        book.setPrice(BigDecimal.valueOf(price));
        bookDetail.setDescription(description);
        bookDetail.setTags(null);
        bookRepository.saveAndFlush(book);
        book.setDescription(description);
        book.setTags(null);
        if(p != null){
            redisUtil.set("book" + bookId, JSONArray.toJSON(book));
        }
        return book;
    }
}