package lt.code1.testair.features.citieslist.list

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lt.code1.testair.databinding.ItemCityBinding
import lt.code1.testair.features.citieslist.data.City
import lt.code1.testair.network.BASE_WEATHER_ICON_API_URL
import timber.log.Timber
import javax.inject.Inject

private const val WEATHER_ICON_TYPE = "@2x.png"

class CityItemViewHolder (
    private val context: Context,
    private val binding: ItemCityBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(city: City) {
        Timber.d("bind(), cityId=${city.id}")
        binding.item = City(
            city.id,
            city.name,
            city.dt,
            city.dayName,
            city.dayNumber,
            city.temp,
            city.temp_min,
            city.temp_max,
            city.icon,
            city.description
        )
        Glide.with(context).load(BASE_WEATHER_ICON_API_URL + city.icon + WEATHER_ICON_TYPE).
            into(binding.iCityIvWeatherIcon)
        binding.executePendingBindings()
    }
}