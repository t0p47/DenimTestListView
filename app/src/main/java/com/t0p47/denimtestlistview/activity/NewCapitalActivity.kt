package com.t0p47.denimtestlistview.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.t0p47.denimtestlistview.R
import com.t0p47.denimtestlistview.databinding.ActivityNewCapitalBinding

class NewCapitalActivity: AppCompatActivity(){
	
	lateinit var binding: ActivityNewCapitalBinding

	override fun onCreate(savedInstanceState: Bundle?){
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_new_capital)

		binding.btnAddCapital.setOnClickListener{
			val intent = Intent()
			intent.putExtra("capital", binding.etCapital.text.toString())
			intent.putExtra("country", binding.etCountry.text.toString())
			intent.putExtra("description", binding.etDescription.text.toString())
			intent.putExtra("images", binding.etImgLinks.text.toString())
			setResult(RESULT_OK, intent)
			finish()
		}
	}

}