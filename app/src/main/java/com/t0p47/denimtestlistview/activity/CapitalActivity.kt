package com.t0p47.denimtestlistview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.databinding.DataBindingUtil
import com.t0p47.denimtestlistview.R
import com.t0p47.denimtestlistview.adapter.ImageAdapter
import com.t0p47.denimtestlistview.databinding.ActivityCapitalBinding

class CapitalActivity : AppCompatActivity() {

	lateinit var binding: ActivityCapitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_capital)

        binding.tvCountry.text = intent.getStringExtra("country")
        binding.tvCapital.text = intent.getStringExtra("capital")
        binding.tvDescription.text = intent.getStringExtra("description")

        val imgList = intent.getStringExtra("images")
        if(imgList.isNotEmpty()){
            val imgArray: ArrayList<String> = if(imgList.contains(",")){
                (imgList?.split(",")) as ArrayList
            }else{
                arrayListOf(imgList!!)
            }

            binding.gridView.adapter = ImageAdapter(this, imgArray)
            adjustGridView()
        }


    }

    private fun adjustGridView(){
        binding.gridView.numColumns = GridView.AUTO_FIT
        binding.gridView.verticalSpacing = 8
        binding.gridView.horizontalSpacing = 8
    }
}