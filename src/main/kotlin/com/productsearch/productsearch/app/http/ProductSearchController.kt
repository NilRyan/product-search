package com.productsearch.productsearch.app.http

import com.productsearch.productsearch.core.ProductSearchService
import com.productsearch.productsearch.core.SearchResult
import com.productsearch.productsearch.core.entities.ProductDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController @Autowired constructor(private val productSearchService: ProductSearchService) {

    @GetMapping("/products")
    fun performProductSearch(@RequestParam searchTerm: String): SearchResult<ProductDetails> {
        return productSearchService.performProductSearch(searchTerm);
    }
}