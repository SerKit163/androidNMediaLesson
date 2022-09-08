package applicationld.ru.netology.nmedia.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import applicationld.ru.netology.nmedia.R
import applicationld.ru.netology.nmedia.adapter.OnClickMainListener
import applicationld.ru.netology.nmedia.adapter.PostViewHolder
import applicationld.ru.netology.nmedia.data.Post
import applicationld.ru.netology.nmedia.databinding.FragmentPostCardBinding
import applicationld.ru.netology.nmedia.fragment.FeedFragment.Companion.textArg
import applicationld.ru.netology.nmedia.fragment.FeedFragment.Companion.videoArg
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel

class PostCardFragment : Fragment() {

//    lateinit var binding: FragmentPostCardBinding
//
//    private val viewModel: PostViewModel by viewModels(
//        ownerProducer = ::requireParentFragment
//    )
//
//    val args by navArgs<PostCardFragmentArgs>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentPostCardBinding.inflate(
//            inflater,
//            container,
//            false
//        )
//
//        val viewHolder = PostViewHolder(binding.postCardFragment, object : OnClickMainListener {
//            override fun onLike(post: Post) {
//                viewModel.onLikeClicked(post.id)
//            }
//
//            override fun onShare(post: Post) {
//                viewModel.onShareClicked(post.id)
//
//                val intent = Intent(Intent.ACTION_SEND).apply {
//                    putExtra(Intent.EXTRA_TEXT, post.content)
//                    type = "text/plain"
//                }
//                val shareIntent = Intent.createChooser(intent, getString(R.string.share))
//                startActivity(shareIntent)
//            }
//
//            override fun onRemove(post: Post) {
//                viewModel.onRemoveClicked(post.id)
//            }
//
//            override fun onEdit(post: Post) {
//                viewModel.edit(post)
//                findNavController().navigate(R.id.action_postCardFragment_to_newPostFragment, Bundle().apply {
//                    textArg = post.content
//                    videoArg = post.video
//                })
//            }
//
//            override fun onVideo(post: Post) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                val choose = Intent.createChooser(intent, null)
//                startActivity(choose)
//            }
//
//            override fun onDetail(post: Post) {
//                return
//            }
//
//        })
//
//
//        viewModel.data.observe(viewLifecycleOwner) { posts ->
//            val post = posts.find { it.id == args.postId.toLong() } ?: run {
//                findNavController().navigateUp()
//                return@observe
//            }
//            viewHolder.bind(post)
//        }
//
//        return binding.root
//    }
}