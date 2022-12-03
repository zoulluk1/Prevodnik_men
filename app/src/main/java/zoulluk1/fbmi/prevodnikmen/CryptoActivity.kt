package zoulluk1.fbmi.prevodnikmen


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zoulluk1.fbmi.prevodnikmen.retrofit.App
import zoulluk1.fbmi.prevodnikmen.retrofit.model.ICrypto
import java.math.BigDecimal
import java.math.RoundingMode

class CryptoActivity: AppCompatActivity() {

    lateinit var BtnCurrencyPage: Button
    lateinit var btnCryptoTransfer: Button
    lateinit var textVResponse: TextView
   // lateinit var editTextCrypto: EditText
    lateinit var spinCrypto: Spinner
    lateinit var editNumCrypto: EditText
    lateinit var CurencyToCrypto: CheckBox
    lateinit var spinCurrs:Spinner
    lateinit var spinCurrName:Spinner
    //lateinit var editTextCurr: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crypto_main)

        btnCryptoTransfer=findViewById(R.id.btnCryptoTransfer)
        textVResponse=findViewById(R.id.textVRes)
        //editTextCrypto=findViewById(R.id.editTextCrypto)
        spinCrypto=findViewById(R.id.spinCrypto)
        editNumCrypto= findViewById(R.id.editNumCrypto)
        CurencyToCrypto=findViewById(R.id.CurrencyToCrypto)
        spinCurrs=findViewById(R.id.spinCurrs)
        spinCurrName= findViewById(R.id.spinCurName)

        //editTextCurr=findViewById(R.id.editTextCur)

        val cryptoCoins = resources.getStringArray(R.array.cryptoCoins)
        val cryptoCurrs = resources.getStringArray(R.array.cryptoCurrency)
        val currName = resources.getStringArray(R.array.CurrName)

        var cryptoCoin=cryptoCoins[0]
        var cryptoCurr=cryptoCurrs[0]
        var CryptoAmount=1.0;
        var CryptoResponse = 1.0;

        val adapterCoins = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,cryptoCoins)
        val adapterCurrs=ArrayAdapter(this,
            android.R.layout.simple_spinner_item,cryptoCurrs)

        val adapterCurrName=ArrayAdapter(this,
            android.R.layout.simple_spinner_item,currName)
        spinCrypto.adapter= adapterCoins;
        spinCurrs.adapter=adapterCurrs;
        spinCurrName.adapter= adapterCurrName


        spinCrypto.onItemSelectedListener =object :
        AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                /*Toast.makeText(
                    this@CryptoActivity,
                    getString(R.string.selected_item) + " " + "" + cryptoCoins[position],
                    Toast.LENGTH_SHORT
                ).show()

                 */
                cryptoCoin = cryptoCoins[position]
                //editTextCrypto.text.clear()
                //editTextCrypto.text.append(cryptoCoin.toString())

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        spinCurrs.onItemSelectedListener =object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

               /* Toast.makeText(
                    this@CryptoActivity,
                    getString(R.string.selected_item) + " " + "" + cryptoCurrs[position],
                    Toast.LENGTH_SHORT
                ).show()

                */
                cryptoCurr = cryptoCurrs[position]
                //editTextCurr.text.clear()
                //editTextCurr.text.append(cryptoCurr.toString())

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        spinCurrName.onItemSelectedListener =object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        btnCryptoTransfer.setOnClickListener{

            val convertCrypto: Call<HashMap<String, ICrypto>> = App.cryptoService.getCrypto(cryptoCoin.toString(),"czk")

            convertCrypto.enqueue(object : Callback<HashMap<String, ICrypto>> {
                override fun onResponse(call: Call<HashMap<String, ICrypto>>, response: Response<HashMap<String, ICrypto>>) {
                    val body = response.body()
                    if(body !== null){
                        CryptoResponse=body.get(cryptoCoin.toString())!!.czk.toDouble()
                        CryptoAmount=editNumCrypto.text.toString().toDouble()
                        if(CurencyToCrypto.isChecked){
                            var result= CryptoAmount/CryptoResponse
                                result = BigDecimal(result).setScale(6,RoundingMode.HALF_UP).toDouble();
                            textVResponse.text=(result).toString()
                        }
                        else {

                            //textVResponse.text=body.get(cryptoCoin.toString())!!.czk.toDouble().toString()


                            textVResponse.text=(CryptoResponse*CryptoAmount).toString()
                        }
                    }
                }

                override fun onFailure(call: Call<HashMap<String, ICrypto >>, t: Throwable) {
                    Log.i("ASSAAS", t.message.toString())
                    Toast.makeText(this@CryptoActivity, "Something wrong", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}