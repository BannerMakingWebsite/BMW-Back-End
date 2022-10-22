package com.example.bmw.domain.bookmark.controller;

import com.example.bmw.domain.bookmark.entity.Bookmark;
import com.example.bmw.domain.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/template/bookmark/{id}")
    public void addBookmark(@PathVariable int id){
        bookmarkService.addBookmark(id);
    }

    @DeleteMapping("/template/bookmark/{id}")
    public void deleteBookmark(@PathVariable int id){
        bookmarkService.deleteBookmark(id);
    }

    @GetMapping("/template/bookmark/list")
    public List<Bookmark> bookmarkList(){
        return bookmarkService.bookmarkList();
    }
}
