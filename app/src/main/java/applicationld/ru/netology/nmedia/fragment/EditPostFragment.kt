package applicationld.ru.netology.nmedia.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import applicationld.ru.netology.nmedia.databinding.FragmentEditPostBinding
import applicationld.ru.netology.nmedia.util.AndroidUtils
import applicationld.ru.netology.nmedia.viewmodel.PostViewModel

class EditPostFragment : Fragment() {

    lateinit var binding: FragmentEditPostBinding

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )

        with(binding) {
            textEditPost.requestFocus()

//            arguments?.textArg?.let (
//                textEditPost::setText
//            )

            btnAdd.setOnClickListener {
                val intent = Intent()

                if (android.text.TextUtils.isEmpty(binding.textEditPost.text)) {
                    activity?.setResult(android.app.Activity.RESULT_CANCELED, intent)
                } else {
                    val content = binding.textEditPost.text.toString()
                    intent.putExtra(Intent.EXTRA_TEXT, content)
                    activity?.setResult(android.app.Activity.RESULT_OK, intent)
                    val post = viewModel.data.value
//                    viewModel.edit(post)
//                    viewModel.changeContent(binding.textEditPost.text.toString())
//                    viewModel.save()
                    AndroidUtils.hideKeyboard(requireView())
                }


                findNavController().navigateUp()
            }
        }

        return binding.root
    }

//    companion object {
//        var Bundle.textArg: String? by StringArg
//    }

/**
 * Используем когда нету Принцип DRY (object)
//    companion object {
//        private const val TEXT_KEY = "TEXT_KEY"
//        var Bundle.textArg: String?
//            set(value) = putString(TEXT_KEY, value)
//            get() = getString(TEXT_KEY)
//    }
*/
}