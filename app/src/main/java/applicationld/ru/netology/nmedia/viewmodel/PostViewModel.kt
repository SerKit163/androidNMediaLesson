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
import okhttp3.Callback
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

class PostViewModel(application: Application) : AndroidViewModel(application) {
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
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

//    fun loadPosts() {
//        // Начинаем загрузку
//        thread {
//            _data.postValue(FeedModel(loading = true))
//            try {
//                // Данные успешно получены
//                println(1)
//                val posts = repository.getAll()
//                println(2)
//                FeedModel(posts = posts, empty = posts.isEmpty())
//            } catch (e: IOException) {
//                // Получена ошибка
//                FeedModel(error = true)
//            }.also(_data::postValue)
//        }
//    }

    fun save() {

        val old = _data.value?.posts.orEmpty()

        edited.value?.let {
            repository.saveAsync(object : PostRepository.Callback<Post> {
                override fun onSuccess(posts: Post) {
                    println(1)
                    _data.postValue(FeedModel(posts = old))
                }

                override fun onError(e: Exception) {
                    println(2)
                    _data.postValue(FeedModel(error = true))
                }
            })
            _postCreated.postValue(Unit)
        }
        edited.value = empty
    }

//    fun save() {
//        edited.value?.let {
//            thread {
//                repository.save(it)
//                _postCreated.postValue(Unit)
//            }
//        }
//        edited.value = empty
//    }

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
        val old = _data.value?.posts.orEmpty()
        if (!post.likedByMe) {
            repository.likeByIdAsync(post.id, object : PostRepository.Callback<Post> {
                override fun onSuccess(posts: Post) {
                    val newPost = old.map {
                        if (it.id == post.id) {
                            posts
                        } else {
                            it
                        }
                    }
                    _data.postValue(FeedModel(posts = newPost))
                }

                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }
            })
        } else {
            repository.unlikeByIdAsync(post.id, object : PostRepository.Callback<Post> {
                override fun onSuccess(posts: Post) {
                    val newPost = old.map {
                        if (it.id == post.id) {
                            posts
                        } else {
                            it
                        }
                    }
                    _data.postValue(FeedModel(posts = newPost))
                }
                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }
            })
        }
    }

//    fun likeById(post: Post) {
//        thread {
//            val old = _data.value?.posts.orEmpty()
//            val likePostId = old.map {
//                if (it.id == post.id) {
//                    if (!it.likedByMe) {
//                        repository.likeById(it.id)
//                    } else {
//                        repository.unlikeById(it.id)
//                    }
//                } else {
//                    it
//                }
//            }
//            _data.postValue(FeedModel(posts = likePostId))
//        }
//    }


    fun removeById(id: Long) {
        val old = _data.value?.posts.orEmpty()
        _data.postValue(FeedModel(posts = old.filter { it.id != id }))
        repository.removeByIdAsync(id, object : PostRepository.Callback<Unit> {
            override fun onSuccess(posts: Unit) {
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

//    fun removeById(id: Long) {
//        thread {
//            val old = _data.value?.posts.orEmpty()
//            _data.postValue(
//                _data.value?.copy(posts = old
//                    .filter { it.id != id }
//                )
//            )
//            try {
//                repository.removeById(id)
//            } catch (e: IOException) {
//                println(5)
//                _data.postValue(_data.value?.copy(posts = old))
//            }
//        }
//    }

}