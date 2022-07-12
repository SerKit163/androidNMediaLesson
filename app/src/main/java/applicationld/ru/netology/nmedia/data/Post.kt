package applicationld.ru.netology.nmedia.data

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likeByMe: Boolean = false,
    val likeByMeCount: Long = 0,
    val shareByMeCount: Long = 0,
    val viewByMeCount: Long = 0,
    val video: String? = null
)