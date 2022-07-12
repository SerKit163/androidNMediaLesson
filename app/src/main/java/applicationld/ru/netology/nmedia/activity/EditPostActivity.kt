package applicationld.ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editTextPost = intent.getStringExtra(Intent.EXTRA_TEXT)
        val editVideoPost = intent.getStringExtra(Intent.EXTRA_HTML_TEXT)

        with(binding) {
            textEditPost.setText(editTextPost)
            videoPostEdit.setText(editVideoPost)

            btnAdd.setOnClickListener {

                if (textEditPost.text.isNullOrBlank()) {
                    Toast.makeText(
                        this@EditPostActivity,
                        getString(R.string.blank_message_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(Activity.RESULT_CANCELED)
                } else {
                    setResult(
                        Activity.RESULT_OK,
                        Intent().apply {
                            putExtra(Intent.EXTRA_TEXT, textEditPost.text.toString())
                            putExtra(Intent.EXTRA_HTML_TEXT, videoPostEdit.text.toString())
                        }
                    )
                }
                finish()
            }
        }
    }
}