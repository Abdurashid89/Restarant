package com.example.restuarant.model.server

import com.example.restuarant.model.entities.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by shohboz on 11,Январь,2021
 */
interface ResApi {
    @POST("/api/auth/login")
    fun login(@Body data: LoginData): Single<LoginResData>


    @POST("/api/auth/register")
    fun register(@Body data: RegisterData): Single<LoginResData>

    @POST("/api/product")
    fun addProduct(@Body data: ProductData): Single<MessageData>

    @GET("/api/product/productName")
    fun productExistOrNot(@Query("name") name: String): Single<MessageDataWithoutMessageType>

    @GET("/api/order/byTable/{tableId}")
    fun getOrderByTableId(@Path("tableId") tableId: Long): Single<Long>

//    @GET("/api/category")
//    fun getAllCategory(
//        @Query("page") page: Int,
//        @Query("size") size: Int,
//        @Query("search") search: String
//    ): Single<GetResponseData<List<CategoryInData>>>
//
//    @GET("/api/categoryProduct/getAll")
//    fun getCategorySearch(
//        @Query("name") name: String
//    ): Single<List<CategoryInData>>
//
//    @GET("/api/brand/getAll")
//    fun getBrandSearch(
//        @Query("name") name: String
//    ): Single<List<BrandInData>>

    @GET("/api/product")
    fun getAllProduct(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("search") search: String
    ): Single<GetResponseData<List<ProductInData>>>

    @GET("/api/table")
    fun getAllTable(): Single<ResData<List<TableData>>>

    @GET("/api/categoryMenu/all")
    fun getAllMenus(): Single<ResData<List<CategoryData>>>

    @GET("/api/table")
    fun getAllTables(): Single<ResData<List<TableData>>>

    @GET("/api/menu/getByCategory")
    fun getItemsById(
        @Query("categoryId") categoryId: Int
    ): Single<List<CategoryItemData>>

    @POST("/api/order")
    fun sendOrder(@Body data: OrderSendData): Single<ResOrderData>
}