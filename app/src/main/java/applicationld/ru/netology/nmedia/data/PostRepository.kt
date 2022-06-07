package applicationld.ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import applicationld.ru.netology.nmedia.Post

interface PostRepository {
//    val data: LiveData<Post>
//    fun like()

    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)

//    fun share()
}