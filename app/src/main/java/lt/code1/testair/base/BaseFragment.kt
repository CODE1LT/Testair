package lt.code1.testair.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import lt.code1.testair.BR
import timber.log.Timber
import java.lang.RuntimeException
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewDataBinding: ViewDataBinding
    abstract val layoutId: Int
    open val dataBindingEnabled: Boolean = true
    open val viewModelClass: KClass<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView()")
        if (view != null) {
            return view
        }

        return if (dataBindingEnabled) {
            viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            viewDataBinding.lifecycleOwner = this
            viewDataBinding.setVariable(BR.viewmodel, getViewModel())
            viewDataBinding.executePendingBindings()
            viewDataBinding.root
        } else {
            inflater.inflate(layoutId, container, false)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun getViewModel(): ViewModel {
        Timber.d("getViewModel()")
        return viewModelClass?.let { viewModelClass ->
            ViewModelProviders.of(this, viewModelFactory)
                .get(viewModelClass.java as Class<ViewModel>)
        } ?: throw RuntimeException("viewModelClass is null!")
    }
}