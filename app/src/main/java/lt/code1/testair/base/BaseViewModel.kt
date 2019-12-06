package lt.code1.testair.base

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import lt.code1.testair.archextensions.ViewData
import timber.log.Timber

abstract class BaseViewModel<T : ViewData> : ViewModel() {

    abstract fun getInitialViewLiveDataValue(): T

    val viewLiveData = MediatorLiveData<T>().apply {
        value = getInitialViewLiveDataValue()
    }

    protected fun notifyLiveDataObservers() {
        Timber.d("notifyLiveDataObservers()")
        viewLiveData.postValue(viewLiveData.value)
    }
}