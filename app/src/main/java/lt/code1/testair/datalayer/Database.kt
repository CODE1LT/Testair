package lt.code1.testair.datalayer

import androidx.room.Database
import androidx.room.RoomDatabase
import lt.code1.testair.datalayer.cities.CitiesDao
import lt.code1.testair.datalayer.cities.entities.CityEntity

@Database(
    entities = [
        CityEntity::class
    ], version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun getCitiesDao(): CitiesDao
}