package com.nokhrin.singlelivedatatest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.siber.gsserver.utils.SingleLiveEvent

class MainViewModel(application: Application):  AndroidViewModel(application) {
    val resultMutableLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    val resultLiveData: LiveData<String> = resultMutableLiveData

    init{
        RfLogger.d("DmNokhrin", "MainViewModel::init: $this")//DmNokhrin
    }

}