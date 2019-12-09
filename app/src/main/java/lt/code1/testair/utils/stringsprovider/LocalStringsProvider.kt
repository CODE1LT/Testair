package lt.code1.testair.utils.stringsprovider

import android.content.Context
import androidx.lifecycle.MutableLiveData
import lt.code1.testair.R
import javax.inject.Inject

class LocalStringsProvider @Inject constructor(private val context: Context) : StringsProvider() {

    override fun getString(stringId: StringId) =
        getStringResourceId(stringId)
            .let { context.resources.getString(it) }
            .let { MutableLiveData(it) }

    private fun getStringResourceId(stringId: StringId) =
        when (stringId) {
            StringId.HISTORY_BUTTON_TEXT -> R.string.f_city_search_history_bt_text
        }
}