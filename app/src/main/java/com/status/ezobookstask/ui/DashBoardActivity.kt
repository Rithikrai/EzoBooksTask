package com.status.ezobookstask.ui

import android.app.ProgressDialog
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.Target
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import coil.Coil

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestListener
import com.squareup.picasso.Picasso
import com.status.ezobookstask.R
import com.status.ezobookstask.databinding.ActivityDashBoardBinding
import com.status.ezobookstask.entity.PicsumModelResponse
import com.status.ezobookstask.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class DashBoardActivity : AppCompatActivity() {
    lateinit var progress: ProgressDialog
    lateinit var binding: ActivityDashBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board)
        progress = ProgressDialog(this)
        binding.authrname.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                validateUser()
            }
        }
    }

    fun validateUser() {

        RetrofitClient.instance.login(
            1
        )?.enqueue(object :
            Callback,
            retrofit2.Callback<PicsumModelResponse?> {
            override fun onResponse(
                call: Call<PicsumModelResponse?>,
                response: Response<PicsumModelResponse?>
            ) {
                progress.dismiss()
                if (response.isSuccessful) {
                    val images = response.body()
                    if (!images.isNullOrEmpty()) {
                        val firstAuthorName = images[0].author
                        binding.authorName.text = "Author Name $firstAuthorName"


                        Glide.with(this@DashBoardActivity)
                            .load(images[0].url)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .downsample(DownsampleStrategy.AT_MOST)
                            .placeholder(R.drawable.logo)
                            .into(binding.imageFullScreen)

                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<PicsumModelResponse?>, t: Throwable) {
                progress.dismiss()
            }
        })
    }
}