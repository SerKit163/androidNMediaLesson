package applicationld.ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView
import applicationld.ru.netology.nmedia.Post
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.databinding.PostCardBinding
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

            txLike.text = countString(post.likeByMeCount)
            txShare.text = countString(post.shareByMeCount)

            ibLike.setImageResource(
                if (post.likeByMe) R.drawable.ic_like_on_24 else R.drawable.ic_like_border_24
            )

            ibLike.setOnClickListener {
                onClickMainListener.onLiked(post.id)
            }

            ibShare.setOnClickListener {
                onClickMainListener.onShared(post.id)
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