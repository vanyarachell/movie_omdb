package ctrl.vanya.movieapp.feature.detail.di

import ctrl.vanya.movieapp.data.repository.DetailRepository
import ctrl.vanya.movieapp.data.source.remote.RemoteDetailDataSource
import dagger.Module
import dagger.Provides

@Module
class DetailModule {
    @Provides
    fun provideMainRepository(
        remoteDetailDataSource: RemoteDetailDataSource
    ): DetailRepository {
        return DetailRepository.DetailRepositoryImpl(remoteDetailDataSource)
    }
}