@file:Suppress("DEPRECATION")

package ctrl.vanya.movieapp.data.source.remote

import android.annotation.SuppressLint
import android.os.Build
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import ctrl.vanya.movieapp.data.utils.TLSSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

object ApiClient {
    @SuppressLint("ObsoleteSdkInt")
    fun retrofitClient(
        url: String): Retrofit {

        val okHttpBuilder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val hostnameVerifier = HostnameVerifier { hostname, session ->
            val hv = HttpsURLConnection.getDefaultHostnameVerifier()
            hv.verify(hostname, session)
        }

        okHttpBuilder.hostnameVerifier(hostnameVerifier)

        okHttpBuilder.addInterceptor(createLoggingInterceptor())
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN &&
            Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
            try {
                okHttpBuilder.sslSocketFactory(TLSSocketFactory())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val okHttpClient = okHttpBuilder.build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}