package ctrl.vanya.movieapp.core.di

import android.content.Context
import ctrl.vanya.movieapp.core.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class CoreModule(private val context: Context) {

    @Provides
    fun providesContext(): Context {
        return context
    }

    @Provides
    fun providesAppDispatcher(): AppDispatchers = AppDispatchers(Dispatchers.Main, Dispatchers.IO)

}