package com.example.chatwiseassignment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.chatwiseassignment.databinding.ActivityProductDetailBinding
import com.example.chatwiseassignment.model.Product

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )

        val product = intent.getParcelableExtra<Product>("PRODUCT")

        product?.let {
            binding.tvProductTitle.text = it.title
            binding.tvProductPrice.text = "$${it.price}"
            Glide.with(this)
                .load(it.thumbnail)
                .into(binding.ivProductImage)
            // Placeholder description
            binding.tvProductDescription.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio."
        }
    }
}
