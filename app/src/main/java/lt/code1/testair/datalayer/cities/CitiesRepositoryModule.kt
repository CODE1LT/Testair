package lt.code1.testair.datalayer.cities

import dagger.Binds
import dagger.Module
import dagger.Provides
import lt.code1.testair.app.ApplicationScope
import lt.code1.testair.datalayer.Database
import lt.code1.testair.datalayer.cities.entities.CitiesListEntity
import lt.code1.testair.datalayer.cities.mapper.CitiesListResponseMapper
import lt.code1.testair.datalayer.cities.mapper.CityEntityMapper
import lt.code1.testair.network.services.cities.pojos.GetCitiesListResponse
import lt.code1.testair.network.services.cities.pojos.GetCityResponse

@Module
abstract class CitiesRepositoryModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideDao(
            database: Database
        ) = database.getCitiesDao()

    }

    @Binds
    abstract fun provideCityEntityMapper(
        cityEntityMapper: CityEntityMapper
    ): Function1<GetCityResponse, @JvmSuppressWildcards List<CitiesListEntity>>

    @Binds
    abstract fun provideCitiesListResponseMapper(
        citiesListResponseMapper: CitiesListResponseMapper
    ): Function1<GetCitiesListResponse, @JvmSuppressWildcards List<CitiesListEntity>>

    @Binds
    @ApplicationScope
    abstract fun provideRepository(
        citiesRepositoryImpl: CitiesRepositoryImpl
    ): CitiesRepository
}