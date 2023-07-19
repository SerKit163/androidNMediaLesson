package applicationld.ru.netology.nmedia.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.model.FeedModel
import applicationld.ru.netology.nmedia.repository.PostRepository
import applicationld.ru.netology.nmedia.repository.PostRepositoryImpl
import applicationld.ru.netology.nmedia.util.SingleLiveEvent
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
//    authorId = 0,
    author = "",
    authorAvatar = "",
    content = "",
    published = "",
    likedByMe = false,
    likes = 0,
    shares = 0,
    video = "",
    attachment = null
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

    private val _postCreatedError = SingleLiveEvent<Unit>()
    val postCreatedError: LiveData<Unit>
        get() = _postCreatedError

    private val _postCreatedLoading = MutableLiveData(false)
    val postCreatedLoading: LiveData<Boolean>
        get() = _postCreatedLoading

    init {
        loadPosts()
    }

    fun loadPosts() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(posts: List<Post>) {
                _data.value = FeedModel(posts = posts, empty = posts.isEmpty())
            }

            override fun onError(e: Exception) {
                _data.value = FeedModel(error = true)
            }
        })
    }

    fun save() {
        val old = _data.value?.posts.orEmpty()
        edited.value?.let {
            try {
                _postCreatedLoading.value = true
                repository.saveAsync(it, object : PostRepository.Callback<Post> {
                    override fun onSuccess(posts: Post) {
                        _data.value = FeedModel(posts = listOf(posts) + old)
                        _postCreated.value = Unit
                    }

                    override fun onError(e: Exception) {
                        _data.value = _data.value?.copy(errorService = true)
//                        _data.value = FeedModel(errorService = true)
//                    _data.postValue(FeedModel(error = true))
                    }
                })
//                _postCreated.value = Unit
            } catch (e: Exception) {
                _postCreatedError.value = Unit
            } finally {
                _postCreatedLoading.value = false
            }

//            Это код исходный

//            repository.saveAsync(it, object : PostRepository.Callback<Post> {
//                override fun onSuccess(posts: Post) {
//                    _data.value = FeedModel(posts = listOf(posts) + old)
//                    _postCreated.value = Unit
//                }
//
//                override fun onError(e: Exception) {
//                    _data.value = FeedModel(errorService = true)
////                    _data.postValue(FeedModel(error = true))
//                }
//            })

        }
        edited.value = empty
//        edited.postValue(empty)
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
                    _data.value = FeedModel(posts = newPost)
                }

                override fun onError(e: Exception) {
                    _data.value = _data.value?.copy(errorService = true)
//                    _data.value = FeedModel(errorService = true)
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
                    _data.value = FeedModel(posts = newPost)
                }
                override fun onError(e: Exception) {
                    _data.value = _data.value?.copy(errorService = true)
//                    _data.value = FeedModel(errorService = true)
                }
            })
        }
    }


    fun removeById(id: Long) {
        val old = _data.value?.posts.orEmpty()
        _data.postValue(FeedModel(posts = old.filter { it.id != id }))
        repository.removeByIdAsync(id, object : PostRepository.Callback<Unit> {
            override fun onSuccess(posts: Unit) {
            }

            override fun onError(e: Exception) {
                _data.value = FeedModel(posts = old)
            }
        })
    }

}