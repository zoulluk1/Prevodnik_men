package zoulluk1.fbmi.prevodnikmen

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zoulluk1.fbmi.prevodnikmen.retrofit.App
import zoulluk1.fbmi.prevodnikmen.retrofit.model.ICrypto

class CryptoActivity: AppCompatActivity() {

    lateinit var BtnCurrencyPage: Button
    lateinit var btnCryptoTransfer: Button
    lateinit var textVResponse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crypto_main)

        btnCryptoTransfer=findViewById(R.id.btnCryptoTransfer)
        textVResponse=findViewById(R.id.textVRes)
        val cryproCoin="litecoin";
        /*BtnCurrencyPage.text=findViewById(R.id.BtnCurrencyPage)
        BtnCurrencyPage.setOnClickListener {

           // val intent = Intent(this, MainActivity::class.java)
           // startActivity(intent)
        }*/
        btnCryptoTransfer.setOnClickListener{

            val convertCrypto: Call<HashMap<String, ICrypto>> = App.cryptoService.getCrypto(cryproCoin,"czk")

            convertCrypto.enqueue(object : Callback<HashMap<String, ICrypto>> {
                override fun onResponse(call: Call<HashMap<String, ICrypto>>, response: Response<HashMap<String, ICrypto>>) {
                    val body = response.body()
                    if(body !== null){
                        textVResponse.text=body.get(cryproCoin)?.czk.toString()
                    }
                }

                override fun onFailure(call: Call<HashMap<String, ICrypto>>, t: Throwable) {
                    Log.i("ASSAAS", t.message.toString())
                    Toast.makeText(this@CryptoActivity, "Something wrong", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}