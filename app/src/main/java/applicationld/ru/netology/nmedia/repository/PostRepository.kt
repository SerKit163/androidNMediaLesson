package applicationld.ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import applicationld.ru.netology.nmedia.data.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(id: Long): Post
    fun unlikeById(id: Long): Post
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}