package ru.nikanorovsa.bankapp.data.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Класс фабрика для создания объекта  Retrofit
//object RetrofitFactory {
//    // Перахватчик, чтобы получать данные от запросов.И выводить их в лог для отладки приложения
//    private val interceptor = run {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.apply {
//            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        }
//    }
//    // Http клиент для использования в Retrofit, к нему подключен "Перехватчик"
//    val builder = OkHttpClient.Builder()
//        .addNetworkInterceptor(interceptor)
//        .connectTimeout(30, TimeUnit.SECONDS)
//        .readTimeout(20, TimeUnit.SECONDS)
//        .writeTimeout(20, TimeUnit.SECONDS)
//
//    val gson = GsonBuilder()
//        .setLenient()
//        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        .create()
//
//    // Создаю экземляр класса Retrofit подключаю RxJava2.
//    private fun retrofit(): Retrofit = Retrofit.Builder()
//        .baseUrl("https://swiftlist.ru/test/")
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .client(builder.build())
//        .build()
//
//    val cardsController: CardsController = retrofit().create(CardsController::class.java)
//}