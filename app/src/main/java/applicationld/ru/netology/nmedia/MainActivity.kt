package applicationld.ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import applicationld.ru.netology.nmedia.adapter.OnClickMainListener
import applicationld.ru.netology.nmedia.adapter.PostsAdapter
import applicationld.ru.netology.nmedia.databinding.ActivityMainBinding
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnClickMainListener {
            override fun onLiked(id: Long) {
                return viewModel.onLikeClicked(id)
            }

            override fun onShared(id: Long) {
                return viewModel.onShareClicked(id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}
