package lt.code1.testair.network.services

import dagger.Module
import dagger.Provides
import lt.code1.testair.network.services.cities.external.GetCityService
import retrofit2.Retrofit

@Module
abstract class ServicesModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideGetCityService(
            retrofit: Retrofit
        ): GetCityService = retrofit.create(GetCityService::class.java)

    }
}