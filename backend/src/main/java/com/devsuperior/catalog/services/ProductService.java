package com.devsuperior.catalog.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.catalog.dto.CategoryDTO;
import com.devsuperior.catalog.dto.ProductDTO;
import com.devsuperior.catalog.entities.Category;
import com.devsuperior.catalog.entities.Product;
import com.devsuperior.catalog.repositories.CategoryRepository;
import com.devsuperior.catalog.repositories.ProductRepository;
import com.devsuperior.catalog.services.exceptions.DatabaseException;
import com.devsuperior.catalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		Product entity = product.orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));

		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		Page<Product> products = productRepository.findAll(pageable);

		return products.map(x -> new ProductDTO(x));
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product product = productRepository.getReferenceById(id);
			copyToEntity(product, dto);	
			product = productRepository.save(product);
			
			return new ProductDTO(product);
		}
		catch(EntityNotFoundException e) {
					throw new ResourceNotFoundException("Id number " + id +  " not found");
				}
		}

	@Transactional
	public ProductDTO insert(@Valid ProductDTO dto) {
		Product product = new Product();
		copyToEntity(product, dto);
		product = productRepository.save(product);
		
		return new ProductDTO(product);
	}

	@Transactional
	public void delete(Long id) {
		if(!productRepository.existsById(id)) {
			throw new ResourceNotFoundException ("Product " + id + "not found");
		}
		try {
			productRepository.deleteById(id);
		}
    	catch (DataIntegrityViolationException e) {
        	throw new DatabaseException("Integrity violation");
    	}
	}
	
	private void copyToEntity(Product product, ProductDTO dto) {
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setImgUrl(dto.getImgUrl());

		product.getCategories().clear();
		for(CategoryDTO dtos : dto.getCategories()) {
			//Set<Category> cats = new HashSet<>();
			Category cat = categoryRepository.getReferenceById(dtos.getId());
			product.getCategories().add(cat);
		}
	}

}

