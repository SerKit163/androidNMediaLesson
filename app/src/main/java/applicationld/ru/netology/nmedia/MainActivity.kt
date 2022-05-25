package applicationld.ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import applicationld.ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!",
            published = "21 мая в 21:00",
            likeByMe = false,
            likeByMeCount = 999,
            shareByMeCount = 1999
        )

        with(binding) {
            titleHeader.text = post.author
            dataHeader.text = post.published
            textContent.text = post.content

            var countLike = post.likeByMeCount
            txLike.text = countString(countLike)

            var countShare = post.shareByMeCount
            txShare.text = countString(countShare)

            if (post.likeByMe) {
                ibLike.setImageResource(R.drawable.ic_like_on_24)
            }

            ibLike.setOnClickListener {
                post.likeByMe = !post.likeByMe
                ibLike.setImageResource(
                    if (post.likeByMe) {
                        R.drawable.ic_like_on_24
                    } else {
                        R.drawable.ic_like_border_24
                    }
                )

                if (post.likeByMe) {
                    countLike++
                } else {
                    countLike--
                }
                txLike.text = countString(countLike)
            }

            ibShare.setOnClickListener {
                txShare.text = countString(countShare++)
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
