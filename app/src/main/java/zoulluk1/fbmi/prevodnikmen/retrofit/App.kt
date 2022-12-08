package zoulluk1.fbmi.prevodnikmen.retrofit

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object CatGetter {
        lateinit var service: MyService
        lateinit var cryptoService: MyCryptoService
        lateinit var cryptoServiceEUR: EURCryptoService
        lateinit var cryptoServiceUSD: USDCryptoService
    }

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://currency-converter-by-api-ninjas.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(MyService::class.java)

        val retrofit2 = Retrofit.Builder()
            .baseUrl("https://coingecko.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        cryptoService = retrofit2.create(MyCryptoService::class.java)
        cryptoServiceEUR = retrofit2.create(EURCryptoService::class.java)
        cryptoServiceUSD = retrofit2.create(USDCryptoService::class.java)
    }
}