package applicationld.ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.adapter.OnClickMainListener
import applicationld.ru.netology.nmedia.adapter.PostsAdapter
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.databinding.ActivityMainBinding
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {


    private val viewModel: PostViewModel by viewModels()

    private val newPostLauncher = registerForActivityResult(NewPostResultContract()) {
        val resultContent = it[0] ?: return@registerForActivityResult
        val resultVideo = it[1] ?: return@registerForActivityResult
        viewModel.changeContent(resultContent)
        viewModel.changeVideo(resultVideo)
        viewModel.save()
    }

    private val editPostLauncher = registerForActivityResult(EditPostResultContract()) {
        val resultContent = it[0] ?: return@registerForActivityResult
        val resultVideo = it[1] ?: return@registerForActivityResult
        viewModel.changeContent(resultContent)
        viewModel.changeVideo(resultVideo)
        viewModel.save()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(object : OnClickMainListener {
            override fun onLike(post: Post) {
                viewModel.onLikeClicked(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.onShareClicked(post.id)

                val intent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.share))
                startActivity(shareIntent)
            }

            override fun onRemove(post: Post) {
                viewModel.onRemoveClicked(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                val arrayInput = ArrayList<String>()
                post.content.let { arrayInput.add(it) }
                post.video?.let { arrayInput.add(it) }
//                editPostLauncher.launch(post.content)
                editPostLauncher.launch(arrayInput)
            }

            override fun onVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                val choose = Intent.createChooser(intent, null)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(choose)
                }
            }

        })

        binding.list.adapter = adapter

        binding.btnAddMain.setOnClickListener {
            newPostLauncher.launch()
        }

//        viewModel.edited.observe(this) {
//            if (it.id == 0L) {
//                return@observe
//            }
//
//            with(binding.content) {
//                setText(it.content)
//                requestFocus()
//                AndroidUtils.showKeyboard(this)
//            }
//
//            with(binding) {
//                viewEditText.text = it.content
//                groupViewEdit.visibility = View.VISIBLE
//
//                closeEdit.setOnClickListener {
//                    viewModel.close()
//                    closeEdit(binding)
//
//                    content.setText("")
//                    content.clearFocus()
//                    AndroidUtils.hideKeyboard(content)
//                }
//            }
//        }

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

    }

//    private fun closeEdit(binding: ActivityMainBinding) {
//        binding.groupViewEdit.visibility = View.GONE
//        binding.viewEditText.text = ""
//    }
}
