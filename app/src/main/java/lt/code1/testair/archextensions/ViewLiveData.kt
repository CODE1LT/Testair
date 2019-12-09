@file:Suppress("unused")

package lt.code1.testair.archextensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import lt.code1.testair.datalayer.core.Resource
import lt.code1.testair.datalayer.core.match
import lt.code1.testair.extensions.addSingleSource
import lt.code1.testair.utils.getFunctionName
import lt.code1.testair.utils.getStackTraceElement
import lt.code1.testair.utils.getTag
import timber.log.Timber

class ViewLiveData<T>(initialValue: T) : MediatorLiveData<T>() { //TODO test
    init {
        value = initialValue
        notifyLiveDataObservers()
    }

    override fun getValue(): T {
        return super.getValue()!!
    }

    fun notifyLiveDataObservers() {
        postValue(value)
    }

    fun <T> addResourceSource(
        source: LiveData<Resource<T>>,
        data: (T?) -> Unit,
        loading: (T?) -> Unit,
        error: (Throwable, T?) -> Unit
    ) {
        getStackTraceElement(javaClass)?.let { stackTraceElement ->
            logSourceAddition(stackTraceElement, source)
            this.addSource(source) { matchResource(stackTraceElement, it, data, loading, error) }
        }
    }

    fun <T> addSingleResourceSource(
        source: SingleResourceLiveData<Resource<T>>,
        data: (T) -> Unit,
        loading: (T?) -> Unit,
        error: (Throwable, T?) -> Unit
    ) {
        getStackTraceElement(javaClass).let { stackTraceElement ->
            logSourceAddition(stackTraceElement, source)
            this.addSingleSource(source) {
                matchResource(
                    stackTraceElement,
                    it,
                    data,
                    loading,
                    error
                )
            }
        }
    }

    private fun <T> logSourceAddition(
        stackTraceElement: StackTraceElement?,
        source: LiveData<Resource<T>>
    ) {
        val tag = getTag(stackTraceElement)
        val functionName = getFunctionName(stackTraceElement)
        Timber.tag(tag)
            .d("${functionName}, Adding source $source")
    }

    private fun <T> matchResource(
        stackTraceElement: StackTraceElement?,
        resource: Resource<T>,
        dataFun: (T) -> Unit,
        loadingFun: (T?) -> Unit,
        errorFun: (Throwable, T?) -> Unit
    ) {
        resource.match({ data ->
            Timber.tag(getTag(stackTraceElement))
                .d("${getFunctionName(stackTraceElement)}, emitted Data with data: $data")
            dataFun(data)
        }, { data ->
            Timber.tag(getTag(stackTraceElement))
                .d("${getFunctionName(stackTraceElement)}, emitted Loading with data: $data")
            loadingFun(data)
        }, { throwable, data ->
            Timber.tag(getTag(stackTraceElement))
                .e(throwable)
            errorFun(throwable, data)
        })
    }
}