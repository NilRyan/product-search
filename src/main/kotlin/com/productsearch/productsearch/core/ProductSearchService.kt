package com.productsearch.productsearch.core

import com.productsearch.productsearch.core.clients.ProductDetailsClient
import com.productsearch.productsearch.core.clients.ProductSearchClient
import com.productsearch.productsearch.core.clients.ProductSearchResult
import com.productsearch.productsearch.core.entities.ProductDetails
import com.productsearch.productsearch.core.entities.ProductOption
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

class ProductSearchService(private val productDetailsClient: ProductDetailsClient,
                           private val productSearchClient: ProductSearchClient) {

    fun performProductSearch(searchTerm: String): SearchResult<ProductDetails> {
        val searchedProducts = productSearchClient.searchProducts(searchTerm)
        val searchedProductsWithDetails = mapProductDetails(searchedProducts)

        return SearchResult<ProductDetails>(searchTerm = searchTerm, items = searchedProductsWithDetails)
    }

    private fun mapProductDetails(searchedProducts: ProductSearchResult): List<ProductDetails>  {
        val products = ArrayList<ProductDetails>()

        // IMPORTANT: Looping through network calls, could be improved by doing a batch call instead or having some kind of caching mechanism
        for (id in searchedProducts.itemIds) {
            val product = productDetailsClient.getProductDetailsById(id)

            product ?: logger.warn { "Product details with ID $id does not exist" }

            product?.let {
                products.add(ProductDetails(
                        id = product.id,
                        name = product.name,
                        minimumPrice = product.options.minBy { it.price }.price,
                        maximumPrice = product.options.maxBy { it.price }.price,
                        options = product.options.map {
                            ProductOption(
                                    id = it.id,
                                    price = it.price,
                                    name = it.name
                            )
                        }.toSet()
                ))
            }
        }

        return products
    }
}