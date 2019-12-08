package lt.code1.testair

import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import androidx.appcompat.widget.Toolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import androidx.navigation.findNavController
import lt.code1.testair.base.BaseActivity
import lt.code1.testair.databinding.ActivityMainBinding
import lt.code1.testair.features.citysearch.CitySearchFragmentDirections
import timber.log.Timber


class MainActivity : BaseActivity(), CoroutineScope, NavigationHost,
    HasActionBar {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var mainActivityViewDataBinding: ActivityMainBinding

    override val viewModelClass = MainActivityViewModel::class
    override val layoutId = R.layout.activity_main

    override val coroutineContext: CoroutineContext = Dispatchers.Default + Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate()")
        super.onCreate(savedInstanceState)
        addReferencesToViewsAndViewModel()
    }

    private fun addReferencesToViewsAndViewModel() {
        Timber.d("addReferencesToViewsAndViewModel()")
        mainActivityViewModel = getViewModel() as MainActivityViewModel
        mainActivityViewDataBinding = viewDataBinding as ActivityMainBinding
    }

    override fun onUpClick() {
        Timber.d("onUpClick()")
        onSupportNavigateUp()
    }

    override fun onSearchButtonClicked(cityName: String?) {
        Timber.d("onUserItemClicked()")
        val action = CitySearchFragmentDirections.actionCitySearchToCitiesList(cityName)
        findNavController(R.id.a_main_nav_host_fragment).navigate(action)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.a_main_nav_host_fragment).navigateUp()

    override fun setActionBar(toolbar: Toolbar) = setSupportActionBar(toolbar)
}