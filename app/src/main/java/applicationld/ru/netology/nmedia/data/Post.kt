package applicationld.ru.netology.nmedia.data

import applicationld.ru.netology.nmedia.enumeration.AttachmentType

data class Post(
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likes: Long = 0,
    val shares: Long = 0,
//    val viewByMeCount: Long = 0,
    val video: String? = null,
    var attachment: Attachment? = null

)

data class Attachment(
    val url: String,
    val description: String?,
    val type: AttachmentType
)