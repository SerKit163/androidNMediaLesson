package applicationld.ru.netology.nmedia.image

import android.app.Activity
import android.widget.ImageView
import applicationld.ru.netology.nmedia.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions




fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.baseline_downloading_24)
        .error(R.drawable.baseline_error_outline_24)
        .circleCrop()
        .timeout(30_000)
        .into(this)
}

//fun imageGlide(url: String, activity: Activity) {
//    Glide.with(activity)
//        .load(url)
//        .placeholder(R.drawable.baseline_downloading_24)
//        .error(R.drawable.baseline_error_outline_24)
//        .timeout(30_000)
//        .into(activity)
//}