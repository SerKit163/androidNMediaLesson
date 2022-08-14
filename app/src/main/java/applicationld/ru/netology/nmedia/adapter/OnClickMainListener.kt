package applicationld.ru.netology.nmedia.adapter

import applicationld.ru.netology.nmedia.data.Post

interface OnClickMainListener {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onVideo(post: Post)
    fun onDetail(post: Post)
}