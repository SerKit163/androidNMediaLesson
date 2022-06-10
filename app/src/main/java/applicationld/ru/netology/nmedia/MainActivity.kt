package applicationld.ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import applicationld.ru.netology.nmedia.adapter.OnClickMainListener
import applicationld.ru.netology.nmedia.adapter.PostsAdapter
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.databinding.ActivityMainBinding
import applicationld.ru.netology.nmedia.util.AndroidUtils
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(object : OnClickMainListener {
            override fun onLike(post: Post) {
                viewModel.onLikeClicked(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.onShareClicked(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.onRemoveClicked(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_edit),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString().trim())
                viewModel.save()

                closeEdit(binding)

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        binding.list.adapter = adapter

        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }

            with(binding.content) {
                setText(it.content)
                requestFocus()
                AndroidUtils.showKeyboard(this)
            }

            with(binding) {
                viewEditText.text = it.content
                groupViewEdit.visibility = View.VISIBLE

                closeEdit.setOnClickListener {
                    closeEdit(binding)

                    content.setText("")
                    content.clearFocus()
                    AndroidUtils.hideKeyboard(content)
                }
            }
        }

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

    }

    private fun closeEdit(binding: ActivityMainBinding) {
        binding.groupViewEdit.visibility = View.GONE
        binding.viewEditText.text = ""
    }
}
