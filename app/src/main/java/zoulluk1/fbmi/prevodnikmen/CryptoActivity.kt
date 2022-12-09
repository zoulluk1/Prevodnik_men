package zoulluk1.fbmi.prevodnikmen


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zoulluk1.fbmi.prevodnikmen.retrofit.App
import zoulluk1.fbmi.prevodnikmen.retrofit.model.EURICrypto
import zoulluk1.fbmi.prevodnikmen.retrofit.model.CZKICrypto
import zoulluk1.fbmi.prevodnikmen.retrofit.model.USDICrypto
import java.math.BigDecimal
import java.math.RoundingMode

class CryptoActivity: AppCompatActivity() {

    lateinit var BtnCurrencyPage: Button
    lateinit var btnCryptoTransfer: Button
    lateinit var textVResponse: EditText
   // lateinit var editTextCrypto: EditText
    lateinit var spinCrypto: Spinner
    lateinit var editNumCrypto: EditText
    lateinit var CurencyToCrypto: CheckBox
    lateinit var spinCurrs:Spinner
    lateinit var spinCurrName:Spinner
    lateinit var textView: TextView
    lateinit var textView2: TextView

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
        textView= findViewById(R.id.textView)
        textView2= findViewById(R.id.textView2)

        //editTextCurr=findViewById(R.id.editTextCur)
        val sharedPrefC = getSharedPreferences("cryptoResult", Context.MODE_PRIVATE)
        val editC = sharedPrefC.edit()
        val resultC = sharedPrefC.getString("resultC","default value")
        val CrVal = sharedPrefC.getInt("CrVal",0)
        val CuVal = sharedPrefC.getInt("CuVal",0)
        val check = sharedPrefC.getBoolean("check",false)
        val amountC= sharedPrefC.getString("amountC","0")

        val cryptoCoins = resources.getStringArray(R.array.cryptoCoins)
        val cryptoCurrs = resources.getStringArray(R.array.cryptoCurrency)
        val currName = resources.getStringArray(R.array.CurrName)

        var cryptoCoin=cryptoCoins[0]
        var cryptoCurr=cryptoCurrs[0]
        var CryptoAmount=1.0;
        var CryptoResponse = 1.0;

