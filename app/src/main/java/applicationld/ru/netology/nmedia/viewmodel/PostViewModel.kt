package applicationld.ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import applicationld.ru.netology.nmedia.data.PostRepository
import applicationld.ru.netology.nmedia.data.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data = repository.getAll()
    fun onLikeClicked(id: Long) = repository.likeById(id)
    fun onShareClicked(id: Long) = repository.shareById(id)

}