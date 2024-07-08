package com.example.myapplication.activity

import ColorAdapter
import ManagementCart
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.SizeAdapter
import com.example.myapplication.Adapter.SliderAdapter
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.Model.SliderModel
import com.example.myapplication.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        getBundle()
        banners()
        initLists()

        setupListeners()
    }

    private fun initLists() {
        val sizeList = ArrayList<String>()
        for (size in item.size){
            sizeList.add(size.toString())
        }
        binding.sizeList.adapter = SizeAdapter(sizeList)
        binding.sizeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl)
        binding.colorList.adapter = ColorAdapter(colorList)
        binding.colorList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        item.picUrl.forEach { imageUrl -> sliderItems.add(SliderModel(imageUrl)) }
        binding.slider.adapter = SliderAdapter(sliderItems, binding.slider)
        binding.slider.clipToPadding = true
        binding.slider.clipChildren = true
        binding.slider.offscreenPageLimit = 1

        if (sliderItems.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.slider)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getBundle() {
        item = intent.getParcelableExtra<ItemsModel>("Object")!!
        if (item != null) {
            binding.titleTxt.text = item.title
            binding.descriptionTxt.text = item.description
            binding.priceTxt.text = "S/${item.price}"
            binding.ratingTxt.text = "${item.rating} Rating"
        } else {
            // Manejar caso donde el objeto es nulo o no se pudo obtener correctamente
            // Esto podría ser una advertencia o manejo de error adecuado
            // por ejemplo, mostrar un mensaje al usuario o volver a la actividad anterior.
        }
    }

    private fun setupListeners() {
        binding.addToCarBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managementCart.insertFood(item)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }
    }
}
