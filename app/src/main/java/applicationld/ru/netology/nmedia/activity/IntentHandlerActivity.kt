package applicationld.ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.databinding.ActivityIntentHandlerBinding
import com.google.android.material.snackbar.Snackbar

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.action == Intent.ACTION_SEND) {

            val text = intent.getStringExtra(Intent.EXTRA_TEXT)

            if (text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.blank_message_error),
                    Snackbar.LENGTH_SHORT
                ).apply {
                    setAction(android.R.string.ok) {
                        finish()
                    }
                }.show()
            }
            binding.textSend.text = text
        }
    }
}