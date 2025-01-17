package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.myapplication.Adapter.BrandAdapter
import com.example.myapplication.Adapter.PopularAdapter
import com.example.myapplication.Model.SliderModel
import com.example.myapplication.Adapter.SliderAdapter
import com.example.myapplication.ViewModel.MainViewModel
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initBanner()
        initBrand()
        initPopular()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.carBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items ->
            banners(items)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images: List<SliderModel>) {
        binding.viewPageSlider.adapter = SliderAdapter(images, binding.viewPageSlider)
        binding.viewPageSlider.clipToPadding = false
        binding.viewPageSlider.clipChildren = false
        binding.viewPageSlider.offscreenPageLimit = 3
        binding.viewPageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewPageSlider.setPageTransformer(compositePageTransformer)

        if (images.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewPageSlider)
        }
    }

    private fun initBrand() {
        binding.progressBarBrand.visibility = View.VISIBLE
        viewModel.brands.observe(this, Observer { brands ->
            binding.viewBrand.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewBrand.adapter = BrandAdapter(brands)
            binding.progressBarBrand.visibility = View.GONE
        })
        viewModel.loadBrand()
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.popular.observe(this, Observer { popularItems ->
            binding.viewPopular.layoutManager = GridLayoutManager(this@MainActivity, 2)
            binding.viewPopular.adapter = PopularAdapter(popularItems)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()
    }
}

