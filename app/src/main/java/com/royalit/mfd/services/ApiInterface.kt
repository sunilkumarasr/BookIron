package com.royalit.mfd.services

import com.royalit.mfd.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/login")/*{"mobile":"","password":""}*/
    fun login(@Body map:HashMap<String,String>): Call<LoginResponse>

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/register")/*{"mobile":"","email":"","password":""}*/
    fun register(@Body map:HashMap<String,String>): Call<LoginResponse>

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/otp/verify")/*{"user_id":"","otp_number":""}*/
    fun verifyOtp(@Body map:HashMap<String,String>): Call<LoginResponse>

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/otp/resend")/*{"user_id":""}*/
    fun resendOtp(@Body map:HashMap<String,String>): Call<Any>

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/password/forgot")/*{"mobile":""}*/
    fun forgotPassword(@Body map:HashMap<String,String>): Call<Any>

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/password/change")/*{"user_id":"","oldPassword":"","newPassword":"","confirmPassword":""}*/
    fun changePassword(@Body map:HashMap<String,String>): Call<Any>
    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/cms/terms")/*{"user_id":"","oldPassword":"","newPassword":"","confirmPassword":""}*/
    fun termsConditions(): Call<Any>

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/cms/about")/*{"user_id":"","oldPassword":"","newPassword":"","confirmPassword":""}*/
    fun about(): Call<Any>
    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/cms/privacy")/*{"user_id":"","oldPassword":"","newPassword":"","confirmPassword":""}*/
    fun privacy(): Call<Any>


    @Headers(
        "Content-type: application/json"
    )
    @POST("api/v1/orders/place")
    fun placeOrder(@Body map:Any ,@Header("api_key") key:String): Call<Any>


    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/user/orders")
    fun orders(@Body map:HashMap<String,String>): Call<Any>/*{ "user_id" : "1" }*/


    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/user/orders/active")
    fun activeOrders(@Body map:HashMap<String,String>): Call<Any>/*{ "user_id" : "1" }*/

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/user/orders/detail")
    fun orderDetails(@Body map:HashMap<String,String>): Call<Any>/*{ "user_id" : "1" }*/



    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/products/list")
    fun productList(@Body map:HashMap<String,String>): Call<Any>


    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/service-categories/list")
    fun serviceCategoryList(): Call<Any>


    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/profile/update")
    fun updateProfile(@Body map:HashMap<String,String>): Call<Any>/*{
        "user_id" : "1", "full_name" : "surya", "email" : "test@yopmail.com", "mobile" : "88888854221" }*/

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/server/file/upload")
    fun uploadImage(@Body map:HashMap<String,String>): Call<Any>/*{
        "user_id" : "1", "full_name" : "surya", "email" : "test@yopmail.com", "mobile" : "88888854221" }*/

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/address/add")
    fun addAddress(@Body map:HashMap<String,String>): Call<Any>

    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/address/list")
    fun addressList(@Body map:HashMap<String,String>): Call<Any>/*user_id*/


    @Headers("api_key:E28DA37796A786FAB9DC6427B1D19")
    @POST("api/v1/users/profile/fetch")
    fun getProfile(@Body map:HashMap<String,String>): Call<Any>/*{
        "user_id" : "1"}*/



}