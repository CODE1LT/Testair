@file:Suppress("unused")

package lt.code1.testair.datalayer.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataMediator<RESPONSE, MAPPED_RESULT, ENTITY> @Inject constructor(
    private val responseToEntityMapper: Function1<@JvmSuppressWildcards RESPONSE, @JvmSuppressWildcards MAPPED_RESULT>,
    override val coroutineContext: CoroutineContext
) : CoroutineScope {

    private val _fetchingLiveData = MutableLiveData<Boolean>().apply { value = false }
    @Suppress("MemberVisibilityCanBePrivate")
    val fetchingLiveData: LiveData<Boolean> = _fetchingLiveData

    private val mediatorLiveData = MediatorLiveData<Resource<ENTITY>>()

    private val fetchErrorLiveData = MutableLiveData<Throwable>()
    private lateinit var queryLiveData: LiveData<ENTITY>

    private var shouldFetchIfCurrentlyNotFetching: () -> Boolean = {
        queryLiveData.value?.let {
            it is List<*> && it.isEmpty()
        } ?: true
    }
    private var getError: (throwable: Throwable) -> Throwable =
        { throwable: Throwable -> throwable }

    init {
        addFetchErrorLiveDataSourceToMediatorLiveData()
    }

    private fun addFetchErrorLiveDataSourceToMediatorLiveData() {
        mediatorLiveData.addSource(fetchErrorLiveData) { throwable ->
            getError(getDefaultError(throwable))
                .let { Resource.Error<ENTITY>(it) }
                .let { mediatorLiveData.postValue(it)}
        }
    }

    private fun getDefaultError(throwable: Throwable) =
        if (throwable is MapException) MapException(throwable.message) else throwable

    fun getData(
        queryLiveData: LiveData<ENTITY>,
        fetchDataDeferred: suspend () -> RESPONSE,
        onResponseResultMapped: (data: MAPPED_RESULT) -> Unit
    ): LiveData<Resource<ENTITY>> {
        if (!this::queryLiveData.isInitialized) {
            this.queryLiveData = queryLiveData
            addQueryLiveDataSourceToMediatorLiveData()
        }
        startFetchCoroutine(fetchDataDeferred, onResponseResultMapped)
        return mediatorLiveData
    }

    private fun addQueryLiveDataSourceToMediatorLiveData() {
        mediatorLiveData.addSource(queryLiveData) {
            mediatorLiveData.postValue(Resource.Data(it))
        }
    }

    private fun startFetchCoroutine(
        fetchDataDeferred: suspend () -> RESPONSE,
        onResponseResultMapped: (data: MAPPED_RESULT) -> Unit
    ) {
        CoroutineScope(coroutineContext).launch {
            if (shouldFetchIfCurrentlyNotFetching() && fetchingLiveData.value == false) {
                fetch(fetchDataDeferred, onResponseResultMapped)
            }
        }
    }

    private suspend fun fetch(
        fetchDataDeferred: suspend () -> RESPONSE,
        onResponseResultMapped: (data: MAPPED_RESULT) -> Unit
    ) {
        _fetchingLiveData.postValue(true)
        postLoadingValue()
        try {
            val response = fetchDataDeferred()
            mapResponseToEntity(response, onResponseResultMapped)
        } catch (e: Exception) {
            fetchErrorLiveData.postValue(e)
        }
        _fetchingLiveData.postValue(false)
    }

    private fun mapResponseToEntity(
        response: RESPONSE,
        onResponseResultMapped: (data: MAPPED_RESULT) -> Unit
    ) {
        try {
            val entity = responseToEntityMapper(response)
            callOnResponseResultMapped(entity, onResponseResultMapped)
        } catch (e: Exception) {
            fetchErrorLiveData.postValue(MapException(e.message))
        }
    }

    private fun callOnResponseResultMapped(
        entity: MAPPED_RESULT,
        onResponseResultMapped: (data: MAPPED_RESULT) -> Unit
    ) {
        try {
            onResponseResultMapped(entity)
        } catch (e: Exception) {
            fetchErrorLiveData.postValue(e)
        }
    }

    private fun postLoadingValue() {
        val resource: Resource<ENTITY> = mediatorLiveData.value?.let {
            Resource.Loading(it.data)
        } ?: Resource.Loading()
        mediatorLiveData.postValue(resource)
    }

    fun shouldFetchIfCurrentlyNotFetching(shouldFetchIfCurrentlyNotFetching: () -> Boolean): DataMediator<RESPONSE, MAPPED_RESULT, ENTITY> {
        this.shouldFetchIfCurrentlyNotFetching = shouldFetchIfCurrentlyNotFetching
        return this
    }

    fun onFetchErrorEmit(
        getError: (throwable: Throwable) -> Throwable
    ): DataMediator<RESPONSE, MAPPED_RESULT, ENTITY> {
        this.getError = getError
        return this
    }
}