package ctrl.vanya.movieapp.feature.list.di

import ctrl.vanya.movieapp.data.repository.MainRepository
import ctrl.vanya.movieapp.data.source.remote.RemoteMainDataSource
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun provideMainRepository(
        remoteMainDataSource: RemoteMainDataSource
    ): MainRepository {
        return MainRepository.MainRepositoryImpl(remoteMainDataSource)
    }
}