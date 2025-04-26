package com.hugh.controller;

import com.hugh.domain.Book;
import com.hugh.controller.Result;
import com.hugh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图书控制器：列表、搜索、详情
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 获取所有图书（可选搜索关键字）
     */
    @GetMapping
    public Result getBooks(@RequestParam(required = false) String search) {
        List<Book> books;
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchByKeyword(search.trim());
        } else {
            books = bookService.findAll();
        }
        return Result.success(books);
    }

    /**
     * 获取图书详情
     */
    @GetMapping("/{id}")
    public Result getBook(@PathVariable("id") Integer id) {
        Book book = bookService.getById(id);
        if (book != null) {
            return Result.success(book);
        } else {
            return Result.error("图书不存在");
        }
    }
}
