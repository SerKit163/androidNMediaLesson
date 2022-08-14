package applicationld.ru.netology.nmedia.old

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import applicationld.ru.netology.nmedia.fragment.NewPostFragment

class NewPostResultContract: ActivityResultContract<Unit, List<String?>>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, NewPostFragment::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<String?> =
        listOf(
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_TEXT)
            } else {
                null
            },
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_HTML_TEXT)
            } else {
                null
            }
        )
}