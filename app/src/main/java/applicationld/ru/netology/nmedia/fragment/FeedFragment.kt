package applicationld.ru.netology.nmedia.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.adapter.OnClickMainListener
import applicationld.ru.netology.nmedia.adapter.PostsAdapter
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.databinding.FragmentFeedBinding
import applicationld.ru.netology.nmedia.util.StringArg
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostsAdapter(object : OnClickMainListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.share))
                startActivity(shareIntent)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
//                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment, Bundle().apply {
//                    textArg = post.content
//                    videoArg = post.video
//                })

            }

            override fun onVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                val choose = Intent.createChooser(intent, null)
                startActivity(choose)
            }

            override fun onDetail(post: Post) {
//                val action = FeedFragmentDirections.actionFeedFragmentToPostCardFragment(post.id.toInt())
//                findNavController().navigate(action)

//                findNavController().navigate(R.id.action_feedFragment_to_postCardFragment, Bundle().apply { idArg = post.id })
            }

        })

        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, { state ->
            adapter.submitList(state.posts)
            binding.progress.isVisible = state.loading
            binding.errorGroup.isVisible = state.error
            binding.emptyText.isVisible = state.empty
        })

        binding.retryButton.setOnClickListener {
            viewModel.loadPosts()
        }

        binding.btnAddMain.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
        var Bundle.videoArg: String? by StringArg
//        var Bundle.idArg: Long by LongArg
    }

}
