package ca.qc.castroguilherme.amaflorafb.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://perenual.com"

class RetrofitInstance {
    companion object {
        private val retrofitInstance by lazy {

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val retrofitService: PlantsApiService by lazy {
            retrofitInstance.create(PlantsApiService::class.java)
        }
    }
}