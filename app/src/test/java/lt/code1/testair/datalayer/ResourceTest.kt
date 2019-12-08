package lt.code1.testair.datalayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import lt.code1.testair.datalayer.core.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations

@Suppress("UNCHECKED_CAST")
class ResourceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<TestObject2>>

    private val testString = "testString"
    private val testObject1 = TestObject1(testString)
    private val testObject2 = TestObject2(testString)
    private val throwable = Throwable()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Resource_map maps Resource_Data to another Resource_Data`() {
        val resource = Resource.Data(testObject1)
            .map { TestObject2(it.string) }

        assertThat(resource).isEqualTo(Resource.Data(testObject2))
    }

    @Test
    fun `Resource_map maps Resource_Loading to another Resource_Loading`() {
        val resource = Resource.Loading(testObject1)
            .map { TestObject2(it.string) }

        assertThat(resource).isEqualTo(Resource.Loading(testObject2))
    }

    @Test
    fun `Resource_map maps Resource_Error to another Resource_Error`() {
        val resource = Resource.Error(throwable, testObject1)
            .map { TestObject2(it.string) }

        assertThat(resource).isEqualTo(Resource.Error(throwable, testObject2))
    }

    @Test
    fun `Resource_map flatMaps Resource_Data to another Resource`() {
        val resource = Resource.Data(testObject1)
            .flatMap { Resource.Data(TestObject2(it?.string)) }

        assertThat(resource).isEqualTo(Resource.Data(testObject2))
    }

    @Test
    fun `Resource_map flatMaps Resource_Error to another Resource`() {
        val resource = Resource.Error(throwable, testObject1)
            .flatMap { Resource.Data(TestObject2(it?.string)) }

        assertThat(resource).isEqualTo(Resource.Data(testObject2))
    }

    @Test
    fun `Resource_map flatMaps Resource_Loading to another Resource`() {
        val resource = Resource.Loading(testObject1)
            .flatMap { Resource.Error(throwable, TestObject2(it?.string)) }

        assertThat(resource).isEqualTo(Resource.Error(throwable, testObject2))
    }

    @Test
    fun `Resource_mapResource maps LiveData_Resource_Data to another LiveData_Resource_Data`() {
        MutableLiveData<Resource<TestObject1>>()
            .apply { value = Resource.Data(testObject1) }
            .mapResource { TestObject2(it.string) }
            .observeForever(observer)

        Mockito.verify(observer)
            .onChanged(Resource.Data(TestObject2(testObject1.string)))
    }

    @Test
    fun `Resource_mapResource maps LiveData_Resource_Error to another LiveData_Resource_Error`() {
        MutableLiveData<Resource<TestObject1>>()
            .apply { value = Resource.Error(throwable, testObject1) }
            .mapResource { TestObject2(it.string) }
            .observeForever(observer)

        Mockito.verify(observer)
            .onChanged(Resource.Error(throwable, testObject2))
    }

    @Test
    fun `Resource_mapResource maps LiveData_Resource_Loading to another LiveData_Resource_Loading`() {
        MutableLiveData<Resource<TestObject1>>()
            .apply { value = Resource.Loading(testObject1) }
            .mapResource { TestObject2(it.string) }
            .observeForever(observer)

        Mockito.verify(observer)
            .onChanged(Resource.Loading(testObject2))
    }

    @Test
    fun `Resource_flatMapResource maps LiveData_Resource_Data to another LiveData_Resource`() {
        val observer2 = Mockito.mock(Observer::class.java) as Observer<Resource<TestObject2>>

        MutableLiveData<Resource<TestObject1>>()
            .apply { value = Resource.Data(testObject1) }
            .flatMapResource { Resource.Data(TestObject2(it?.string)) }
            .observeForever(observer2)

        Mockito.verify(observer2)
            .onChanged(Resource.Data(testObject2))
    }

    @Test
    fun `Resource_flatMapResource maps LiveData_Resource_Loading to another LiveData_Resource`() {
        val observer2 = Mockito.mock(Observer::class.java) as Observer<Resource<TestObject2>>

        MutableLiveData<Resource<TestObject1>>()
            .apply { value = Resource.Loading(testObject1) }
            .flatMapResource { Resource.Loading(TestObject2(it?.string)) }
            .observeForever(observer2)

        Mockito.verify(observer2)
            .onChanged(Resource.Loading(testObject2))
    }

    @Test
    fun `Resource_flatMapResource maps LiveData_Resource_Error to another LiveData_Resource`() {
        val observer2 = Mockito.mock(Observer::class.java) as Observer<Resource<TestObject2>>

        MutableLiveData<Resource<TestObject1>>()
            .apply { value = Resource.Error(throwable, testObject1) }
            .flatMapResource { Resource.Error(throwable, TestObject2(it?.string)) }
            .observeForever(observer2)

        Mockito.verify(observer2)
            .onChanged(Resource.Error(throwable, testObject2))
    }

    @Test
    fun `Resource_match extension function calls only data function on type Resource_Data`() {
        val dataFunctionMock = mock<(TestObject1?) -> String>()
        val loadingFunctionMock = mock<(TestObject1?) -> String>()
        val errorFunctionMock = mock<(Throwable, TestObject1?) -> String>()
        val resourceData = Resource.Data(testObject1)
        resourceData.match(
            data = dataFunctionMock,
            loading = loadingFunctionMock,
            error = errorFunctionMock
        )
        Mockito.verify(dataFunctionMock)
            .invoke(testObject1)
        Mockito.verify(loadingFunctionMock, never())
            .invoke(any())
        Mockito.verify(errorFunctionMock, never())
            .invoke(eq(throwable), any())
    }

    @Test
    fun `Resource_match extension function calls only loading function on type Resource_Loading`() {
        val dataFunctionMock = mock<(TestObject1?) -> String>()
        val loadingFunctionMock = mock<(TestObject1?) -> String>()
        val errorFunctionMock = mock<(Throwable, TestObject1?) -> String>()
        val resourceData = Resource.Loading(testObject1)
        resourceData.match(
            data = dataFunctionMock,
            loading = loadingFunctionMock,
            error = errorFunctionMock
        )
        Mockito.verify(dataFunctionMock, never())
            .invoke(any())
        Mockito.verify(loadingFunctionMock)
            .invoke(testObject1)
        Mockito.verify(errorFunctionMock, never())
            .invoke(eq(throwable), any())
    }

    @Test
    fun `Resource_match extension function calls only error function on type Resource_Error`() {
        val dataFunctionMock = mock<(TestObject1?) -> String>()
        val loadingFunctionMock = mock<(TestObject1?) -> String>()
        val errorFunctionMock = mock<(Throwable, TestObject1?) -> String>()
        val resourceData = Resource.Error(throwable, testObject1)
        resourceData.match(
            data = dataFunctionMock,
            loading = loadingFunctionMock,
            error = errorFunctionMock
        )
        Mockito.verify(dataFunctionMock, never())
            .invoke(any())
        Mockito.verify(loadingFunctionMock, never())
            .invoke(any())
        Mockito.verify(errorFunctionMock)
            .invoke(throwable, testObject1)
    }
}
