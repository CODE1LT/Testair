package lt.code1.testair.features.citysearch

import android.content.Context
import android.os.Bundle
import android.view.*
import lt.code1.testair.NavigationHost
import lt.code1.testair.R
import lt.code1.testair.base.BaseFragment
import timber.log.Timber
import androidx.lifecycle.Observer
import lt.code1.testair.databinding.FragmentCitySearchBinding

class CitySearchFragment : BaseFragment() {

    private var navigationHost: NavigationHost? = null
    private lateinit var citySearchFragmentViewModel: CitySearchFragmentViewModel
    private lateinit var fragmentCitySearchBinding: FragmentCitySearchBinding
    override val layoutId = R.layout.fragment_city_search
    override val viewModelClass = CitySearchFragmentViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        Timber.d("onAttach()")
        super.onAttach(context)
        navigationHost = context as NavigationHost
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
        addReferencesToViewsAndViewModel()
        observeSearchButtonClickedEvent()
    }

    private fun addReferencesToViewsAndViewModel() {
        Timber.d("addReferencesToViewsAndViewModel()")
        citySearchFragmentViewModel = getViewModel() as CitySearchFragmentViewModel
        fragmentCitySearchBinding = viewDataBinding as FragmentCitySearchBinding
    }

    private fun observeSearchButtonClickedEvent() {
        Timber.d("observeUserItemClickedEvent()")
        citySearchFragmentViewModel.cityNameSubmitedEvent.observe(viewLifecycleOwner, Observer { cityName ->
            navigationHost?.onSearchButtonClicked(cityName)
        })
    }

    override fun onDetach() {
        Timber.d("onDetach()")
        super.onDetach()
        navigationHost = null
    }
}
