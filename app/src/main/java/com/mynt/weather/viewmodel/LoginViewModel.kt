package com.mynt.weather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mynt.weather.models.User
import com.mynt.weather.repository.LoginRepository
import com.mynt.weather.utils.AppResponse
import com.mynt.weather.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.sqlcipher.database.SQLiteConstraintException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    private val _appResponse = MutableLiveData<AppResponse>()
    private val _validEmail = MutableLiveData<Boolean>()
    private val _validPassword = MutableLiveData<Boolean>()
    private val _user = MutableLiveData<User>()
    val appResponse: LiveData<AppResponse>
        get() = _appResponse
    val validEmail: LiveData<Boolean>
        get() = _validEmail
    val validPassword: LiveData<Boolean>
        get() = _validPassword
    val user: LiveData<User>
        get() = _user

    init {
        _user.value = User(email = "", password = "")
        _validEmail.postValue(true)
        _validPassword.postValue(true)
    }

    fun proceedToLogin(user: User) {
        _appResponse.postValue(AppResponse.Loading())
        val vEmail = isValidEmail(user.email)
        val vPassword = isValidPassword(user.password)
        _validEmail.postValue(vEmail)
        _validPassword.postValue(vPassword)
        if (vEmail && vPassword) {
            viewModelScope.launch {
                val loggedInUser =
                    loginRepository.getUserByEmailAndPassword(user.email, user.password)
                loggedInUser?.let {
                    _user.postValue(it)
                    _appResponse.postValue(AppResponse.Success())
                } ?: run {
                    _appResponse.postValue(AppResponse.Error(message = Constants.notValidUser))
                }
            }
        } else {
            _appResponse.postValue(AppResponse.Error(message = null))
        }
    }

    fun isValidEmail(text: String?): Boolean {
        return text?.trim()?.isNotEmpty() == true && Constants.emailPattern.toRegex().matches(text)
    }

    fun isValidPassword(text: String?): Boolean {
        return text?.trim()?.isNotEmpty() == true
    }

    fun onEmailEditTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _validEmail.postValue(true)
    }

    fun onPasswordEditTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _validPassword.postValue(true)
    }
}