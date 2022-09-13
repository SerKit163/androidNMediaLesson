package applicationld.ru.netology.nmedia.data

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likes: Long = 0,
    val shares: Long = 0,
//    val viewByMeCount: Long = 0,
    val video: String? = null
)