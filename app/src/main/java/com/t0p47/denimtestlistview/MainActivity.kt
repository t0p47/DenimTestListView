package com.t0p47.denimtestlistview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.t0p47.denimtestlistview.model.Capital
import com.t0p47.denimtestlistview.activity.CapitalActivity
import com.t0p47.denimtestlistview.activity.MyViewModel
import com.t0p47.denimtestlistview.activity.NewCapitalActivity
import com.t0p47.denimtestlistview.adapter.CapitalAdapter
import com.t0p47.denimtestlistview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
	private lateinit var viewModel: MyViewModel
    private var capitalsList: ArrayList<Capital>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

		viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        binding.vm = viewModel
		binding.executePendingBindings()


        if(viewModel.repositories.value == null){
            viewModel.loadRepositories()
        }

		viewModel.repositories.observe(this,
        	Observer<ArrayList<Capital>>{ it ->
				it?.let{
        		Log.d("LOG_TAG", "MainActivity: get repository data")
        		capitalsList = it
        		binding.lvCapital.adapter = CapitalAdapter(this, capitalsList!!)
        	}})

        binding.btnAddCapital.setOnClickListener{
			val intent = Intent(this, NewCapitalActivity::class.java)
			startActivityForResult(intent, 1)
        }

		binding.lvCapital.setOnItemClickListener{ _, _, position, _->

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
				val capital = viewModel.addNewCapital(data)

                capitalsList!!.add(capital)
                Log.d("LOG_TAG","MainActivity: updateListView with new capital")
                (binding.lvCapital.adapter as BaseAdapter).notifyDataSetChanged()
    		}
    	}
    }




}
