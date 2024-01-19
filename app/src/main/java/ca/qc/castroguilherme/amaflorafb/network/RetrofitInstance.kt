package ca.qc.castroguilherme.amaflorafb.network

import ca.qc.castroguilherme.amaflorafb.network.amafloraApi.AmafloraApiService
import ca.qc.castroguilherme.amaflorafb.network.plantidentification.PlantIdentificationApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://perenual.com"
private const val URL_PLANTNET = "https://my-api.plantnet.org"
private const val AMAFLORA = "https://amaflora2.onrender.com"

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

        private val retrofitIdentificationInstance by lazy {

            Retrofit.Builder()
                .baseUrl(URL_PLANTNET)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val retrofitIdentificationService: PlantIdentificationApiService by lazy {
            retrofitIdentificationInstance.create(PlantIdentificationApiService::class.java)
        }

        private val retrofitAmafloraInstance by lazy {

            Retrofit.Builder()
                .baseUrl(AMAFLORA)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val retrofitAmafloraService: AmafloraApiService by lazy {
            retrofitAmafloraInstance.create(AmafloraApiService::class.java)
        }


    }
}