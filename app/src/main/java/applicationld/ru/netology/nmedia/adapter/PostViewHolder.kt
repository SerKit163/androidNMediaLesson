package applicationld.ru.netology.nmedia.adapter

import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.databinding.PostCardBinding
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onClickMainListener: OnClickMainListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            titleHeader.text = post.author
            dataHeader.text = post.published
            textContent.text = post.content

            ibLike.text = countString(post.likeByMeCount)
            ibShare.text = countString(post.shareByMeCount)

            ibLike.isChecked = post.likeByMe

//            ibLike.setImageResource(
//                if (post.likeByMe) R.drawable.ic_like_on_24 else R.drawable.ic_like_border_24
//            )

            if (!post.video.isNullOrBlank()) {
                videoGroup.visibility = View.VISIBLE
            }

            ibLike.setOnClickListener {
                onClickMainListener.onLike(post)
            }

            ibShare.setOnClickListener {
                onClickMainListener.onShare(post)
            }

            ibVideo.setOnClickListener {
                onClickMainListener.onVideo(post)
            }

            ibVideoIcon.setOnClickListener {
                onClickMainListener.onVideo(post)
            }

            menuHeader.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onClickMainListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onClickMainListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

        }
    }

    private fun countString(count: Long): String {

        if (count in 1000..9999) {
            val num = (count.toDouble() / 1000).toBigDecimal().setScale(1, RoundingMode.FLOOR)
            return "${DecimalFormat("#.######").format(num)}K"
        }

        if (count in 10000..999999) {
            val num = (count / 1000)
            return "${num}K"
        }

        if (count in 999999..999999999) {
            val num = (count / 1000000)
            return "${num}M"
        }

        if (count > 999999999) {
            val num = (count.toDouble() / 1000000).toBigDecimal().setScale(1, RoundingMode.FLOOR)
            return "${DecimalFormat("#.######").format(num)}M"
        }
        return count.toString()
    }
}