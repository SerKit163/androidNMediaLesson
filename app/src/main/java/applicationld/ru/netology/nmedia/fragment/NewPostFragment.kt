package applicationld.ru.netology.nmedia.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import applicationld.ru.netology.nmedia.fragment.FeedFragment.Companion.textArg
import applicationld.ru.netology.nmedia.databinding.FragmentNewPostBinding
import applicationld.ru.netology.nmedia.fragment.FeedFragment.Companion.videoArg
import applicationld.ru.netology.nmedia.util.AndroidUtils
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel

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

        with(binding) {
            textPost.requestFocus()

            arguments?.apply {
                textArg?.let(textPost::setText)
                videoArg?.let(videoPost::setText)
            }

            btnAdd.setOnClickListener {

                if (!textPost.text.isNullOrBlank()) {
                    viewModel.changeContent(textPost.text.toString())
                    viewModel.changeVideo(videoPost.text.toString())
                    viewModel.save()
                    AndroidUtils.hideKeyboard(requireView())
                }
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}