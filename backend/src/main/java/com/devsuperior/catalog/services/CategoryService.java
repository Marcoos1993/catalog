package com.devsuperior.catalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.catalog.dto.CategoryDTO;
import com.devsuperior.catalog.entities.Category;
import com.devsuperior.catalog.repositories.CategoryRepository;
import com.devsuperior.catalog.services.exceptions.DatabaseException;
import com.devsuperior.catalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;


	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		Page<Category> list = categoryRepository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
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

	public void delete(Long id) {
		if(!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Category " + id + " not found");
		}
		try {
        	categoryRepository.deleteById(id);    		
	}
    	catch (DataIntegrityViolationException e) {
        	throw new DatabaseException("Integrity violation");
   	}
		
	}
}
