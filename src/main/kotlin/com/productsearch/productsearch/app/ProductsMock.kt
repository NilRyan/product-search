package com.productsearch.productsearch.app

import com.productsearch.productsearch.core.clients.*
import java.math.BigDecimal

val products = listOf<Product>(
        Product(
                id = 1,
                name = "Modern Chair",
                options = listOf(
                        ProductOption(id = 4, name = "Blue", price = BigDecimal(123.0)),
                        ProductOption(id = 5, name = "Red", price = BigDecimal(154.0))
                ),
        ),
        Product(id = 2,
                name = "Modern Table",
                options = listOf(
                        ProductOption(id = 6, name = "Wood", price = BigDecimal(1223.0)),
                        ProductOption(id = 7, name = "Metal", price = BigDecimal(2154.0))
                )
        ),
        Product(id = 3,
                name = "Modern Couch",
                options = listOf(
                        ProductOption(id = 6, name = "Fuzzy", price = BigDecimal(892.0)),
                        ProductOption(id = 7, name = "Leather", price = BigDecimal(1054.0))
                )
        ))

class ProductSearchMock : ProductSearchClient {

    override fun searchProducts(searchTerm: String): ProductSearchResult {
        return ProductSearchResult(searchTerm, itemIds = products.filter {
            it.name.contains(searchTerm)
        }.map { it.id }.toIntArray());
    }

}

class ProductDetailsMock : ProductDetailsClient {

    override fun getProductDetailsById(id: Int): Product? {
        return products.firstOrNull { it.id == id };
    }
}
