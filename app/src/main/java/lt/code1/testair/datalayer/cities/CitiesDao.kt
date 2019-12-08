package lt.code1.testair.datalayer.cities

import androidx.room.*
import lt.code1.testair.datalayer.cities.entities.CityEntity

private const val CITIES_SEARCH_HISTORY_SIZE_LIMIT = 5

@Dao
abstract class CitiesDao {

    @Query("SELECT * FROM cities")
    abstract fun queryCities(): List<CityEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addCity(city: CityEntity)

    @Query("DELETE FROM cities WHERE rowId NOT IN (SELECT rowId FROM cities ORDER BY rowId DESC LIMIT $CITIES_SEARCH_HISTORY_SIZE_LIMIT)")
    abstract fun cleanupSearchHistory()

}