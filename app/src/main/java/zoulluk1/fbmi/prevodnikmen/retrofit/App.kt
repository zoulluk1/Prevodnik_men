package zoulluk1.fbmi.prevodnikmen.retrofit

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object CatGetter {
        lateinit var service: MyService
    }

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://currency-converter-by-api-ninjas.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(MyService::class.java)
    }
}