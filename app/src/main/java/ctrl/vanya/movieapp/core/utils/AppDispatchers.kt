package ctrl.vanya.movieapp.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AppDispatchers @Inject constructor(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)