package applicationld.ru.netology.nmedia.sql

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("""
        UPDATE PostEntity
        SET content = :content, video = :video
        WHERE id = :id
        """)
    fun updateContentById(id: Long, content: String, video: String)

    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentById(
            post.id,
            post.content,
            post.video ?: ""
        )

    @Query("""
        UPDATE PostEntity
        SET likeByMeCount = likeByMeCount + CASE WHEN likeByMe THEN -1 ELSE 1 END,
        likeByMe = CASE WHEN likeByMe THEN 0 ELSE 1 END
        WHERE id = :id
    """)
    fun likeById(id: Long)

    @Query("""
        DELETE FROM PostEntity WHERE id = :id
    """)
    fun removeById(id: Long)

    @Query("""
        UPDATE PostEntity
        SET shareByMeCount = shareByMeCount + 1
        WHERE id = :id
    """)
    fun shareById(id: Long)

}