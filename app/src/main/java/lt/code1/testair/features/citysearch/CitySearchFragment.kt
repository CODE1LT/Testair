package lt.code1.testair.features.citysearch

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import lt.code1.testair.NavigationHost
import lt.code1.testair.R
import lt.code1.testair.base.BaseFragment
import timber.log.Timber
import androidx.lifecycle.Observer
import es.dmoral.toasty.Toasty
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
        observeHistoryButtonClickedEvent()
    }

    private fun addReferencesToViewsAndViewModel() {
        Timber.d("addReferencesToViewsAndViewModel()")
        citySearchFragmentViewModel = getViewModel() as CitySearchFragmentViewModel
        fragmentCitySearchBinding = viewDataBinding as FragmentCitySearchBinding
    }

    private fun observeSearchButtonClickedEvent() {
        Timber.d("observeUserItemClickedEvent()")
        citySearchFragmentViewModel.cityNameSubmitedEvent.observe(
            viewLifecycleOwner,
            Observer { cityName ->
                if (cityName.isNullOrEmpty()) {
                    showUnsuccessNotification(R.string.f_city_search_tst_no_city_name)
                } else {
                    hideKeyboard(
                        activity as Context,
                        fragmentCitySearchBinding.fCitySearchCityNameEt
                    )
                    navigationHost?.onSearchButtonClicked(cityName)
                }
            })
    }

    private fun observeHistoryButtonClickedEvent() {
        Timber.d("observeUserItemClickedEvent()")
        citySearchFragmentViewModel.retrieveSearchHistoryEvent.observe(
            viewLifecycleOwner,
            Observer {
                hideKeyboard(
                    activity as Context,
                    fragmentCitySearchBinding.fCitySearchCityNameEt
                )
                navigationHost?.onSearchButtonClicked("")

            })
    }

    @Suppress("SameParameterValue")
    private fun showUnsuccessNotification(messageId: Int) {
        val toast = Toasty.error(
            requireActivity(),
            getString(messageId),
            Toast.LENGTH_SHORT,
            true
        )
        toast.setGravity(Gravity.TOP, 0, 80)
        toast.show()
    }

    private fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDetach() {
        Timber.d("onDetach()")
        super.onDetach()
        navigationHost = null
    }
}
