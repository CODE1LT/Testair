package lt.code1.testair.datalayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import lt.code1.testair.datalayer.core.RequestMediator
import lt.code1.testair.datalayer.core.Resource
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.coroutines.Continuation

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class RequestMediatorTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: Function1<Continuation<TestResponse>, TestResponse>
    @Mock
    private lateinit var observer: Observer<in Resource<TestResponse>>

    private val successResponse = TestResponse("1")
    private var testCoroutineScope = TestCoroutineScope()
    private lateinit var requestMediator: RequestMediator<TestResponse>
    private lateinit var noMapperRequestMediator: RequestMediator<ResponseBody>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        requestMediator = RequestMediator(
            testCoroutineScope.coroutineContext
        )
        noMapperRequestMediator =
            RequestMediator(coroutineContext = testCoroutineScope.coroutineContext)
    }

    @Test
    fun `request emits Resource_Loading and then Resource_Data when api success`() =
        runBlockingTest {
            Mockito.`when`(service.invoke(any()))
                .thenReturn(successResponse)

            testCoroutineScope.pauseDispatcher()
            requestMediator
                .request(
                    requestDeferred = service as suspend () -> TestResponse
                )
                .observeForever(observer)
            testCoroutineScope.resumeDispatcher()

            Mockito.verify(service)
                .invoke(any())

            val inOrder = Mockito.inOrder(observer)
            inOrder.verify(observer)
                .onChanged(Resource.Loading())
            inOrder.verify(observer)
                .onChanged(Resource.Data(successResponse))
        }

    @Test
    fun `request emits Resource_Error when api call fails`() = runBlockingTest {
        val exception = Exception()
        Mockito.`when`(service.invoke(any()))
            .thenAnswer { throw exception }

        testCoroutineScope.pauseDispatcher()
        requestMediator
            .request(
                requestDeferred = service as suspend () -> TestResponse
            )
            .observeForever(observer)
        testCoroutineScope.resumeDispatcher()

        Mockito.verify(service)
            .invoke(any())

        val inOrder = Mockito.inOrder(observer)
        inOrder.verify(observer)
            .onChanged(Resource.Loading())
        inOrder.verify(observer)
            .onChanged(Resource.Error(exception, null))
    }
}