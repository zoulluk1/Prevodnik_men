package zoulluk1.fbmi.prevodnikmen


import android.content.Intent
import android.icu.number.Precision.currency
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
//import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zoulluk1.fbmi.prevodnikmen.retrofit.App
import zoulluk1.fbmi.prevodnikmen.retrofit.model.ConvertCurency


class MainActivity : AppCompatActivity() {

    lateinit var fromCurrency: EditText
    lateinit var toCurrency: EditText
    lateinit var amount: EditText
    lateinit var btnTransfer: Button
    lateinit var transferResult: TextView
    lateinit var spinFromCur: Spinner
    lateinit var spinToCur: Spinner
    lateinit var BtnCryptoPage: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fromCurrency = findViewById(R.id.editTextInCur)
        toCurrency = findViewById(R.id.editTextOutCur)
        amount = findViewById(R.id.editNumInAmount)
        btnTransfer = findViewById(R.id.BtnPrevod)
        transferResult = findViewById(R.id.NumViewOutAmount)
        spinFromCur = findViewById(R.id.spinFromCur)
        spinToCur = findViewById(R.id.spinToCur)
        BtnCryptoPage= findViewById(R.id.btnCryptoPage)


        /*val spinner = findViewById(R.id.spinFromCur)
        val adapter = ArrayAdapter.createFromResource(this,R.array.currency,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter;
        spinner.onItemSelectedListener = this
        resultView = findViewById(R.id.)
*/
        /*val adapter = ArrayAdapter.createFromResource(this,R.array.currency,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)*/
        val currency = resources.getStringArray(R.array.currency)

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, currency)

        spinFromCur.adapter= adapter;
        spinToCur.adapter= adapter;


        spinFromCur.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.selected_item) + " " + "" + currency[position],
                    Toast.LENGTH_SHORT
                ).show()
                fromCurrency.text.clear()
                fromCurrency.text.append(currency[position].toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

       // fromCurrency.text=spinFromCur

        //spinToCur.getItemAtPosition(adapter.getPosition(currency)).toString()



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

        BtnCryptoPage.setOnClickListener {

            val intent = Intent(this, CryptoActivity::class.java)
            startActivity(intent)


        }


        }


    }
