package applicationld.ru.netology.nmedia.data

import applicationld.ru.netology.nmedia.enumeration.AttachmentType

data class Post(
    val id: Long,
//    val authorId: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Long = 0,
    val shares: Long = 0,
//    val viewByMeCount: Long = 0,
    val video: String? = null,
    var attachment: Attachment? = null
)

data class Attachment(
    val url: String,
    val description: String,
    val type: AttachmentType
)

data class Comment(
    val id: Long,
    val postId: Long,
    val authorId: Long,
    val content: String,
    val published: Long,
    val likedById: Boolean,
    val likes: Int = 0
)

data class Author(
    val id: Long,
    val name: String,
    val avatar: String
)