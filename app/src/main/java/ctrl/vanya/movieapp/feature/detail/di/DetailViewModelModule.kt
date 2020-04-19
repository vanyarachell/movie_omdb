package ctrl.vanya.movieapp.feature.detail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ctrl.vanya.movieapp.core.base.BaseViewModelFactory
import ctrl.vanya.movieapp.core.base.ViewModelKey
import ctrl.vanya.movieapp.feature.detail.ui.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun bindDriverViewModel(viewModel: DetailViewModel): ViewModel
}