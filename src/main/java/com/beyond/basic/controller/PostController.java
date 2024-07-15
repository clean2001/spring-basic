package com.beyond.basic.controller;

import com.beyond.basic.dto.PostResDto;
import com.beyond.basic.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    @ResponseBody
    @GetMapping("/list")
    public List<PostResDto> getPostList() {
        return postService.getPostList();
    }
}