        val adapterCoins = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,cryptoCoins)
        val adapterCurrs=ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,cryptoCurrs)

        val adapterCurrName=ArrayAdapter(this,
            android.R.layout.simple_spinner_item,currName)
        spinCrypto.adapter= adapterCoins;
        spinCurrs.adapter=adapterCurrs;
        spinCurrName.adapter= adapterCurrName;
        CurencyToCrypto.isChecked=check

        textVResponse.text.append(resultC)
        editNumCrypto.text.clear()
        editNumCrypto.text.append(amountC)


        CurencyToCrypto.setOnClickListener {

        if(CurencyToCrypto.isChecked){
                textView.text="Měna:"
                textView2.text="Cryptoměna";

            spinCrypto.adapter= adapterCurrs;
            //spinCrypto.setSelection(CuVal)
            //spinCurrs.setSelection(CrVal)
            spinCurrs.adapter=adapterCoins;
            editC.putBoolean("check",true)
            editC.commit()
            /*---------Spinner Cryptoměn-------------*/
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

                        cryptoCurr = cryptoCurrs[position]
                       // editC.putInt("CuVal",position)
                       // editC.commit()

                    //editTextCrypto.text.clear()
                    //editTextCrypto.text.append(cryptoCoin.toString())

                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
/*---------Spinner měn-------------*/
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

                        cryptoCoin = cryptoCoins[position]
                      // editC.putInt("CrVal",position)
                       // editC.commit()

                    //editTextCurr.text.clear()
                    //editTextCurr.text.append(cryptoCurr.toString())

                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        else{
            textView.text="Cryptoměna:"
            textView2.text="Měna:".toString()
            spinCrypto.adapter= adapterCoins;
            spinCurrs.adapter=adapterCurrs;
            //spinCrypto.setSelection(CrVal)
            //spinCurrs.setSelection(CuVal)
            editC.putBoolean("check",false)
            editC.commit()
/*---------Spinner Cryptoměn-------------*/
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
                        //editC.putInt("CrVal",position)
                        //editC.commit()

                    //editTextCrypto.text.clear()
                    //editTextCrypto.text.append(cryptoCoin.toString())

                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
/*---------Spinner měn-------------*/
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
                        //editC.putInt("CuVal",position)
                        //editC.commit()

                    //editTextCurr.text.clear()
                    //editTextCurr.text.append(cryptoCurr.toString())

                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        //spinCurrName.adapter= adapterCurrName
        }

        /*---------Spinner seznam měn-------------*/
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
/*------BTN----------------------*/
        btnCryptoTransfer.setOnClickListener{

            val convertCrypto: Call<HashMap<String, CZKICrypto>> = App.cryptoService.getCrypto(cryptoCoin.toString(),"czk")
            val convertCryptoUSD:Call<HashMap<String, USDICrypto>> = App.cryptoServiceUSD .getCrypto(cryptoCoin.toString(),"usd")
            val convertCryptoEUR:Call<HashMap<String, EURICrypto>> = App.cryptoServiceEUR .getCrypto(cryptoCoin.toString(),"eur")
/******___EUR___***********************************************************************************************************************************************/
            if (cryptoCurr=="eur"){
                convertCryptoEUR.enqueue(object : Callback<HashMap<String, EURICrypto>> {
                    override fun onResponse(call: Call<HashMap<String, EURICrypto>>, response: Response<HashMap<String, EURICrypto>>) {
                        val body = response.body()
                        if(body !== null){
                            CryptoResponse=body.get(cryptoCoin.toString())!!.eur.toDouble()
                            CryptoAmount=editNumCrypto.text.toString().toDouble()
                            editC.putString ("amountC",CryptoAmount.toString())
                            editC.commit()
                            if(CurencyToCrypto.isChecked){
                                var result= CryptoAmount/CryptoResponse
                                result = BigDecimal(result).setScale(6,RoundingMode.HALF_UP).toDouble();
                                textVResponse.text.clear()
                                textVResponse.text.append(result.toString())
                                editC.putString("resultC",textVResponse.text.toString())
                                editC.commit()
                            }
                            else {

                                //textVResponse.text=body.get(cryptoCoin.toString())!!.czk.toDouble().toString()

                                textVResponse.text.clear()
                                textVResponse.text.append((CryptoResponse*CryptoAmount).toString())
                                editC.putString("resultC",textVResponse.text.toString())
                                editC.commit()
                                //textVResponse.text=(CryptoResponse*CryptoAmount).toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<HashMap<String, EURICrypto >>, t: Throwable) {
                        Log.i("ASSAAS", t.message.toString())
                        Toast.makeText(this@CryptoActivity, "Something wrong", Toast.LENGTH_SHORT).show()
                    }

                })
            }
            /****___USD___**********************************************************************************************************************/
            else if (cryptoCurr=="usd"){
                convertCryptoUSD.enqueue(object : Callback<HashMap<String, USDICrypto>> {
                    override fun onResponse(call: Call<HashMap<String, USDICrypto>>, response: Response<HashMap<String, USDICrypto>>) {
                        val body = response.body()
                        if(body !== null){
                            CryptoResponse=body.get(cryptoCoin.toString())!!.usd.toDouble()
                            CryptoAmount=editNumCrypto.text.toString().toDouble()
                            editC.putString ("amountC",CryptoAmount.toString())
                            editC.commit()
                            if(CurencyToCrypto.isChecked){
                                var result= CryptoAmount/CryptoResponse
                                result = BigDecimal(result).setScale(6,RoundingMode.HALF_UP).toDouble();
                                //textVResponse.text=(result).toString()
                                textVResponse.text.clear()
                                textVResponse.text.append(result.toString())
                                editC.putString("resultC",textVResponse.text.toString())
                                editC.commit()
                            }
                            else {

                                //textVResponse.text=body.get(cryptoCoin.toString())!!.czk.toDouble().toString()


                                textVResponse.text.clear()
                                textVResponse.text.append((CryptoResponse*CryptoAmount).toString())
                                editC.putString("resultC",textVResponse.text.toString())
                                editC.commit()
                            }
                        }
                    }

                    override fun onFailure(call: Call<HashMap<String, USDICrypto >>, t: Throwable) {
                        Log.i("ASSAAS", t.message.toString())
                        Toast.makeText(this@CryptoActivity, "Something wrong", Toast.LENGTH_SHORT).show()
                    }

                })
            }
            /***___CZK___**************************************************************************************************/
            else {
                convertCrypto.enqueue(object : Callback<HashMap<String, CZKICrypto>> {
                    override fun onResponse(call: Call<HashMap<String, CZKICrypto>>, response: Response<HashMap<String, CZKICrypto>>) {
                        val body = response.body()
                        if(body !== null){
                            CryptoResponse=body.get(cryptoCoin.toString())!!.czk.toDouble()
                            CryptoAmount=editNumCrypto.text.toString().toDouble()
                            editC.putString ("amountC",CryptoAmount.toString())
                            editC.commit()
                            if(CurencyToCrypto.isChecked){
                                var result= CryptoAmount/CryptoResponse
                                result = BigDecimal(result).setScale(6,RoundingMode.HALF_UP).toDouble();
                                textVResponse.text.clear()
                                textVResponse.text.append(result.toString())
                                editC.putString("resultC",textVResponse.text.toString())
                                editC.commit()
                            }
                            else {

                                //textVResponse.text=body.get(cryptoCoin.toString())!!.czk.toDouble().toString()


                                textVResponse.text.clear()
                                textVResponse.text.append((CryptoResponse*CryptoAmount).toString())
                                editC.putString("resultC",textVResponse.text.toString())
                                editC.commit()
                            }
                        }
                    }

                    override fun onFailure(call: Call<HashMap<String, CZKICrypto >>, t: Throwable) {
                        Log.i("ASSAAS", t.message.toString())
                        Toast.makeText(this@CryptoActivity, "Something wrong", Toast.LENGTH_SHORT).show()
                    }

                })
            }





        }
    }
}