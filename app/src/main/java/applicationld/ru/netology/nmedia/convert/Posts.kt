package applicationld.ru.netology.nmedia.convert

import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.sql.PostEntity

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    published = published,
    likeByMe = likeByMe,
    likeByMeCount = likeByMeCount,
    shareByMeCount = shareByMeCount,
    video = video
)

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    published = published,
    likeByMe = likeByMe,
    likeByMeCount = likeByMeCount,
    shareByMeCount = shareByMeCount,
    video = video
)