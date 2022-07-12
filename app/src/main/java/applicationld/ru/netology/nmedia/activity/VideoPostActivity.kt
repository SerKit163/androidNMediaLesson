package applicationld.ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.databinding.ActivityVideoPostBinding

class VideoPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVideoPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        with(binding) {
//            btnAdd.setOnClickListener {
//                if (textPost.text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@NewPostActivity,
//                        getString(R.string.blank_message_error),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    setResult(Activity.RESULT_CANCELED)
//                } else {
//                    setResult(
//                        Activity.RESULT_OK,
//                        Intent().apply {
//                            putExtra(Intent.EXTRA_TEXT, textPost.text.toString())
//                            putExtra(Intent.EXTRA_ORIGINATING_URI, videoPost.text.toString())
//                        }
//                    )
//                }
//                finish()
//            }
//        }
    }
}