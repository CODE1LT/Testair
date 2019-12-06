package lt.code1.testair.datalayer.cities.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = false)
    val rowId: Long,
    val name: String,
    val cityId: String
)