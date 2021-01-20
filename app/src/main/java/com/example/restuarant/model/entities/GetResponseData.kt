package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class GetResponseData<T>(
    var objectData: T,
    var totalElements: Int = 0,
    var currentPage: Int = 0
)

data class CategoryInData(
    var id: Int = 0,
    var createdAt: String = "",
    var updatedAt: String = "",
    var createdBy: String = "",
    var updatedBy: String = "",
    var name: String = "",
    var parent: String = "",
    var childCategories: Any,
    var active: Boolean = false
) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<CategoryInData>() {
            override fun areItemsTheSame(
                oldItem: CategoryInData,
                newItem: CategoryInData
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CategoryInData,
                newItem: CategoryInData
            ): Boolean = oldItem.name == newItem.name && oldItem.active == newItem.active
        }
    }
}

data class ImageData(
    var id: String = "",
    var name: String,
    var contentType: String,
    var size: Long
)

data class BrandInData(
    var id: Int = 0,
    var createdAt: String = "",
    var updatedAt: String = "",
    var createdBy: String = "",
    var updatedBy: String = "",
    var name: String = "",
    var active: Boolean = false,
    var brandIcon: ImageData
) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<BrandInData>() {
            override fun areItemsTheSame(oldItem: BrandInData, newItem: BrandInData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BrandInData, newItem: BrandInData): Boolean =
                oldItem.name == newItem.name && oldItem.active == newItem.active

        }
    }
}

data class ProductInData(
    var id: Int = 0,
    var createdAt: String,
    var updatedAt: String,
    var createdBy: String,
    var updatedBy: String,
    var name: String,
    var category: CategoryInProductData,
    var active: Boolean = false,
    var hibernateLazyInitializer: Any? = ""

) {
    companion object {
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<ProductInData>() {
            override fun areItemsTheSame(oldItem: ProductInData, newItem: ProductInData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ProductInData,
                newItem: ProductInData
            ): Boolean =
                oldItem.name == newItem.name && oldItem.active == newItem.active && oldItem.category == newItem.category

        }
    }
}

data class CategoryInProductData(
    var id: Int,
    var createdAt: String,
    var updatedAt: String,
    var createdBy: String,
    var updatedBy: String,
    var name: String,
    var parent: String,
    var childCategories: Any,
    var active: Boolean = false,
    var hibernateLazyInitializer: Any
)

data class ResponseFileData<T>(
    var success: Boolean,
    var message: String,
    var objectData: List<T>
)

data class ResponseFileObjectData(
    var fileId: String,
    var fileName: String,
    var fileDownloadUri: String,
    var fileType: String,
    var size: Int = 0
)
