package zoulluk1.fbmi.prevodnikmen.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import zoulluk1.fbmi.prevodnikmen.retrofit.model.ConvertCurency

interface MyService {

    @Headers(
        "X-RapidAPI-Key: 00ba8e0973mshf38ef47c69f7f44p1ca08djsnfd5644985b79",
        "X-RapidAPI-Host: currency-converter-by-api-ninjas.p.rapidapi.com"
    )
    @GET("/v1/convertcurrency")
    fun getConvertCurency(
        @Query("have") have: String,
        @Query("want") want: String,
        @Query("amount") amount: Number

    ): Call<ConvertCurency>
}