package com.example.myapplication.activity

import ChangeNumberItemsListener
import ManagementCart
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.CartAdapter
import com.example.myapplication.databinding.ActivityCartBinding
class CartActivity : BaseActivity() {
    private lateinit var binding : ActivityCartBinding
    private lateinit var managementCart: ManagementCart
    private var tax : Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        setVariable()
        initCartList()
        calculateCar()
    }

    private fun initCartList() {
        binding.viewCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewCart.adapter = CartAdapter(managementCart.getListCart(), this, object :
            ChangeNumberItemsListener {
            override fun onChanged() {
                calculateCar()
            }
        })

    with(binding){
        emptyTxt.visibility = if(managementCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
        scrollView2.visibility = if(managementCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
    }

    }
    @SuppressLint("SetTextI18n")
    private fun calculateCar(){
        val porcentTax = 0.02
        val delivery = 10.0
        tax = Math.round((managementCart.getTotalFee() * porcentTax) * 100) / 100.0
        val total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100.0
        val itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100

        with(binding){
            totalFeeTxt.text = "S/$itemTotal"
            taxTxt.text = "S/$tax"
            deliveryTxt.text = "S/$delivery"
            totalTxt.text = "S/$total"
        }
    }
    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }
}