package zoulluk1.fbmi.prevodnikmen


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
//import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zoulluk1.fbmi.prevodnikmen.retrofit.App
import zoulluk1.fbmi.prevodnikmen.retrofit.model.ConvertCurency
import android.widget.TextView
import android.widget.Toast



class MainActivity : AppCompatActivity() {
    lateinit var fromCurrency: EditText
    lateinit var toCurrency: EditText
    lateinit var amount: EditText
    lateinit var btnTransfer: Button
    lateinit var transferResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fromCurrency = findViewById(R.id.editTextInCur)
        toCurrency = findViewById(R.id.editTextOutCur)
        amount = findViewById(R.id.editNumInAmount)
        btnTransfer = findViewById(R.id.BtnPrevod)
        transferResult = findViewById(R.id.NumViewOutAmount)


        btnTransfer.setOnClickListener{

            val convertCurency: Call<ConvertCurency> = App.service.getConvertCurency(fromCurrency.text.toString(),toCurrency.text.toString(),amount.text.toString().toDouble())

            convertCurency.enqueue(object : Callback<ConvertCurency> {
                override fun onResponse(call: Call<ConvertCurency>, response: Response<ConvertCurency>) {
                    val body = response.body()
                    if(body !== null){
                        transferResult.text=body.new_amount.toString()
                    }
                }

                override fun onFailure(call: Call<ConvertCurency>, t: Throwable) {
                    Log.i("ASSAAS", t.message.toString())
                    Toast.makeText(this@MainActivity, "Something wrong", Toast.LENGTH_SHORT).show()
                }

            })
        }


        }

    }
//}