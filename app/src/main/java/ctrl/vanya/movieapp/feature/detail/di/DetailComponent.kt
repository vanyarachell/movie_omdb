package ctrl.vanya.movieapp.feature.detail.di

import ctrl.vanya.movieapp.core.di.CoreModule
import ctrl.vanya.movieapp.data.di.DataModule
import ctrl.vanya.movieapp.feature.detail.ui.DetailActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DetailModule::class,
        DetailViewModelModule::class,
        CoreModule::class,
        DataModule::class
    ]
)
interface DetailComponent {
    fun inject(activity: DetailActivity)
}