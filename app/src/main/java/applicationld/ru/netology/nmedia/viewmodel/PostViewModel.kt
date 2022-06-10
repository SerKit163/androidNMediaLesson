package applicationld.ru.netology.nmedia.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    viewByMeCount = 0
)

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data = repository.getAll()

    fun onLikeClicked(id: Long) = repository.likeById(id)
    fun onShareClicked(id: Long) = repository.shareById(id)
    fun onRemoveClicked(id: Long) = repository.removeById(id)

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
            edited.value = it.copy(content = content)
        }

//        edited.value?.let {
//            val text = content.trim()
//            if (it.content == text) {
//                return
//            }
//            edited.value = it.copy(content = text)
//        }
    }

    fun close() {
        edited.value = empty
    }


}