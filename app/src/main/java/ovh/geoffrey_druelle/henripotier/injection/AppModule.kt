package ovh.geoffrey_druelle.henripotier.injection

import androidx.room.RoomDatabase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ovh.geoffrey_druelle.henripotier.HenriPotierApplication
import ovh.geoffrey_druelle.henripotier.data.database.HPDatabase
import ovh.geoffrey_druelle.henripotier.network.HPApi
import ovh.geoffrey_druelle.henripotier.ui.bookDetails.BookDetailsViewModel
import ovh.geoffrey_druelle.henripotier.ui.books.BooksViewModel
import ovh.geoffrey_druelle.henripotier.ui.cart.CartViewModel
import ovh.geoffrey_druelle.henripotier.ui.splashScreen.SplashScreenViewModel
import ovh.geoffrey_druelle.henripotier.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    // Network module part
    single { provideDefaultOkHttpClient() }
    single { provideRetrofit(get()) }
    single{ provideHPService(get()) }

    // DataBase module part
    single { provideDataBase() }
    single { get<HPDatabase>().cartDao() }

    // ViewModels module part
    viewModel { SplashScreenViewModel() }
    viewModel { BooksViewModel(api = get()) }
    viewModel { BookDetailsViewModel() }
    viewModel { CartViewModel(api = get()) }
}

fun getModules(): List<Module>{
    return listOf(appModule)
}

fun provideDataBase(): RoomDatabase {
    return HPDatabase.getInstance(HenriPotierApplication.appContext)
}


// Functions related to Network Module
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