package com.beyond.basic.service;

import com.beyond.basic.domain.Post;
import com.beyond.basic.dto.PostResDto;
import com.beyond.basic.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    public List<PostResDto> getPostList() {
        List<Post> postList = postRepository.findAll();

        for(Post p : postList) {
            System.out.println("저자의 이름은 " + p.getMember().getName());
        }

        return postList.stream().map(Post::fromEntity).collect(Collectors.toList());

        // 현재는 조회 상황이기 때문에,fromEntity만 필요하다.
    }
}
