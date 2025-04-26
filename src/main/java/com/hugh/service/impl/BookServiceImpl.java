package com.hugh.service.impl;

import com.hugh.dao.BookDao;
import com.hugh.domain.Book;
import com.hugh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图书服务实现
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> searchByKeyword(String keyword) {
        return bookDao.searchByKeyword(keyword);
    }

    @Override
    public Book getById(Integer bookId) {
        return bookDao.findById(bookId);
    }
}
