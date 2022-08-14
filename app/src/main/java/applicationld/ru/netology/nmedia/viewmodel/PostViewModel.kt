package applicationld.ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.data.PostRepository
import applicationld.ru.netology.nmedia.data.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likeByMe = false,
    likeByMeCount = 0,
    shareByMeCount = 0,
    viewByMeCount = 0,
    video = ""
)

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryInMemoryImpl(application)

    val data = repository.getAll()

    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        if (content.isBlank()) {
            return
        }

        edited.value?.let {
            edited.value = it.copy(
                content = content,
            )
        }
    }

    fun changeVideo(video: String) {

        edited.value?.let {
            edited.value = it.copy(
                video = video,
            )
        }
    }

    fun close() {
        edited.value = empty
    }

    fun onLikeClicked(id: Long) = repository.likeById(id)
    fun onShareClicked(id: Long) = repository.shareById(id)
    fun onRemoveClicked(id: Long) = repository.removeById(id)


}