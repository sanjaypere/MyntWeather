package com.mynt.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mynt.weather.models.User
import com.mynt.weather.repository.LoginRepository
import com.mynt.weather.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    private val _validEmail = MutableLiveData<Boolean>()
    private val _validPassword = MutableLiveData<Boolean>()
    private val _user = MutableLiveData<User>()
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
        val isValidEmail = isValidEmail(user.email)
        val isValidPassword = isValidPassword(user.password)
        _validEmail.postValue(isValidEmail)
        _validPassword.postValue(isValidPassword)
        if (isValidEmail && isValidPassword) {
            viewModelScope.launch {
                val loggedInUser =
                    loginRepository.getUserByEmailAndPassword(user.email, user.password)
                loggedInUser?.let {
                    _user.postValue(it)
                } ?: run {
                    // User not registered
                }
            }
        }
    }

    fun isValidEmail(text: String?): Boolean {
        return text?.isNotEmpty() == true && Constants.emailPattern.toRegex().matches(text)
    }

    fun isValidPassword(text: String?): Boolean {
        return text?.isNotEmpty() == true && text.length >= 6
    }

    fun onEmailEditTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _validEmail.postValue(true)
    }

    fun onPasswordEditTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _validPassword.postValue(true)
    }
}