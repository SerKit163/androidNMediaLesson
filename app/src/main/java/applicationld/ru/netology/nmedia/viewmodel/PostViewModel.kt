package applicationld.ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import applicationld.ru.netology.nmedia.data.PostRepository
import applicationld.ru.netology.nmedia.data.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data get() = repository.data
    fun onLikeClicked() = repository.like()
    fun onShareClicked() = repository.share()
}