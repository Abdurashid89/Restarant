package com.example.restuarant.model.entities

import androidx.recyclerview.widget.DiffUtil

data class GetResponseData<T>(
    var objectData: T,
    var totalElements: Int = 0,
    var currentPage: Int = 0
)

data class ImageData(
    var id: String = "",
    var name: String,
    var contentType: String,
    var size: Long
)

data class ProductInData(
    var id: Int = 0,
    var createdAt: String,
    var updatedAt: String,
    var createdBy: String,
    var updatedBy: String,
    var name: String,
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
                oldItem.name == newItem.name && oldItem.active == newItem.active

        }
    }
}

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
