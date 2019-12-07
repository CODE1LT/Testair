package lt.code1.testair.features.citieslist

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.navArgs
import lt.code1.testair.NavigationHost
import lt.code1.testair.R
import lt.code1.testair.base.BaseFragment
import lt.code1.testair.databinding.FragmentCitiesListBinding
import timber.log.Timber

class CitiesListFragment : BaseFragment() {

    private var navigationHost: NavigationHost? = null
    private lateinit var citiesListFragmentViewModel: CitiesListFragmentViewModel
    private lateinit var fragmentCityListBinding: FragmentCitiesListBinding
    override val layoutId = R.layout.fragment_cities_list
    override val viewModelClass = CitiesListFragmentViewModel::class
    private val args: CitiesListFragmentArgs by navArgs()

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
        loadCityData()
    }

    private fun addReferencesToViewsAndViewModel() {
        Timber.d("addReferencesToViewsAndViewModel()")
        citiesListFragmentViewModel = getViewModel() as CitiesListFragmentViewModel
        fragmentCityListBinding = viewDataBinding as FragmentCitiesListBinding
    }

    private fun loadCityData() {
        Timber.d("loadUserData()")
        args.cityName?.let { citiesListFragmentViewModel.getCity(it) }
    }

    override fun onDetach() {
        Timber.d("onDetach()")
        super.onDetach()
        navigationHost = null
    }
}
