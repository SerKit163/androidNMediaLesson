package applicationld.ru.netology.nmedia.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import applicationld.ru.netology.nmedia.fragment.FeedFragment.Companion.textArg
import applicationld.ru.netology.nmedia.databinding.FragmentNewPostBinding
import applicationld.ru.netology.nmedia.fragment.FeedFragment.Companion.videoArg
import applicationld.ru.netology.nmedia.util.AndroidUtils
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel

const val TEXT_POST_DRAFT = "draft_post"

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

        val prefs = requireActivity().getSharedPreferences(TEXT_POST_DRAFT, Context.MODE_PRIVATE)

        requireActivity().onBackPressedDispatcher.addCallback(this) {

            if (!binding.textPost.text.isNullOrBlank()) {
                prefs.edit().apply {
                    putString(TEXT_POST_DRAFT, binding.textPost.text.toString())
                    apply()
                }
            }
            findNavController().navigateUp()
        }

            with(binding) {
                textPost.requestFocus()

                arguments?.apply {
                    textArg?.let(textPost::setText)
                    videoArg?.let(videoPost::setText)
                }

                binding.textPost.setText(prefs.getString(TEXT_POST_DRAFT, null))

                btnAdd.setOnClickListener {
                    if (!textPost.text.isNullOrBlank()) {
                        viewModel.changeContent(textPost.text.toString())
                        viewModel.changeVideo(videoPost.text.toString())
                        viewModel.save()
                        AndroidUtils.hideKeyboard(requireView())
                        prefs.edit().apply {
                            putString(TEXT_POST_DRAFT, "")
                            apply()
                        }
                    }

                    findNavController().navigateUp()
                }
            }
        return binding.root
    }
}