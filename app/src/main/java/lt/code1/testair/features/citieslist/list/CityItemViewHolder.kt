package lt.code1.testair.features.citieslist.list

import androidx.recyclerview.widget.RecyclerView
import lt.code1.testair.databinding.ItemCityBinding
import lt.code1.testair.features.citieslist.data.City
import timber.log.Timber

class CityItemViewHolder(
    private val binding: ItemCityBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(city: City) {
        Timber.d("bind(), cityId=${city.id}")
        binding.item = City(
            city.id,
            city.name,
            city.dt,
            city.temp,
            city.temp_min,
            city.temp_max,
            city.icon,
            city.description
        )
        binding.executePendingBindings()
    }
}