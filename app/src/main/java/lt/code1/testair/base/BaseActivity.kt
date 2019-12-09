package lt.code1.testair.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import lt.code1.testair.BR
import timber.log.Timber
import java.lang.RuntimeException
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewDataBinding: ViewDataBinding
    abstract val layoutId: Int
    open val dataBindingEnabled: Boolean = true
    open val viewModelClass: KClass<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (dataBindingEnabled) {
            initViewDataBinding()
        }
    }

    private fun initViewDataBinding() {
        Timber.d("initViewDataBinding()")
        viewDataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, layoutId)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
        viewDataBinding.setVariable(BR.viewmodel, getViewModel())
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