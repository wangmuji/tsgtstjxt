package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entity.Books;

@Service("recommendService")
public interface RecommendService {
	public List<Books> getRecommend(String userid);
}
