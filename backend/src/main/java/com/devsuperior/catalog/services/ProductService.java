package com.devsuperior.catalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.catalog.dto.ProductDTO;
import com.devsuperior.catalog.entities.Product;
import com.devsuperior.catalog.repositories.ProductRepository;
import com.devsuperior.catalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		Product entity = product.orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));

		return new ProductDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> products = productRepository.findAll(pageRequest);

		return products.map(x -> new ProductDTO(x));
	}
	
	@Transactional
	public ProductDTO update(Long id,@Valid ProductDTO dto) {
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

	public ProductDTO insert(@Valid ProductDTO dto) {
		Product product = new Product();
		copyToEntity(product, dto);
		product = productRepository.save(product);
		
		return new ProductDTO(product);
	}
	
	public void copyToEntity(Product product, ProductDTO dto) {
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setImgUrl(dto.getImgUrl());
	}

}

