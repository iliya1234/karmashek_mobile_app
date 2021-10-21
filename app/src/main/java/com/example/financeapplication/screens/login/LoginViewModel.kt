package com.example.financeapplication.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapplication.R
import com.example.financeapplication.di.ResourcesProvider
import com.example.financeapplication.models.*
import com.example.financeapplication.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Call
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel
@Inject
constructor(private val repository: AuthRepository, private val resourcesProvider: ResourcesProvider): ViewModel() {
    private val _userResponse = MutableLiveData<UserAuthResponse>()
    private val _errorMessage = MutableLiveData<ErrorMessage>()
    val userResponse: LiveData<UserAuthResponse> get() = _userResponse
    val errorMessage: LiveData<ErrorMessage> get() = _errorMessage


    fun initUser(user: UserAuthRequest) = viewModelScope.launch {
            try {
                val result = repository.authUser(user)
                if (result.isSuccessful){
                    _userResponse.postValue(result.body())
                }
                else{
                    when(result.code()){
                        403 -> _errorMessage.postValue(
                            ErrorMessage(resourcesProvider.getString(R.string.invalid_login_or_password)))
                        500 -> _errorMessage.postValue(
                            ErrorMessage(resourcesProvider.getString(R.string.server_error)))
                    }
                }
            }
            catch (e: SocketTimeoutException){
                Log.e("MyError",e.toString());
                _errorMessage.postValue(
                    ErrorMessage(resourcesProvider.getString(R.string.failed_to_connect_to_server)))
            }
    }
}