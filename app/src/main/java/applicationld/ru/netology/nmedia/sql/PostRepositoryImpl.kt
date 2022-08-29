package applicationld.ru.netology.nmedia.sql

import androidx.lifecycle.Transformations
import applicationld.ru.netology.nmedia.convert.toEntity
import applicationld.ru.netology.nmedia.convert.toModel
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.data.PostRepository

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
//            it.toDto()
            Post(
                it.id,
                it.author,
                it.content,
                it.published,
                it.likeByMe,
                it.likeByMeCount,
                it.shareByMeCount,
                it.video
            )
        }
    }


    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
//        dao.save(post.toEntity())
    }

}