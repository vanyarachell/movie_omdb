package ctrl.vanya.movieapp.feature.list.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ctrl.vanya.movieapp.core.base.BaseViewModelFactory
import ctrl.vanya.movieapp.core.base.ViewModelKey
import ctrl.vanya.movieapp.feature.list.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindDriverViewModel(viewModel: MainViewModel): ViewModel
}