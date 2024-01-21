package com.productsearch.productsearch.core.clients

import java.math.BigDecimal


data class ProductOption(val id: Int, val name: String, val price: BigDecimal)
data class Product(val id: Int, val name: String, val options: List<ProductOption>)

interface ProductDetailsClient {
    fun getProductDetailsById(id: Int): Product?
}