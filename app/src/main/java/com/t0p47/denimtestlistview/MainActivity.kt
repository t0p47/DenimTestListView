package com.t0p47.denimtestlistview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.t0p47.capitals.model.Capital
import com.t0p47.denimtestlistview.activity.CapitalActivity
import com.t0p47.denimtestlistview.activity.MyViewModel
import com.t0p47.denimtestlistview.activity.NewCapitalActivity
import com.t0p47.denimtestlistview.adapter.CapitalAdapter
import com.t0p47.denimtestlistview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var capitalsList: ArrayList<Capital>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

		val viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        binding.vm = viewModel
		binding.executePendingBindings()

		viewModel.loadRepositories()
		viewModel.repositories.observe(this,
        	Observer<ArrayList<Capital>>{it?.let{
        		Log.d("LOG_TAG", "MainActivity: Get data from internet")
        		capitalsList = it
        		binding.lvCapital.adapter = CapitalAdapter(this, capitalsList!!)
        	}})

        binding.btnAddCapital.setOnClickListener{
			val intent = Intent(this, NewCapitalActivity::class.java)
			startActivityForResult(intent, 1)
        }

		binding.lvCapital.setOnItemClickListener{ parent, view, position, id->

			Log.d("LOG_TAG","MainActivity: lv click")
			val intent = Intent(this, CapitalActivity::class.java)
			intent.putExtra("capital", capitalsList?.get(position)?.capital)
			intent.putExtra("country", capitalsList?.get(position)?.country)
			intent.putExtra("description", capitalsList?.get(position)?.description)
			intent.putExtra("images", capitalsList?.get(position)?.images)
			startActivity(intent)

		}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

    	if(resultCode == RESULT_OK){
    		if(capitalsList != null){
    			val capital = Capital()
    			capital.capital = data?.getStringExtra("capital")
    			capital.country = data?.getStringExtra("country")
    			capital.description = data?.getStringExtra("description")
    			//val imgList = (data?.getStringExtra("images")?.split(",")) as ArrayList
    			val imgList = data?.getStringExtra("images")
				if(data?.getStringExtra("images")!!.contains(",")){
					capital.images = (imgList?.split(",")) as ArrayList
				}else{
					capital.images = arrayListOf(imgList!!)
				}

    			capitalsList!!.add(capital)
				Log.d("LOG_TAG","MainActivity: updateListView with new capital")
				(binding.lvCapital.adapter as BaseAdapter).notifyDataSetChanged()
    		}
    	}
    }


}
