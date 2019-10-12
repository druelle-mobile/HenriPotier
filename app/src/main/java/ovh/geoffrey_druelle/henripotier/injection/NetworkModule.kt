package ovh.geoffrey_druelle.henripotier.injection

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import ovh.geoffrey_druelle.henripotier.network.HPApi
import ovh.geoffrey_druelle.henripotier.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { provideDefaultOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideHPService(get()) }
}

fun provideDefaultOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(apiInterceptor())
        .build()
}

fun apiInterceptor() = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
        .header("User-Agent","HenriPotier")
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .build())
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun provideHPService(retrofit: Retrofit): HPApi = retrofit.create(HPApi::class.java)