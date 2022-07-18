package applicationld.ru.netology.nmedia.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


    /**
     *
     * SharedPreferences
     *
     * private val prefs = context.getSharedPreferences("posts", Context.MODE_PRIVATE)
     * private val postKey = "posts"
     *
    private fun sync() {
    prefs.edit {
    val postString = gson.toJson(posts, type)
    putString(postKey, postString)
    }
    }

    private fun readAll(): List<Post> =
    prefs.getString(postKey, null)?.let {
    gson.fromJson<List<Post>>(it, type)
    }.orEmpty()
     */

    /**
     *
     * Альтернативный вариант из слайда Сохранение данных
     * Нужно еще к каждому изменяемому методу добавлять sync()
     *
     * //    private var posts = emptyList<Post>()
     *
    //    init {
    //        prefs.getString(postKey, null)?.let {
    //            posts = gson.fromJson(it, type)
    //            data.value = posts
    //        }
    //    }
    //
    //    private fun sync() {
    //        with(prefs.edit()) {
    //            putString(postKey, gson.toJson(posts))
    //            apply()
    //        }
    //    }
     */

    /**
     *
     * Список фиксированный Постов
     *
    //    private var posts = listOf(
    //        Post(
    //            id = 2,
    //            author = "Нетология. Университет интернет-профессий будущего",
    //            content = "Привет, это новая Нетология!",
    //            published = "30 мая в 10:34",
    //            likeByMe = false,
    //            likeByMeCount = 0,
    //            shareByMeCount = 0,
    //            video = "https://www.youtube.com/watch?v=JC5Qch9VTQ4"
    //        ),
    //        Post(
    //            id = 1,
    //            author = "Нетология. Университет интернет-профессий будущего",
    //            content = "Привет, это новая Нетология!",
    //            published = "21 мая в 21:00",
    //            likeByMe = false,
    //            likeByMeCount = 999,
    //            shareByMeCount = 1098,
    //            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
    //        )
    //    )
     */

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

//        // Добавление нового поста
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = posts.firstOrNull()?.id?.plus(1) ?: 1L
//                    id = (posts.firstOrNull()?.id ?: 0L) + 1
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