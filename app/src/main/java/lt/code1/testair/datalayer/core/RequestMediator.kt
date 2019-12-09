@file:Suppress("unused")

package lt.code1.testair.datalayer.core

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import lt.code1.testair.archextensions.SingleResourceLiveData
import lt.code1.testair.archextensions.toSingleResourceLiveData
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RequestMediator<RESPONSE> @Inject constructor(
    override val coroutineContext: CoroutineContext
) : CoroutineScope {

    private var getError: (throwable: Throwable) -> Throwable =
        { throwable: Throwable -> throwable }

    fun request(
        requestDeferred: suspend () -> RESPONSE
    ): SingleResourceLiveData<Resource<RESPONSE>> {
        return MutableLiveData<Resource<RESPONSE>>()
            .also {
                startRequestCoroutine(
                    requestDeferred,
                    it
                )
            }
            .toSingleResourceLiveData()
    }

    private fun startRequestCoroutine(
        requestDeferred: suspend () -> RESPONSE,
        singleMutableLiveData: MutableLiveData<Resource<RESPONSE>>
    ) {
        CoroutineScope(coroutineContext).launch {
            postLoading(singleMutableLiveData)
            try {
                postSuccess(singleMutableLiveData, requestDeferred())
            } catch (e: Exception) {
                getError(getDefaultError(e))
                    .let { Resource.Error<RESPONSE>(it) }
                    .let { singleMutableLiveData.postValue(it) }
            }
        }
    }

    private fun postLoading(singleMutableLiveData: MutableLiveData<Resource<RESPONSE>>) =
        singleMutableLiveData.postValue(Resource.Loading())

    private fun postSuccess(
        singleMutableLiveData: MutableLiveData<Resource<RESPONSE>>,
        data: RESPONSE
    ) {
        val resourceSuccess: Resource<RESPONSE> =
            Resource.Data(data)
        singleMutableLiveData.postValue(resourceSuccess)
    }

    private fun getDefaultError(throwable: Throwable): Throwable {
        return if (throwable is MapException) {
            MapException(throwable.message)
        } else {
            throwable
        }
    }

    fun onRequestErrorEmit(
        getError: (throwable: Throwable) -> Throwable
    ): RequestMediator<RESPONSE> {
        this.getError = getError
        return this
    }
}