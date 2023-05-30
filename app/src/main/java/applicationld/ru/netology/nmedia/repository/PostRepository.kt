package applicationld.ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import applicationld.ru.netology.nmedia.data.Post

interface PostRepository {
//    fun getAll(): List<Post>
//    fun likeById(id: Long): Post
//    fun unlikeById(id: Long): Post
    fun shareById(id: Long)
//    fun removeById(id: Long)
//    fun save(post: Post)

    fun getAllAsync(callback : Callback<List<Post>>)
    fun likeByIdAsync(id: Long, callback: Callback<Post>)
    fun unlikeByIdAsync(id: Long, callback: Callback<Post>)
    fun removeByIdAsync(id: Long, callback: Callback<Unit>)
    fun saveAsync(post: Post, callback : Callback<Post>)

    interface Callback<T> {
        fun onSuccess(posts: T)
        fun onError(e: Exception)
    }
}