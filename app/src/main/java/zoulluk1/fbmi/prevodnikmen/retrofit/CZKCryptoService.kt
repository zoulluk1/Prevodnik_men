package zoulluk1.fbmi.prevodnikmen.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import zoulluk1.fbmi.prevodnikmen.retrofit.model.CZKICrypto

interface CZKCryptoService {
    @Headers(
        "X-RapidAPI-Key: 00ba8e0973mshf38ef47c69f7f44p1ca08djsnfd5644985b79",
        "X-RapidAPI-Host: coingecko.p.rapidapi.com"
    )
    @GET("/simple/price")
    fun getCrypto(
        @Query("ids") ids: String,
        @Query("vs_currencies") vs_currencies: String
        //@Query("precision") precision: String

    ): Call<HashMap<String, CZKICrypto>>
}