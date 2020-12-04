package com.test.breakingbadcharacters.webApi.retrofit


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.breakingbadcharacters.webApi.retrofit.ApiConstant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit client
 */
class RetrofitClient {
    companion object {
        private var retrofit: Retrofit? = null
        private var gson: Gson = GsonBuilder().setLenient().create()

        /**
         * function to create the Http client and return reference
         */
        fun getClient(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()
            val interceptor  = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(interceptor)
            okHttpClientBuilder.connectTimeout(1000, TimeUnit.MILLISECONDS)
            return okHttpClientBuilder.build()
        }

        /**
         * function to create the retrofit client and return reference
         */
        fun getRetrofitClient(): Retrofit? {
            if (retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(gson)).build()
            }
            return retrofit
        }
    }
}