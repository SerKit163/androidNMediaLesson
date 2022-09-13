package applicationld.ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.model.FeedModel
import applicationld.ru.netology.nmedia.repository.PostRepository
import applicationld.ru.netology.nmedia.repository.PostRepositoryImpl
import applicationld.ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    likes = 0,
    shares = 0,
    video = ""
)

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)

    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        // Начинаем загрузку
        thread {
            _data.postValue(FeedModel(loading = true))
            try {
                // Данные успешно получены
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (e: IOException) {
                // Получена ошибка
                FeedModel(error = true)
            }.also(_data::postValue)
        }
    }

    fun save() {
        edited.value?.let {
            thread {
                repository.save(it)
                _postCreated.postValue(Unit)
            }
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun changeVideo(video: String) {

        edited.value?.let {
            edited.value = it.copy(
                video = video,
            )
        }
    }

    fun likeById(post: Post) {
        thread {
            edited.postValue(
                edited.value?.copy(
                    likedByMe = !post.likedByMe
                )
            )

            if (!post.likedByMe) {
                repository.likeById(post.id)
                loadPosts()
            } else {
                repository.unlikeById(post.id)
                loadPosts()
            }
        }
    }

    fun removeById(id: Long) {
        thread {
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                )
            )
            try {
                repository.removeById(id)
            } catch (e: IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }
    }

}