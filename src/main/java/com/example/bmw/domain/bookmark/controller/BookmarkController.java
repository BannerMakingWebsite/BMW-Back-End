package com.example.bmw.domain.bookmark.controller;

import com.example.bmw.domain.bookmark.controller.dto.response.BookmarkResponse;
import com.example.bmw.domain.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/template/bookmark/{postId}")
    public void addBookmark(@PathVariable int postId){
        bookmarkService.addBookmark(postId);
    }

    @DeleteMapping("/template/bookmark/{postId}")
    public void deleteBookmark(@PathVariable int postId){
        bookmarkService.deleteBookmark(postId);
    }

    @GetMapping("/template/bookmark/list")
    public List<BookmarkResponse> bookmarkList(){
        return bookmarkService.bookmarkList();
    }
}
