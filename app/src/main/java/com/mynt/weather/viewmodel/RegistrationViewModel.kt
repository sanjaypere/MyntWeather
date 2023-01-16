package com.mynt.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mynt.weather.data.db.entity.UserEntity
import com.mynt.weather.data.repository.LoginRepository
import com.mynt.weather.utils.AppResponse
import com.mynt.weather.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.sqlcipher.database.SQLiteConstraintException
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    private val _appResponse = MutableLiveData<AppResponse>()
    private val _validEmail = MutableLiveData<Boolean>()
    private val _validName = MutableLiveData<Boolean>()
    private val _validPassword = MutableLiveData<Boolean>()
    private val _userEntity = MutableLiveData<UserEntity>()
    val appResponse: LiveData<AppResponse>
        get() = _appResponse
    val validName: LiveData<Boolean>
        get() = _validName
    val validEmail: LiveData<Boolean>
        get() = _validEmail
    val validPassword: LiveData<Boolean>
        get() = _validPassword
    val user: LiveData<UserEntity>
        get() = _userEntity

    /**
     * Default initialization
     */
    init {
        _userEntity.value = UserEntity(email = "", password = "")
        _validEmail.postValue(true)
        _validPassword.postValue(true)
        _validName.postValue(true)
    }

    /**
     * Validate user credential for registration and save User detail to DataBase.
     * If user registered successfully it send Success Response, else send Error response
     * @param : UserEntity(User detail)
     */
    fun registerUser(user: UserEntity) {
        _appResponse.postValue(AppResponse.Loading())
        val vName = isValidName(user.name)
        val vEmail = isValidEmail(user.email)
        val vPassword = isValidPassword(user.password)
        _validName.postValue(vName)
        _validEmail.postValue(vEmail)
        _validPassword.postValue(vPassword)
        if (vName && vEmail && vPassword) {
            viewModelScope.launch {
                try {
                    loginRepository.registerUser(user)
                    val loggedInUser =
                        loginRepository.getUserByEmailAndPassword(user.email, user.password)
                    loggedInUser?.let {
                        _userEntity.postValue(it)
                        _appResponse.postValue(AppResponse.Success())
                    }
                } catch (e: SQLiteConstraintException) {
                    _appResponse.postValue(AppResponse.Error(message = Constants.userAlreadyRegistered))
                } catch (e: Exception) {
                    _appResponse.postValue(e.message?.let { AppResponse.Error(message = it) })
                }
            }
        } else {
            _appResponse.postValue(AppResponse.Error(message = null))
        }
    }

    /**
     * Check for valid email
     */
    fun isValidEmail(text: String?): Boolean {
        return text?.trim()?.isNotEmpty() == true && Constants.emailPattern.toRegex().matches(text)
    }

    /**
     * Check for valid password
     */
    fun isValidPassword(text: String?): Boolean {
        return text?.trim()?.isNotEmpty() == true && text.length >= Constants.passwordLength
    }

    /**
     * Check for valid name
     */
    fun isValidName(text: String?): Boolean {
        return text?.trim()?.isNotEmpty() == true && text.length >= Constants.nameLength
    }

    /**
     * Email editTextChange listener
     */
    fun onEmailEditTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _validEmail.postValue(true)
    }

    /**
     * Password editTextChange listener
     */
    fun onPasswordEditTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _validPassword.postValue(true)
    }

    /**
     * Name editTextChange listener
     */
    fun onNameEditTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _validName.postValue(true)
    }
}