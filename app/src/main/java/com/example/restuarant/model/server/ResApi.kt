package com.example.restuarant.model.server

import com.example.restuarant.model.entities.*
import com.example.restuarant.model.entities.check.CheckData
import com.example.restuarant.model.entities.check.PaidCheck
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    @POST("/api/product")
    fun inputProduct(@Body data: ProductInData): Single<MessageData>

    @POST("/api/product/output")
    fun outputProduct(@Body data: ProductInData): Single<MessageData>

    @GET("/api/product/productName")
    fun productExistOrNot(@Query("name") name: String): Single<MessageDataWithoutMessageType>

    @GET("/api/product/search")
    fun getAllProduct(
    ): Single<GetResponseData<List<ProductInData>>>

    @GET("/api/product/inputHistory")
    fun productInputHistory(): Single<List<ProductData>>

    @GET("/api/product/outputHistory")
    fun productOutputHistory(): Single<List<ProductData>>

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

    @POST("/api/order")
    fun orderUpdate(@Body data: OrderUpdateData): Single<ResOrderData>

    @GET("/api/order/byTable")
    fun getTableInfo(@Query("tableId") tableId: Int): Single<ResData<OrderGetData>>

    @POST("/api/order/pay")
    fun sendCheckByOrderId(
        @Body data: PaidCheck
    ): Single<CheckData>

    @GET("/api/order/unPayOrder/UNPAID")
    fun getOrderUnPaid(): Single<ResData<List<OrderGetData>>>


    @GET("/api/order/unPayOrder/PAID")
    fun getAllHistory(): Single<ResData<List<OrderGetData>>>
}