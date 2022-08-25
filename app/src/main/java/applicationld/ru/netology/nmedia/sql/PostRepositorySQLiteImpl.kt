package applicationld.ru.netology.nmedia.sql

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.data.PostRepository

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {

    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likeByMeCount = if (it.likeByMe) it.likeByMeCount - 1 else it.likeByMeCount + 1,
                likeByMe = !it.likeByMe
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                shareByMeCount = it.shareByMeCount + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }
}