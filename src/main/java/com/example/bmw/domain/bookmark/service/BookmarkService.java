package com.example.bmw.domain.bookmark.service;

import com.example.bmw.domain.bookmark.entity.Bookmark;
import com.example.bmw.domain.bookmark.repository.BookmarkRepository;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.post.repository.PostRepository;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.repository.UserRepository;
import com.example.bmw.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void addBookmark(int postId){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new IllegalArgumentException("not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("not found"));

        Bookmark bookmark = new Bookmark(user, post);
        bookmarkRepository.save(bookmark);

        post.setBookmarkCount(post.getGoodCount() + 1);
        postRepository.save(post);
    }

    @Transactional
    public void deleteBookmark(int postId){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new IllegalArgumentException("not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("not found"));

        Bookmark bookmark = bookmarkRepository.findByUserAndPost(user, post).orElseThrow(() -> new IllegalArgumentException("not found"));
        bookmarkRepository.delete(bookmark);

        post.setBookmarkCount(post.getGoodCount() - 1);
        postRepository.save(post);
    }

    @Transactional
    public List<Bookmark> bookmarkList(){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new IllegalArgumentException("not found"));
        return bookmarkRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("not found"));
    }
}
