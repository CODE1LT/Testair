package lt.code1.testair.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

abstract class DataBindingAdapter<T1, T2 : RecyclerView.ViewHolder>(var data: List<T1> = listOf()) :
    RecyclerView.Adapter<T2>() {
    companion object {

        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        @BindingAdapter("viewData")
        fun <T1, T2 : RecyclerView.ViewHolder> setData(
            recyclerView: RecyclerView,
            data: List<T1>?
        ) {
            if (recyclerView.adapter is DataBindingAdapter<*, *>) {
                (recyclerView.adapter as DataBindingAdapter<T1, T2>).data = data ?: ArrayList()
                (recyclerView.adapter as DataBindingAdapter<T1, T2>).notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T2 {
        Timber.d("onCreateViewHolder()")
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return getDataBindingViewHolder(binding, viewType)
    }

    override fun getItemCount() = data.size

    abstract override fun onBindViewHolder(holder: T2, position: Int)

    abstract override fun getItemViewType(position: Int): Int

    abstract fun getDataBindingViewHolder(binding: ViewDataBinding, viewType: Int): T2
}
