package org.visionapp.myopia.kotlin.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.visionapp.myopia.kotlin.core.Failure

//Iván Fernández Rico, Globalincubator

/*
* @author  Iván Fernández Rico, Globalincubator
*/
abstract class BaseViewModel: ViewModel()
{

    private var _failure: MutableLiveData<Failure> = MutableLiveData()

    val failure: LiveData<Failure>
        get() = _failure

    protected fun handleFailure(failure: Failure) {
        this._failure.value = failure
    }

}