package applicationld.ru.netology.nmedia.sql

import androidx.room.Entity
import androidx.room.PrimaryKey
import applicationld.ru.netology.nmedia.data.Post


@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likeByMe: Boolean = false,
    val likeByMeCount: Long = 0,
    val shareByMeCount: Long = 0,
    val video: String? = null
) {
    fun toDto() = Post(id, author, content, published, likeByMe, likeByMeCount, shareByMeCount, video)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.author,
                dto.content,
                dto.published,
                dto.likedByMe,
                dto.likes,
                dto.shares,
                dto.video
            )
    }
}
