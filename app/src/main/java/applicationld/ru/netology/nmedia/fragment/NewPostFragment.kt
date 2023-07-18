package applicationld.ru.netology.nmedia.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import applicationld.ru.netology.nmedia.fragment.FeedFragment.Companion.textArg
import applicationld.ru.netology.nmedia.databinding.FragmentNewPostBinding
import applicationld.ru.netology.nmedia.fragment.FeedFragment.Companion.videoArg
import applicationld.ru.netology.nmedia.util.AndroidUtils
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel

const val POST_DRAFT = "post_draft"
const val CONTENT_POST_DRAFT = "content_post_draft"
const val VIDEO_POST_DRAFT = "video_post_draft"

class NewPostFragment : Fragment() {
    lateinit var binding: FragmentNewPostBinding
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )

        val prefs = requireActivity().getSharedPreferences(POST_DRAFT, Context.MODE_PRIVATE)

//        Button previous
        requireActivity().onBackPressedDispatcher.addCallback(this) {

            if (!binding.textPost.text.isNullOrBlank()) {
                prefs.edit().apply {
                    putString(CONTENT_POST_DRAFT, binding.textPost.text.toString())
                    putString(VIDEO_POST_DRAFT, binding.videoPost.text.toString())
                    apply()
                }
            }
            findNavController().navigateUp()
        }

            with(binding) {
                textPost.requestFocus()

                binding.textPost.setText(prefs.getString(CONTENT_POST_DRAFT, null))
                binding.videoPost.setText(prefs.getString(VIDEO_POST_DRAFT, null))

                arguments?.apply {
                    textArg?.let(textPost::setText)
                    videoArg?.let(videoPost::setText)
                }

                btnAdd.setOnClickListener {
                    viewModel.changeContent(textPost.text.toString())
                    viewModel.save()
                    AndroidUtils.hideKeyboard(requireView())

//                  Что бы кнопка исчезала
                    viewModel.postCreatedLoading.observe(viewLifecycleOwner) {
                        binding.btnAdd.isEnabled = !it
                    }

                }

                viewModel.postCreated.observe(viewLifecycleOwner) {
                    viewModel.loadPosts()
                    findNavController().navigateUp()
                }

            }
        return binding.root
    }
}