package zoulluk1.fbmi.prevodnikmen


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zoulluk1.fbmi.prevodnikmen.retrofit.App
import zoulluk1.fbmi.prevodnikmen.retrofit.model.ConvertCurency


class MainActivity : AppCompatActivity() {

    lateinit var amount: EditText
    lateinit var btnTransfer: Button
    lateinit var transferResult: TextView
    lateinit var spinFromCur: Spinner
    lateinit var spinToCur: Spinner
    lateinit var BtnCryptoPage: Button
    lateinit var spinNames:Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        amount = findViewById(R.id.editNumInAmount)
        btnTransfer = findViewById(R.id.BtnPrevod)
        transferResult = findViewById(R.id.NumViewOutAmount)
        spinFromCur = findViewById(R.id.spinFromCur)
        spinToCur = findViewById(R.id.spinToCur)
        BtnCryptoPage= findViewById(R.id.btnCryptoPage)
        spinNames=findViewById(R.id.spinNames)

        val sharedPref = getSharedPreferences("result", Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        val result = sharedPref.getString("result","1")
        val inCur = sharedPref.getInt("inCur",0)
        val toCur = sharedPref.getInt("toCur",0)
        val amountM = sharedPref.getString("amount","1")
        transferResult.text=result;
        amount.text.clear();
        amount.text.append(amountM);

        val currency = resources.getStringArray(R.array.currency)
        val currNames= resources.getStringArray(R.array.CurrNames)
        var fromCurrencyV= currency[4]
        var toCurrencyV= currency[3]


        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, currency)
        val CurrAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, currNames)

        spinFromCur.adapter= adapter;
        spinToCur.adapter= adapter;
        spinNames.adapter=CurrAdapter;

        spinFromCur.setSelection(inCur);
        spinToCur.setSelection(toCur);
        spinFromCur.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                fromCurrencyV=currency[position]
                edit.putInt("inCur",position)
                edit.commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                spinFromCur.setSelection(sharedPref.getInt("inCur",0))
            }
        }

        spinToCur.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                toCurrencyV = currency[position]
                edit.putInt("toCur",position)
                edit.commit()
            }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    spinToCur.setSelection(sharedPref.getInt("toCur",0))

            }
        }
            spinNames.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {


                }

            override fun onNothingSelected(parent: AdapterView<*>) {
                spinNames.setSelection(0)
            }
        }



        btnTransfer.setOnClickListener{

            val convertCurency: Call<ConvertCurency> = App.service.getConvertCurency(fromCurrencyV.toString(),toCurrencyV.toString(),amount.text.toString().toDouble())

            convertCurency.enqueue(object : Callback<ConvertCurency> {
                override fun onResponse(call: Call<ConvertCurency>, response: Response<ConvertCurency>) {
                    val body = response.body()
                    if(body !== null){
                        transferResult.text=body.new_amount.toString()
                        edit.putString("result",transferResult.text.toString())
                        edit.putString("amount",amount.text.toString())
                        edit.commit()
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
