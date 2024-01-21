package com.productsearch.productsearch.core

class SearchResult<T>(
        private val searchTerm: String,
        val items: List<T>
) {

    val meta: Metadata
        get() {
            return Metadata(searchTerm)
        }

    inner class Metadata(val searchTerm: String) {

        val count: Int
            get() {
                return items.size
            }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SearchResult<*>.Metadata

            if (searchTerm != other.searchTerm) return false
            if (count != other.count) return false

            return true
        }

        override fun hashCode(): Int {
            var result = searchTerm.hashCode()
            result = 31 * result + count
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchResult<*>

        if (searchTerm != other.searchTerm) return false
        if (items != other.items) return false
        if (meta != other.meta) return false

        return true
    }

    override fun hashCode(): Int {
        var result = searchTerm.hashCode()
        result = 31 * result + items.hashCode()
        result = 31 * result + meta.hashCode()
        return result
    }


}