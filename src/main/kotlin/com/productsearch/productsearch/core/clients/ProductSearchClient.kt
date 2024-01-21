package com.productsearch.productsearch.core.clients

data class ProductSearchResult(val searchTerm: String, val itemIds: IntArray)

interface ProductSearchClient {
    fun searchProducts(searchTerm: String): ProductSearchResult
}