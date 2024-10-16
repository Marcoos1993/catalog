package com.devsuperior.catalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.catalog.dto.CategoryDTO;
import com.devsuperior.catalog.entities.Category;
import com.devsuperior.catalog.repositories.CategoryRepository;
import com.devsuperior.catalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = categoryRepository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).toList();	
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Category number " + id + " not found"));

		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO insert(@Valid CategoryDTO dto) {
		Category cat = new Category();
		cat.setName(dto.getName());
		cat = categoryRepository.save(cat);
		return new CategoryDTO(cat);
	}

	@Transactional
	public CategoryDTO update(@Valid Long id, CategoryDTO dto) {
		try {
			Category cat = categoryRepository.getReferenceById(id);
			cat.setName(dto.getName());
			cat = categoryRepository.save(cat);
			return new CategoryDTO(cat);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id number " + id +  " not found");
		}
	}
}
