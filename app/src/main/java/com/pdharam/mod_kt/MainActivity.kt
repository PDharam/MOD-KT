package com.pdharam.mod_kt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var resultList: MutableList<Result>? = null
    var resultAdapter: ResultAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setListeners()
    }

    private fun init() {
        resultList = mutableListOf()
        rv_result.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        resultAdapter = ResultAdapter(this, resultList as ArrayList<Result>)
        rv_result.adapter = resultAdapter
    }


    private fun setListeners() {
        btn_submit.setOnClickListener {
            if (validateFields()) {
                hideKeyboard()
                resultList?.clear()
                compute(edt_value.text.toString().toInt(), edt_match_value.text.toString().toInt())
            }
        }
    }

    private fun validateFields(): Boolean {
        return when {
            TextUtils.isEmpty(edt_value.text) -> {
                toast("Please Enter Value")
                false
            }
            edt_value.text.toString() == "0" -> {
                toast("Value Must not be Zero (0)")
                false
            }
            TextUtils.isEmpty(edt_match_value.text) -> {
                toast("Please Enter Match Value")
                false
            }
            edt_match_value.text.toString() == "0" -> {
                toast("Match Value Must not be Zero (0)")
                false
            }
            edt_match_value.text.toString().toInt() > edt_value.text.toString().toInt() -> {
                toast("Match Value Must not be grater then Value")
                false
            }
            else -> true
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun compute(num: Int?, matchNum: Int): Int {
        var nearestValue = 0
        var setActive = false
        loop@ for (i in 1..num!!) {
            if (num % i == 0) {

                var result = Result()
                result.text = "$i x ${num / i} =$num"
                result.isNearest = false

                if (i <= matchNum) nearestValue = i
                else if (!setActive) {
                    setActive = true
                    resultList?.get(resultList?.size?.minus(1)!!)?.isNearest = setActive
                }

                resultList?.add(result)

            }
        }

        resultAdapter?.notifyDataSetChanged()
        println("NEARTEST VALUE:  $nearestValue")
        return nearestValue
    }

    private fun hideKeyboard(){
        val inputManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED) // It can be done by show_forced too
    }

}
