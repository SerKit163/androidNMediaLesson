package applicationld.ru.netology.nmedia.old

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import applicationld.ru.netology.nmedia.fragment.EditPostFragment

//class EditPostResultContract: ActivityResultContract<PostParam, List<String?>>() {
//    override fun createIntent(context: Context, input: PostParam): Intent {
//        val intentEditPost = Intent(context, EditPostActivity::class.java)
//        return intentEditPost.apply {
//            putExtra(Intent.EXTRA_TEXT, PostParam(textContent = input.textContent))
//            putExtra(Intent.EXTRA_HTML_TEXT, postParam.linkVideo)
//        }
//    }
//
//    override fun parseResult(resultCode: Int, intent: Intent?): List<String?> =
//        listOf(
//            if (resultCode == Activity.RESULT_OK) {
//                intent?.getStringExtra(Intent.EXTRA_TEXT)
//            } else {
//                null
//            },
//            if (resultCode == Activity.RESULT_OK) {
//                intent?.getStringExtra(Intent.EXTRA_HTML_TEXT)
//            } else {
//                null
//            }
//        )
//}


class EditPostResultContract: ActivityResultContract<ArrayList<String>,  List<String?>>() {
    override fun createIntent(context: Context, input: ArrayList<String>): Intent {
        val intentEditPost = Intent(context, EditPostFragment::class.java)
        return intentEditPost.apply {
            putExtra(Intent.EXTRA_TEXT, input[0])
            putExtra(Intent.EXTRA_HTML_TEXT, input[1])
        }
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
