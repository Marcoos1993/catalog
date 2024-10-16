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

	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> products = productRepository.findAll(pageRequest);

		return products.map(x -> new ProductDTO(x));
	}


}
