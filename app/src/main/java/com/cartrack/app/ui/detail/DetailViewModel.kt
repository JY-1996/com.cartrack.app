package com.cartrack.app.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrack.app.data.detail.DetailRepository
import com.cartrack.app.data.detail.UserContent.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class DetailViewModel(private val repository: DetailRepository): ViewModel() {
    var detail: MutableLiveData<Response<List<User>>> = MutableLiveData()
    var detail2: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    fun getDetail() {
        viewModelScope.launch (Dispatchers.IO){
            val response = repository.getDetail()
            detail.value = response
        }
    }
    fun getDetail2() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDetail2()
            detail2.value = response
        }
    }
}