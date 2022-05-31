package applicationld.ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import applicationld.ru.netology.nmedia.Post

class PostRepositoryInMemoryImpl: PostRepository {

    private val post: Post get() = checkNotNull(data.value) {
        "data.value null"
    }

    override val data = MutableLiveData(
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

    override fun like() {
        val post = post
        data.value = post.copy(
            likeByMeCount = if (post.likeByMe) post.likeByMeCount - 1 else post.likeByMeCount + 1,
            likeByMe = !post.likeByMe
        )
    }

    override fun share() {
        val post = post
        data.value = post.copy(
            shareByMeCount = post.shareByMeCount + 1
        )
    }
}