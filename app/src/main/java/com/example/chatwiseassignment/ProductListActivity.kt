package com.example.chatwiseassignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatwiseassignment.adapter.ProductAdapter
import com.example.chatwiseassignment.databinding.ActivityProductListBinding
import com.example.chatwiseassignment.model.Product
import com.example.chatwiseassignment.model.ProductResponse
import com.example.chatwiseassignment.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )

        setupRecyclerView()
        fetchProducts()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchProducts() {
        RetrofitClient.apiService.getProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    val products = response.body()?.products ?: emptyList()
                    val items = listOf("Products List") + products
                    productAdapter = ProductAdapter(items) { product ->
                        // Handle item click, navigate to ProductDetailActivity
                        val intent = Intent(this@ProductListActivity, ProductDetailActivity::class.java)
                        intent.putExtra("PRODUCT", product)
                        startActivity(intent)
                    }
                    binding.recyclerViewProducts.adapter = productAdapter
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
