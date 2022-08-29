package applicationld.ru.netology.nmedia.old

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.data.PostRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositoryInMemoryImpl(
    private val context: Context
) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val postFileName = "posts.json"

    private var posts = readAll()
        set(value) {
            field = value
            sync()
        }
    private val data = MutableLiveData(posts)

    private fun sync() {
        context.openFileOutput(postFileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts, type))
        }
    }

    private fun readAll(): List<Post> {
        val file = context.filesDir.resolve(postFileName)
        return if (file.exists()) {
            context.openFileInput(postFileName).bufferedReader().use {
                gson.fromJson(it, type)
            }
        } else {
            emptyList()
        }
    }


    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likeByMeCount = if (it.likeByMe) it.likeByMeCount - 1 else it.likeByMeCount + 1,
                likeByMe = !it.likeByMe
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                shareByMeCount = it.shareByMeCount + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {

        //  Добавление нового поста
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = posts.firstOrNull()?.id?.plus(1) ?: 1L
                )
            ) + posts
            data.value = posts
            return
        }

        // Обновление поста
        posts = posts.map {
            if (it.id == post.id) it.copy(
                content = post.content,
                video = post.video
            ) else it
        }

        data.value = posts
    }
}