package com.devsuperior.catalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.catalog.entities.Category;
import com.devsuperior.catalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll(){
		List<Category> list = categoryRepository.findAll();
	//	List<CategoryDTO> result = list.stream().map(x -> new CategoryDTO(x)).toList();
		
		return list;
	}
}
