package applicationld.ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import applicationld.ru.netology.nmedia.Post

class PostRepositoryInMemoryImpl: PostRepository {

    private var posts = listOf(
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!",
            published = "30 мая в 10:34",
            likeByMe = false,
            likeByMeCount = 0,
            shareByMeCount = 0
        ),
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!",
            published = "21 мая в 21:00",
            likeByMe = false,
            likeByMeCount = 999,
            shareByMeCount = 1098
        )
    )

    private val data = MutableLiveData(posts)

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
}