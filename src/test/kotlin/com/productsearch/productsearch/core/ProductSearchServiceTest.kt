package com.productsearch.productsearch.core

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.productsearch.productsearch.core.clients.*
import com.productsearch.productsearch.core.entities.ProductDetails
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductSearchServiceTest {
    val productDetailsClient: ProductDetailsClient = mockk()
    val productSearchGateway: ProductSearchClient = mockk()
    val sut: ProductSearchService = ProductSearchService(productDetailsClient, productSearchGateway)

    @AfterEach
    fun afterTests() {
        clearAllMocks()
    }

    @Test
    fun `performProductSearch should return list of products matching the search term with complete details`() {
        val searchTerm = "Mod"
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
        )
        every { productDetailsClient.getProductDetailsById(any()) }.returnsMany(products)
        every { productSearchGateway.searchProducts(any()) }.returns(ProductSearchResult(searchTerm, products.map { it.id }.toIntArray()))

        val result = sut.performProductSearch(searchTerm)

        val expectedResult = SearchResult<ProductDetails>("Mod",
                listOf(
                        ProductDetails(
                                id = 1,
                                name = "Modern Chair",
                                minimumPrice = BigDecimal(123),
                                maximumPrice = BigDecimal(154),
                                options = setOf(com.productsearch.productsearch.core.entities.ProductOption(
                                        id = 4,
                                        name = "Blue",
                                        price = BigDecimal(123)
                                ),
                                        com.productsearch.productsearch.core.entities.ProductOption(
                                                id = 5,
                                                name = "Red",
                                                price = BigDecimal(154)
                                        )
                                )

                        ),
                        ProductDetails(
                                id = 2,
                                name = "Modern Table",
                                minimumPrice = BigDecimal(1223),
                                maximumPrice = BigDecimal(2154),
                                options = setOf(com.productsearch.productsearch.core.entities.ProductOption(
                                        id = 6,
                                        name = "Wood",
                                        price = BigDecimal(1223)
                                ),
                                        com.productsearch.productsearch.core.entities.ProductOption(
                                                id = 7,
                                                name = "Metal",
                                                price = BigDecimal(2154)
                                        )
                                )

                        ),

                        ))

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `performProductSearch should return empty list if no record is found`() {

        every { productDetailsClient.getProductDetailsById(any()) }.returns(null)
        val searchTerm = "search"
        every { productSearchGateway.searchProducts(any()) }.returns(ProductSearchResult(searchTerm, IntArray(0)))

        val result = sut.performProductSearch(searchTerm)

        val expectedResult = SearchResult<ProductDetails>("search", emptyList())

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `performProductSearch should return correct price ranges when there are more than 2 price options`() {

        val products = listOf<Product>(
                Product(
                        id = 1,
                        name = "Modern Chair",
                        options = listOf(
                                ProductOption(id = 4, name = "Blue", price = BigDecimal(123.0)),
                                ProductOption(id = 5, name = "Red", price = BigDecimal(154.0)),
                                ProductOption(id = 6, name = "Indigo", price = BigDecimal(99.0))
                        ),
                ),
        )

        every { productDetailsClient.getProductDetailsById(any()) }.returnsMany(products)
        val searchTerm = "Mod"
        every { productSearchGateway.searchProducts(any()) }.returns(ProductSearchResult(searchTerm, products.map { it.id }.toIntArray()))


        val result = sut.performProductSearch(searchTerm)

        val expectedResult = SearchResult<ProductDetails>("Mod", listOf(ProductDetails(
                id = 1,
                name = "Modern Chair",
                minimumPrice = BigDecimal(99),
                maximumPrice = BigDecimal(154),
                options = setOf(
                        com.productsearch.productsearch.core.entities.ProductOption(
                                id = 4,
                                name = "Blue",
                                price = BigDecimal(123)
                        ),
                        com.productsearch.productsearch.core.entities.ProductOption(
                                id = 5,
                                name = "Red",
                                price = BigDecimal(154)
                        ),
                        com.productsearch.productsearch.core.entities.ProductOption(
                                id = 6,
                                name = "Indigo",
                                price = BigDecimal(99)
                        ),

                        )

        )))

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `performProductSearch should return correct price range when there is only 1 price option`() {

        val products = listOf<Product>(
                Product(
                        id = 1,
                        name = "Modern Chair",
                        options = listOf(
                                ProductOption(id = 6, name = "Indigo", price = BigDecimal(99.0))
                        ),
                ),
        )

        every { productDetailsClient.getProductDetailsById(any()) }.returnsMany(products)
        val searchTerm = "Mod"
        every { productSearchGateway.searchProducts(any()) }.returns(ProductSearchResult(searchTerm, products.map { it.id }.toIntArray()))


        val result = sut.performProductSearch(searchTerm)

        assertThat(result.items[0].priceRange).isEqualTo("US$99.00-US$99.00")
    }
}