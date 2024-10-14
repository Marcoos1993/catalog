package com.devsuperior.catalog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.catalog.entities.Category;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	public ResponseEntity<Category> findByid(){
		return null;
	}
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L, "carnes"));
		list.add(new Category(2L, "frios"));
		list.add(new Category(1L, "queijos"));


		return ResponseEntity.ok(list);
	}
	

	
	
}
