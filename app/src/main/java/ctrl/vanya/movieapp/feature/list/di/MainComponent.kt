package ctrl.vanya.movieapp.feature.list.di

import ctrl.vanya.movieapp.core.di.CoreModule
import ctrl.vanya.movieapp.data.di.DataModule
import ctrl.vanya.movieapp.feature.list.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainModule::class,
        MainViewModelModule::class,
        CoreModule::class,
        DataModule::class
    ]
)
interface MainComponent {
    fun inject(activity: MainActivity)
}