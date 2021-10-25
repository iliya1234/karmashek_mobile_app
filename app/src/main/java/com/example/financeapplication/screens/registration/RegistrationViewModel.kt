package com.example.financeapplication.screens.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapplication.R
import com.example.financeapplication.di.ResourcesProvider
import com.example.financeapplication.models.ErrorMessage
import com.example.financeapplication.models.UserAuthResponse
import com.example.financeapplication.models.UserRegistration
import com.example.financeapplication.repositories.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel
@Inject
constructor(
    private val repository: AuthRepository,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {
    private val _errorMessage = MutableLiveData<ErrorMessage>()
    val errorMessage: LiveData<ErrorMessage> get() = _errorMessage

    private val _userResponse = MutableLiveData<UserAuthResponse>()

    val userResponse: LiveData<UserAuthResponse> get() = _userResponse

    fun registerUser(userRegistration: UserRegistration) = viewModelScope.launch {
        try {
            repository.registerUser(userRegistration).let { result ->
                if (result.isSuccessful) {
                    _userResponse.postValue(result.body())
                } else {
                    when (result.code()) {
                        500 -> _errorMessage.postValue(ErrorMessage(resourcesProvider.getString(R.string.server_error)))
                        400 -> {
                            _errorMessage.postValue(
                                Gson().fromJson(
                                    result.errorBody()?.string(),
                                    ErrorMessage::class.java
                                )
                            )
                        }
                    }
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("MyError", e.toString());
            _errorMessage.postValue(
                ErrorMessage(resourcesProvider.getString(R.string.failed_to_connect_to_server))
            )
        }
    }
}