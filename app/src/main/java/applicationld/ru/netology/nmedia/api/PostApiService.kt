package applicationld.ru.netology.nmedia.api

import applicationld.ru.netology.nmedia.BuildConfig
import applicationld.ru.netology.nmedia.data.Post
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

private const val BASE_URL = "${BuildConfig.BASE_URL}/api/slow/"

private val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface PostApiService {

    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @POST("posts")
    fun savePost(@Body post: Post): Call<Post>

    @POST("posts/{id}/likes")
    fun like(@Path("id") id: Long): Call<Post>

    @DELETE("posts/{id}/likes")
    fun unlike(@Path("id") id: Long): Call<Post>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Long): Call<Unit>

}

object PostApi {
    val service by lazy {
        retrofit.create<PostApiService>()
    }
}