@file:Suppress("DEPRECATION")

package lt.code1.testair.archextensions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import lt.code1.testair.datalayer.core.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception

@Suppress("UNCHECKED_CAST")
class SingleResourceLiveDataTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<String>>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `SingleResourceLiveData emits first Data after Loading`() {
        val liveData = MutableLiveData<Resource<String>>()

        liveData.toSingleResourceLiveData()
            .observeForever(observer)
        liveData.postValue(Resource.Loading())
        liveData.postValue(Resource.Data("1"))

        val inOrder = Mockito.inOrder(observer)
        inOrder.verify(observer)
            .onChanged(Resource.Loading())
        inOrder.verify(observer)
            .onChanged(Resource.Data("1"))
    }

    @Test
    fun `SingleResourceLiveData emits first Data after Loading when has initial values`() {
        val liveData = MutableLiveData<Resource<String>>()

        liveData.postValue(Resource.Loading())
        liveData.toSingleResourceLiveData()
            .observeForever(observer)
        liveData.postValue(Resource.Data("1"))

        val inOrder = Mockito.inOrder(observer)
        inOrder.verify(observer)
            .onChanged(Resource.Loading())
        inOrder.verify(observer)
            .onChanged(Resource.Data("1"))
    }

    @Test
    fun `SingleResourceLiveData throws exception if Data emits more than once`() {
        val liveData = MutableLiveData<Resource<String>>()

        liveData.toSingleResourceLiveData()
            .observeForever(observer)
        liveData.postValue(Resource.Loading())
        liveData.postValue(Resource.Data("1"))

        try {
            liveData.postValue(Resource.Data("2"))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceLiveData throws exception if Error emits more than once`() {
        val liveData = MutableLiveData<Resource<String>>()
        val throwable1 = Exception()
        val throwable2 = RuntimeException()

        liveData.toSingleResourceLiveData()
            .observeForever(observer)
        liveData.postValue(Resource.Loading())
        liveData.postValue(Resource.Error(throwable1))

        try {
            liveData.postValue(Resource.Error(throwable2))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceLiveData throws exception if Error emits and Data was already emitted`() {
        val liveData = MutableLiveData<Resource<String>>()
        val throwable = Exception()

        liveData.toSingleResourceLiveData()
            .observeForever(observer)
        liveData.postValue(Resource.Loading())
        liveData.postValue(Resource.Data("1"))

        try {
            liveData.postValue(Resource.Error(throwable))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceLiveData throws exception if Data emits and Error was already emitted`() {
        val liveData = MutableLiveData<Resource<String>>()
        val throwable = Exception()

        liveData.toSingleResourceLiveData()
            .observeForever(observer)
        liveData.postValue(Resource.Loading())
        liveData.postValue(Resource.Error(throwable))

        try {
            liveData.postValue(Resource.Data("2"))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceLiveData throws exception if Loading emits more than once`() {
        val liveData = MutableLiveData<Resource<String>>()

        liveData.toSingleResourceLiveData()
            .observeForever(observer)
        liveData.postValue(Resource.Loading())

        try {
            liveData.postValue(Resource.Loading("2"))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceLiveData throws exception if Loading emits after Data`() {
        val liveData = MutableLiveData<Resource<String>>()

        liveData.toSingleResourceLiveData()
            .observeForever(observer)
        liveData.postValue(Resource.Data("1"))

        try {
            liveData.postValue(Resource.Loading("2"))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceLiveData throws exception if Loading emits after Error`() {
        val liveData = MutableLiveData<Resource<String>>()
        val throwable = Exception()

        liveData.toSingleResourceLiveData()
            .observeForever(observer)
        liveData.postValue(Resource.Error(throwable))

        try {
            liveData.postValue(Resource.Loading("2"))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceLiveData throws exception if value is not Resource`() {
        val observer2 = Mockito.mock(Observer::class.java) as Observer<String>
        val liveData = MutableLiveData<String>()
        val singleResourceLiveData = SingleResourceMutableLiveData<String>(liveData)

        singleResourceLiveData.observeForever(observer2)

        try {
            liveData.postValue("1")
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceLiveData throws exception when setValue called from outside`() {
        val liveData = MutableLiveData<Resource<String>>()
        val singleResourceLiveData = liveData.toSingleResourceLiveData()

        singleResourceLiveData.observeForever(observer)

        try {
            singleResourceLiveData.value = Resource.Data("1")
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `SingleResourceLiveData throws exception when postValue called from outside`() {
        val liveData = MutableLiveData<Resource<String>>()
        val singleResourceLiveData = liveData.toSingleResourceLiveData()

        singleResourceLiveData.observeForever(observer)

        try {
            singleResourceLiveData.postValue(Resource.Data("1"))
            Assert.fail()
        } catch (e: RuntimeException) {
            //success
        }
    }

    @Test
    fun `mapSingleResource maps SingleResourceLiveData Resource value`() {
        val liveData = MutableLiveData<Resource<String>>()

        liveData.toSingleResourceLiveData()
            .mapSingleResource {
                "/$it"
            }
            .observeForever(observer)

        liveData.postValue(Resource.Loading())
        liveData.postValue(Resource.Data("1"))

        val inOrder = Mockito.inOrder(observer)
        inOrder.verify(observer)
            .onChanged(Resource.Loading())
        inOrder.verify(observer)
            .onChanged(Resource.Data("/1"))
    }
}