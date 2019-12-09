@file:Suppress("unused")

package lt.code1.testair.archextensions

import androidx.lifecycle.*
import com.snakydesign.livedataextensions.map
import lt.code1.testair.datalayer.core.Resource
import lt.code1.testair.datalayer.core.map
import lt.code1.testair.datalayer.core.mapResource

open class SingleResourceLiveData<T> protected constructor(liveData: LiveData<T>) :
    MediatorLiveData<T>() {

    private var wasLoading = false
    private var wasDataOrError = false

    private val mediatorObserver = Observer<T> {
        throwExceptionIfDataIsIncorrect(it)
        setValueTypeFlags(it)
        super.setValue(it)
    }

    init {
        @Suppress("LeakingThis")
        addSource(liveData, mediatorObserver)
    }

    private fun throwExceptionIfDataIsIncorrect(value: T?) {
        if (wasLoading && value is Resource.Loading<*>) {
            throw RuntimeException("Can't set value to Loading more than once")
        } else if (wasDataOrError && value is Resource.Data<*> || wasDataOrError && value is Resource.Error<*>) {
            throw RuntimeException("Can't set value to Data or Error more than once")
        } else if (wasDataOrError && value is Resource.Loading<*>) {
            throw RuntimeException("Can't set value to Loading after Data or Error")
        } else if (value !is Resource<*>) {
            throw RuntimeException("value should be type of Resource")
        }
    }

    private fun setValueTypeFlags(value: T?) {
        if (value is Resource.Loading<*>) {
            wasLoading = true
        } else if (value is Resource.Data<*> || value is Resource.Error<*>) {
            wasDataOrError = true
        }
    }

    @Deprecated("Shouldn't call setValue directly. Will cause crash.")
    @Suppress("DeprecatedCallableAddReplaceWith")
    override fun setValue(value: T?) {
        throw RuntimeException("Shouldn't call setValue directly!")
    }

    @Deprecated("Shouldn't call postValue directly. Will cause crash.")
    @Suppress("DeprecatedCallableAddReplaceWith")
    override fun postValue(value: T) {
        throw RuntimeException("Shouldn't call postValue directly!")
    }
}

fun <T> LiveData<Resource<T>>.toSingleResourceLiveData(): SingleResourceLiveData<Resource<T>> =
    SingleResourceMutableLiveData(this)

fun <T1, T2> SingleResourceLiveData<Resource<T1>>.mapSingleResource(map: (T1) -> T2) =
    mapResource(map).toSingleResourceLiveData()

fun <T1, T2> SingleResourceLiveData<Resource<T1>>.mapSingle(mapFunction: (Resource<T1>) -> Resource<T2>) =
    map(mapFunction).toSingleResourceLiveData()

fun <T1, T2> SingleResourceLiveData<Resource<T1>>.switchMapSingle(mapFunction: (Resource<T1>) -> LiveData<Resource<T2>>) =
    switchMap(mapFunction).toSingleResourceLiveData()

fun <T1, T2> SingleResourceLiveData<Resource<T1>>.switchMapSingleResourceData(
    defaultValue: T2,
    mapFunction: (Resource<T1>) -> LiveData<Resource<T2>>
) =
    switchMapSingle {
        when (it) {
            is Resource.Data -> mapFunction(it)
            else -> MutableLiveData(it.map { defaultValue })
        }
    }