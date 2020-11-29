package com.shareeverything.controller;

import com.shareeverything.dto.request.PostRequestDto;
import com.shareeverything.security.SecurityContextDetails;
import com.shareeverything.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class PostController extends BaseController {

    @Autowired
    PostService postService;

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequestDto postRequestDto) {
        SecurityContextDetails contextDetails = getSecurityContextDetails();
        return ResponseEntity.ok(postService.addPost(postRequestDto, contextDetails.getUserid()));
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.POST)
    public ResponseEntity<?> updatePost(@RequestBody PostRequestDto postRequestDto, @PathVariable String postId) {
        SecurityContextDetails contextDetails = getSecurityContextDetails();
        return ResponseEntity.ok(postService.updatePost(postRequestDto, postId));
    }

    @RequestMapping(value = "/all-post", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPost() {
        SecurityContextDetails contextDetails = getSecurityContextDetails();
        return ResponseEntity.ok(postService.getAllPost(contextDetails.getUserid()));
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPost(@PathVariable(name = "postId") String postId) {
        SecurityContextDetails contextDetails = getSecurityContextDetails();
        return ResponseEntity.ok(postService.getPost(postId, contextDetails.getUserid()));
    }

    @RequestMapping(value = "/post-info", method = RequestMethod.GET)
    public ResponseEntity<?> getPostInfo() {
        return ResponseEntity.ok(postService.postInfo());
    }






}
