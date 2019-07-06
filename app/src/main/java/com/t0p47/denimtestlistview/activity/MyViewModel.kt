package com.t0p47.denimtestlistview.activity

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.t0p47.capitals.model.Capital
import com.t0p47.capitals.rest.ApiInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup


class MyViewModel: AndroidViewModel {

	private val capitalsService by lazy{
		ApiInterface.create(getApplication())
	}

	val isLoading = ObservableField(false)

    lateinit var disposable: Disposable
    var repositories = MutableLiveData<ArrayList<Capital>>()

    constructor(application: Application) : super(application)

    fun loadRepositories(){
		Log.d("LOG_TAG","loadRepositories")
    	/*disposable = capitalRepository.getRepositories().subscribeWith(object: DisposableObserver<CapitalResponse>(){

    		override fun onError(e: Throwable){
				Log.d("LOG_TAG","Error: ${e.stackTrace}")
			}

    		override fun onNext(data: CapitalResponse){
				Log.d("LOG_TAG","onNext")
    			repositories.value = data
    		}

    		override fun onComplete(){
				Log.d("LOG_TAG","OnComplete")
			}

		})*/

		disposable = capitalsService.getCapitals()
            .map {capResponse -> capResponse.capitals}
            .flatMap {capitals -> Observable.fromIterable(capitals)}
            .map {capital ->
                fun changeImgLink(capital: Capital): Capital{
                    capital.images?.forEachIndexed { id, value ->
                        val imgUri = Uri.parse(value)
                        Log.d("LOG_TAG","Check image host: ${imgUri.host}")
                        if(imgUri.host == "en.wikipedia.org"){
                            val doc = Jsoup.connect(value).get()
                            val fileName = value.split("File:")[1]
                            val imgs = doc.select("img[src$=${fileName}]").first()
                            capital.images?.set(id, "https:${imgs.attr("src")}")
                        }
                    }
                    return capital
                }
                changeImgLink(capital)
            }
            .collect({ArrayList<Capital>()}, {container, value-> container.add(value) })
            .doOnSubscribe{ _ -> isLoading.set(true)}
            .doAfterTerminate{ isLoading.set(false)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                //{ result -> repositories.value = result },
                { result -> repositories.value = result },
                { error -> Log.d("LOG_TAG","Error: ${error.message}") }
            )
    }

    override fun onCleared(){
    	super.onCleared()
    	if(!disposable.isDisposed){
    		disposable.dispose()
    	}
    }

}