package ro.matheszabi.scale3candroidassignment.retrofit

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("public-api/todos")
    fun getTodosWithPaginationInfo() : Call<JsonObject>

    @GET("public/v2/todos")
    fun getTodosWithoutPagination() : Call<JsonArray>

    companion object {

        var BASE_URL = "https://gorest.co.in/"



        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }

}