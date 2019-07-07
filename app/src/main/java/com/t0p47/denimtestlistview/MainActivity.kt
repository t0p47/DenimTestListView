package com.t0p47.denimtestlistview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
	lateinit var viewModel: MyViewModel
    private var capitalsList: ArrayList<Capital>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

		viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
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
			intent.putExtra("images", TextUtils.join(",",capitalsList?.get(position)?.images))

			startActivity(intent)
		}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

    	if(resultCode == RESULT_OK){
    		if(capitalsList != null){
    			/*var capital = Capital()
    			capital.capital = data?.getStringExtra("capital")
    			capital.country = data?.getStringExtra("country")
    			capital.description = data?.getStringExtra("description")
    			//val imgList = (data?.getStringExtra("images")?.split(",")) as ArrayList
    			val imgList = data?.getStringExtra("images")

				//capital.images = (imgList?.split(",")) as ArrayList
				if(imgList!!.contains(",")){
					capital.images = (imgList?.split(",")) as ArrayList
				}else{
					capital.images = arrayListOf(imgList!!)
				}

				capital = viewModel.changeImgLink(capital)

    			capitalsList!!.add(capital)
				Log.d("LOG_TAG","MainActivity: updateListView with new capital")
				(binding.lvCapital.adapter as BaseAdapter).notifyDataSetChanged()*/

				val capital = viewModel.addNewCapital(data)

                capitalsList!!.add(capital)
                Log.d("LOG_TAG","MainActivity: updateListView with new capital")
                (binding.lvCapital.adapter as BaseAdapter).notifyDataSetChanged()
    		}
    	}
    }




}
