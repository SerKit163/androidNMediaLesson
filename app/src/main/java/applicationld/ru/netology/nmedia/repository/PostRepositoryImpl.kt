package applicationld.ru.netology.nmedia.repository

import android.widget.Toast
import applicationld.ru.netology.nmedia.api.PostApi
import applicationld.ru.netology.nmedia.data.Post
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class PostRepositoryImpl : PostRepository {
//    private val client = OkHttpClient.Builder()
//        .connectTimeout(30, TimeUnit.SECONDS)
//        .build()
//    private val gson = Gson()
//    private val typeToken = object : TypeToken<List<Post>>() {}

//    companion object {
//        private const val BASE_URL = "http://10.0.2.2:9999"
//        private val jsonType = "application/json".toMediaType()
//    }

//    override fun getAll(): List<Post> {
//        return PostApi.service.getPosts()
//            .execute()
//            .let { it.body() ?: throw RuntimeException("body is null") }
//    }

    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        PostApi.service.getPosts()
            .enqueue(object : retrofit2.Callback<List<Post>> {
                override fun onResponse(
                    call: retrofit2.Call<List<Post>>,
                    response: retrofit2.Response<List<Post>>
                ) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.errorBody()?.string()))
                        return
                    }

                    val body = response.body() ?: throw RuntimeException("body is null")
                    callback.onSuccess(body)
                }

                override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }

            })

//        val request: Request = Request.Builder()
//            .url("${BASE_URL}/api/slow/posts")
//            .build()
//
//        client.newCall(request)
//            .enqueue(object : Callback {
//                override fun onResponse(call: Call, response: Response) {
//                    val body = response.body?.string() ?: throw RuntimeException("body is null")
//                    try {
//                        callback.onSuccess(gson.fromJson(body, typeToken.type))
//                    } catch (e: Exception) {
//                        callback.onError(e)
//                    }
//                }
//
//                override fun onFailure(call: Call, e: IOException) {
//                    callback.onError(e)
//                }
//            })
    }

//    override fun save(post: Post) {
//        PostApi.service.savePost(post)
//            .execute()
//    }
//
//    override fun removeById(id: Long) {
//        PostApi.service.deletePost(id)
//            .execute()
//    }

    override fun likeByIdAsync(id: Long, callback: PostRepository.Callback<Post>) {

        PostApi.service.like(id)
            .enqueue(object : retrofit2.Callback<Post> {
                override fun onResponse(
                    call: retrofit2.Call<Post>,
                    response: retrofit2.Response<Post>
                ) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.errorBody()?.string()))
                        return
                    }

                    val body = response.body() ?: throw RuntimeException("body is null")
                    callback.onSuccess(body)
                }

                override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }

            })

//        val request: Request = Request.Builder()
//            .url("${BASE_URL}/api/posts/${id}/likes")
//            .post(gson.toJson(id).toRequestBody(jsonType))
//            .build()
//
//       client.newCall(request)
//           .enqueue(object : Callback {
//               override fun onResponse(call: Call, response: Response) {
//                   val body = response.body?.string() ?: throw RuntimeException("body is null")
//                   try {
//                       callback.onSuccess(gson.fromJson(body, Post::class.java))
//                   } catch (e: Exception) {
//                       callback.onError(e)
//                   }
//               }
//
//               override fun onFailure(call: Call, e: IOException) {
//                   callback.onError(e)
//               }
//           })
    }

    override fun unlikeByIdAsync(id: Long, callback: PostRepository.Callback<Post>) {

        PostApi.service.unlike(id)
            .enqueue(object : retrofit2.Callback<Post> {
                override fun onResponse(
                    call: retrofit2.Call<Post>,
                    response: retrofit2.Response<Post>
                ) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.errorBody()?.string()))
                        return
                    }

                    val body = response.body() ?: throw RuntimeException("body is null")
                    callback.onSuccess(body)
                }

                override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }

            })

//        val request: Request = Request.Builder()
//            .url("${BASE_URL}/api/posts/${id}/likes")
//            .delete()
//            .build()
//
//        client.newCall(request)
//            .enqueue(object : Callback {
//                override fun onResponse(call: Call, response: Response) {
////                    val body = response.body?.string() ?: throw RuntimeException("body is null")
//                    val body = response.body?.string() ?: run {
//                        callback.onError(RuntimeException("body is null"))
//                        return
//                    }
//                    try {
//                        callback.onSuccess(gson.fromJson(body, Post::class.java))
//                    } catch (e: Exception) {
//                        callback.onError(e)
//                    }
//                }
//
//                override fun onFailure(call: Call, e: IOException) {
//                    callback.onError(e)
//                }
//            })
    }

    override fun shareById(id: Long) {
//        val request: Request = Request.Builder()
//            .post(gson.toJson(id).toRequestBody(jsonType))
//            .url("${BASE_URL}/api/slow/posts")
//            .build()
    }

    override fun saveAsync(post: Post, callback: PostRepository.Callback<Post>) {

        PostApi.service.savePost(post)
            .enqueue(object : retrofit2.Callback<Post> {
                override fun onResponse(
                    call: retrofit2.Call<Post>,
                    response: retrofit2.Response<Post>
                ) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.errorBody()?.string()))
                        return
                    }

                    val body = response.body() ?: throw RuntimeException("body is null")
                    callback.onSuccess(body)
                }


                override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }

            })

//        val request: Request = Request.Builder()
//            .post(gson.toJson(post).toRequestBody(jsonType))
//            .url("${BASE_URL}/api/slow/posts")
//            .build()
//
//        client.newCall(request)
//            .enqueue(object : Callback {
//                override fun onResponse(call: Call, response: Response) {
////                    val body = response.body?.string() ?: throw RuntimeException("body is null")
//                    val body = response.body?.string() ?: run {
//                        callback.onError(RuntimeException("body is null"))
//                        return
//                    }
//
//                    try {
//                        callback.onSuccess(gson.fromJson(body, Post::class.java))
//                    } catch (e: Exception) {
//                        callback.onError(e)
//                    }
//                }
//
//                override fun onFailure(call: Call, e: IOException) {
//                    callback.onError(e)
//                }
//            })
    }

    override fun removeByIdAsync(id: Long, callback: PostRepository.Callback<Unit>) {

        PostApi.service.deletePost(id)
            .enqueue(object : retrofit2.Callback<Unit> {
                override fun onResponse(
                    call: retrofit2.Call<Unit>,
                    response: retrofit2.Response<Unit>
                ) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.errorBody()?.string()))
                        return
                    }

                    val body = response.body() ?: throw RuntimeException("body is null")
                    callback.onSuccess(body)
                }

                override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                    callback.onError(RuntimeException(t))
                }

            })


//        val request: Request = Request.Builder()
//            .delete()
//            .url("${BASE_URL}/api/slow/posts/${id}")
//            .build()
//
//        client.newCall(request)
//            .enqueue(object : Callback {
//                override fun onResponse(call: Call, response: Response) {
////                    val body = response.body?.string() ?: throw RuntimeException("body is null")
////                    try {
////                        callback.onSuccess(gson.fromJson(body, Unit::class.java))
////                    } catch (e: Exception) {
////                        callback.onError(e)
////                    }
//
//                    try {
//                        callback.onSuccess(Unit)
//                    } catch (e: Exception) {
//                        callback.onError(e)
//                    }
//                }
//
//                override fun onFailure(call: Call, e: IOException) {
//                    callback.onError(e)
//                }
//            })
    }
}
