package com.shareeverything.service;

import com.shareeverything.dto.BaseResponseDto;
import com.shareeverything.dto.request.AmountToPayRequestDto;
import com.shareeverything.dto.request.BookingSetRequestDto;
import com.shareeverything.dto.request.PostRequestDto;
import com.shareeverything.dto.response.AmountToPayResponseDto;
import com.shareeverything.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {

    BaseResponseDto addPost(PostRequestDto postRequestDto,String userId);

    List<PostResponseDto> getAllPost(String userId);

    BaseResponseDto updatePost(PostRequestDto postRequestDto,String postId);

    PostResponseDto getPost(String postId,String userId);

    List<PostResponseDto> getPostFeed();

    PostResponseDto getPostDetails(String postId);

    AmountToPayResponseDto getAmountToPay(AmountToPayRequestDto amountToPayRequestDto,String postId);

    BaseResponseDto setBooking(String userId, BookingSetRequestDto bookingSetRequestDto);

    BaseResponseDto confirmBooking(String orderId);


}
