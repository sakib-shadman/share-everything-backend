package com.shareeverything.service;

import com.shareeverything.dto.BaseResponseDto;
import com.shareeverything.dto.request.PostRequestDto;
import com.shareeverything.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {

    BaseResponseDto addPost(PostRequestDto postRequestDto,String userId);

    List<PostResponseDto> getAllPost(String userId);

    BaseResponseDto updatePost(PostRequestDto postRequestDto);

}
