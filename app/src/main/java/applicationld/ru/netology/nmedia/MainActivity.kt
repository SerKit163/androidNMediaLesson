package applicationld.ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import applicationld.ru.netology.nmedia.databinding.ActivityMainBinding
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->
            with(binding) {
                titleHeader.text = post.author
                dataHeader.text = post.published
                textContent.text = post.content

                txLike.text = countString(post.likeByMeCount)
                txShare.text = countString(post.shareByMeCount)

                ibLike.setImageResource(
                    if (post.likeByMe) R.drawable.ic_like_on_24 else R.drawable.ic_like_border_24
                )

                ibLike.setOnClickListener {
                    viewModel.onLikeClicked()
                }

                ibShare.setOnClickListener {
                    viewModel.onShareClicked()
                }
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
