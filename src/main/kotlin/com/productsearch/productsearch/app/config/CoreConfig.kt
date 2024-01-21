package com.productsearch.productsearch.app.config

import com.productsearch.productsearch.app.ProductDetailsMock
import com.productsearch.productsearch.app.ProductSearchMock
import com.productsearch.productsearch.core.ProductSearchService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreConfig {

    @Bean
    fun getProductSearchService()  = ProductSearchService(ProductDetailsMock(), ProductSearchMock())
}