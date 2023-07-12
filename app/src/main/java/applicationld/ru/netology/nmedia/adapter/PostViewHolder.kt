package applicationld.ru.netology.nmedia.adapter

import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.databinding.PostCardBinding
import applicationld.ru.netology.nmedia.image.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

            val urlAvatars = "http://10.0.2.2:9999/avatars/${post.authorAvatar}"
            val urlPostImage = "http://10.0.2.2:9999/images/${post.attachment?.url}"

            iconHeader.load(urlAvatars)

            Glide.with(binding.postImage)
                .load(urlPostImage)
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_error_outline_24)
                .timeout(30_000)
                .into(binding.postImage)


            textImage.text = post.attachment?.description


            ibLike.text = countString(post.likes)
            ibShare.text = countString(post.shares)

            ibLike.isChecked = post.likedByMe

            if (post.video.isNullOrBlank()) {
                videoGroup.visibility = View.GONE
            } else {
                videoGroup.visibility = View.VISIBLE
            }

            if (post.attachment?.url.isNullOrBlank()) {
                imageGroup.visibility = View.GONE
            } else {
                imageGroup.visibility = View.VISIBLE
            }

            root.setOnClickListener {
                onClickMainListener.onDetail(post)
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