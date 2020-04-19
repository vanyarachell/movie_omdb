package ctrl.vanya.movieapp.data.di

import ctrl.vanya.movieapp.BuildConfig
import ctrl.vanya.movieapp.data.source.DetailDataSource
import ctrl.vanya.movieapp.data.source.MainDataSource
import ctrl.vanya.movieapp.data.source.remote.*
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    // Service
    @Provides
    fun provideMainService(): MainService {
        return ApiClient.retrofitClient(
            BuildConfig.BASE_URL).create(MainService::class.java)
    }

    @Provides
    fun provideDetailService(): DetailService {
        return ApiClient.retrofitClient(
            BuildConfig.BASE_URL).create(DetailService::class.java)
    }

    // Remote Data Source
    @Provides
    fun provideMainDataSource(mainService: MainService): MainDataSource {
        return RemoteMainDataSource(
            mainService
        )
    }

    @Provides
    fun provideDetailDataSource(detailService: DetailService): DetailDataSource {
        return RemoteDetailDataSource(
            detailService
        )
    }
}