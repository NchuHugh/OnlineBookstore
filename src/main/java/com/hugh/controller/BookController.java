package com.hugh.controller;

import com.hugh.domain.Book;
import com.hugh.controller.Result;
import com.hugh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图书控制器：列表、搜索、详情
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 获取图书列表（支持分页和搜索）
     * @param page 页码（从1开始）
     * @param size 每页数量
     * @param search 搜索关键词
     * @return 分页结果
     */
    @GetMapping
    public Result getBooks(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "16") Integer size,
            @RequestParam(required = false) String search) {
        
        // 页码从1开始，转换为从0开始
        int offset = (page - 1) * size;
        
        List<Book> books;
        int total;
        
        if (search != null && !search.trim().isEmpty()) {
            // 搜索带分页
            books = bookService.searchByKeywordWithPaging(search.trim(), offset, size);
            total = bookService.countByKeyword(search.trim());
        } else {
            // 全部图书带分页
            books = bookService.findAllWithPaging(offset, size);
            total = bookService.count();
        }
        
        // 计算总页数
        int totalPages = (int) Math.ceil((double) total / size);
        
        // 构造分页响应数据
        Map<String, Object> data = new HashMap<>();
        data.put("books", books);
        data.put("total", total);
        data.put("page", page);
        data.put("size", size);
        data.put("totalPages", totalPages);
        
        return Result.success(data);
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
