package com.devsuperior.catalog.factory;

import com.devsuperior.catalog.dto.ProductDTO;
import com.devsuperior.catalog.entities.Category;
import com.devsuperior.catalog.entities.Product;

public class Factory {

	public static Product createProduct() {
		
		Product product = new Product(1L, "Phone", "Good phone", 800.0, "https://img.com");
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category createCategory() {
		
		Category category = new Category(1L, "Electronics");
		return category;
	}
}
