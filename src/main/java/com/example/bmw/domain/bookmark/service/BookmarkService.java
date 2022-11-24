package com.example.bmw.domain.bookmark.service;

import com.example.bmw.domain.bookmark.controller.dto.response.BookmarkResponse;
import com.example.bmw.domain.bookmark.entity.Bookmark;
import com.example.bmw.domain.bookmark.repository.BookmarkRepository;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.post.repository.PostRepository;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.repository.UserRepository;
import com.example.bmw.global.error.ErrorCode;
import com.example.bmw.global.error.exception.CustomException;
import com.example.bmw.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void addBookmark(int postId){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.TEMPLATE_NOT_FOUND));

        Bookmark bookmark = new Bookmark(user, post);
        bookmarkRepository.save(bookmark);

        post.setBookmarkCount(post.getBookmarkCount() + 1);
        postRepository.save(post);
    }

    @Transactional
    public void deleteBookmark(int postId){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.TEMPLATE_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository.findByUserAndPost(user, post).orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));
        bookmarkRepository.delete(bookmark);

        post.setBookmarkCount(post.getBookmarkCount() - 1);
        postRepository.save(post);
    }

    @Transactional
    public List<BookmarkResponse> bookmarkList(){
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user).orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));
        return bookmarks.stream().map(m -> new BookmarkResponse(
                m.getPost().getId(),
                m.getPost().getComments(),
                m.getUser(),
                m.getPost().getCategory(),
                m.getPost().getDesign(),
                m.getPost().getTitle(),
                m.getPost().getGoodCount(),
                m.getPost().getBookmarkCount(),
                m.getPost().getCreateTime(),
                m.getPost().getPreview()
                )).collect(Collectors.toList());
    }
}
