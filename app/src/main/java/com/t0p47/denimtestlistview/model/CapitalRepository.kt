package com.t0p47.denimtestlistview.model

import android.content.Context
import com.t0p47.denimtestlistview.rest.ApiInterface
import io.reactivex.Observable

class CapitalRepository(private val context: Context){

	fun getRepositories(): Observable<CapitalResponse> {
		return ApiInterface.create(context).getCapitals()
	}

}