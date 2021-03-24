package ru.nikanorovsa.bankapp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikanorovsa.bankapp.BuildConfig
import ru.nikanorovsa.bankapp.R
import ru.nikanorovsa.bankapp.data.retrofit.CardsController
import ru.nikanorovsa.bankapp.data.room.AppDatabase
import ru.nikanorovsa.bankapp.data.room.CardsDao
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "cards_model")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCardsDao(appDatabase: AppDatabase) : CardsDao = appDatabase.cardsDao()

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    } else {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGSONBuilder(@ApplicationContext context: Context) : Gson = GsonBuilder()
        .setLenient()
        .setDateFormat(context.getString(R.string.retrofit_gson_date_format))
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson,okHttpClient: OkHttpClient, @ApplicationContext context: Context) = Retrofit.Builder()
        .baseUrl(context.getString(R.string.retrofit_url))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideController(retrofit: Retrofit): CardsController = retrofit.create(CardsController::class.java)
}