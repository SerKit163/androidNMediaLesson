package applicationld.ru.netology.nmedia.sql

import applicationld.ru.netology.nmedia.data.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post): Post
}