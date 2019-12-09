package lt.code1.testair.archextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import lt.code1.testair.datalayer.core.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("UNCHECKED_CAST")
class SingleResourceMutableLiveDataTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<String>>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `postSingleValue posts value`() {
        val liveData = SingleResourceMutableLiveData<Resource<String>>()

        liveData.observeForever(observer)
        liveData.postSingleValue(Resource.Loading())

        Mockito.verify(observer)
            .onChanged(Resource.Loading())
    }

    @Test
    fun `setSingleValue sets value`() {
        val liveData = SingleResourceMutableLiveData<Resource<String>>()

        liveData.observeForever(observer)
        liveData.setSingleValue(Resource.Loading())

        Mockito.verify(observer)
            .onChanged(Resource.Loading())
    }

    @Test
    fun `SingleResourceMutableLiveData throws exception if Data is being posted more than once`() {
        val liveData = SingleResourceMutableLiveData<Resource<String>>()

        liveData.observeForever(observer)
        liveData.postSingleValue(Resource.Loading())
        liveData.postSingleValue(Resource.Data("1"))

        try {
            liveData.postSingleValue(Resource.Data("2"))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceMutableLiveData throws exception if Data is being set more than once`() {
        val liveData = SingleResourceMutableLiveData<Resource<String>>()

        liveData.observeForever(observer)
        liveData.setSingleValue(Resource.Loading())
        liveData.setSingleValue(Resource.Data("1"))

        try {
            liveData.setSingleValue(Resource.Data("2"))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceMutableLiveData throws exception when setValue called from outside`() {
        val singleResourceMutableLiveData = SingleResourceMutableLiveData<Resource<String>>()

        singleResourceMutableLiveData.observeForever(observer)

        try {
            @Suppress("DEPRECATION")
            singleResourceMutableLiveData.value = Resource.Data("1")
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceMutableLiveData throws exception when postValue called from outside`() {
        val singleResourceMutableLiveData = SingleResourceMutableLiveData<Resource<String>>()

        singleResourceMutableLiveData.observeForever(observer)

        try {
            @Suppress("DEPRECATION")
            singleResourceMutableLiveData.postValue(Resource.Data("1"))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }
}