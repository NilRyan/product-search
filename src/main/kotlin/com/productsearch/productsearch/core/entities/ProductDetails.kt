package com.productsearch.productsearch.core.entities

import java.math.BigDecimal
import java.util.*

data class ProductOption(val id: Int, val name: String, val price: BigDecimal)

class ProductDetails(val id: Int, val name: String, private val currency: Currency = Currency.getInstance("USD"), private val minimumPrice: BigDecimal, private val maximumPrice: BigDecimal, val options: Set<ProductOption>) {
    val priceRange: String
        get() {

            return "${currency.symbol}${minimumPrice.setScale(2)}-${currency.symbol}${maximumPrice.setScale(2)}"
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductDetails

        if (id != other.id) return false
        if (name != other.name) return false
        if (currency != other.currency) return false
        if (minimumPrice != other.minimumPrice) return false
        if (maximumPrice != other.maximumPrice) return false
        if (options != other.options) return false
        if (priceRange != other.priceRange) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + currency.hashCode()
        result = 31 * result + minimumPrice.hashCode()
        result = 31 * result + maximumPrice.hashCode()
        result = 31 * result + options.hashCode()
        result = 31 * result + priceRange.hashCode()
        return result
    }


}