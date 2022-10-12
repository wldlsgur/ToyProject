package com.example.issueproject.res.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModels : ViewModel() {
    val userIds = MutableLiveData<String>()
//
//    val userId : LiveData<String>
//        get() = _userId

    fun setUserId(userId:String){
        userIds.value = userId
    }
}