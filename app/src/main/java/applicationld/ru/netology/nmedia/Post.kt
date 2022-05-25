package applicationld.ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likeByMe: Boolean = false,
    var likeByMeCount: Long = 0,
    var shareByMeCount: Long = 0,
    var viewByMeCount: Long = 0
)