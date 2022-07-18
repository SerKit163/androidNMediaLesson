package applicationld.ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.data.PostRepository
import applicationld.ru.netology.nmedia.data.PostRepositoryInMemoryImpl
import applicationld.ru.netology.nmedia.param.PostParam

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

//        edited.value?.let {
//            val text = content.trim()
//            if (it.content == text) {
//                return
//            }
//            edited.value = it.copy(content = text)
//        }
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

//    override fun onLike(post: Post) {
//        repository.likeById(post.id)
//    }
//
//    override fun onShare(post: Post) {
//        repository.shareById(post.id)
//        val intent = Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_TEXT, post.content)
//            type = "text/plain"
//        }
//
//        val shareIntent = Intent.createChooser(intent, null)
//        startActivity(shareIntent)
//
//
//    override fun onRemove(post: Post) {
//        repository.removeById(post.id)
//    }
//
//    override fun onEdit(post: Post) {
//        edit(post)
//    }

    fun onLikeClicked(id: Long) = repository.likeById(id)
    fun onShareClicked(id: Long) = repository.shareById(id)
    fun onRemoveClicked(id: Long) = repository.removeById(id)


}