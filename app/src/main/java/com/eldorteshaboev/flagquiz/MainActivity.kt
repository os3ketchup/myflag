package com.eldorteshaboev.flagquiz

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import com.eldorteshaboev.flagquiz.models.Flag
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var flagArrayList: ArrayList<Flag>
    lateinit var buttonArrayList: ArrayList<Button>
    var count = 0
    var countryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonArrayList = ArrayList()
        createObject()
        buttonStoreCount()
    }

    private fun createObject() {
        flagArrayList = ArrayList()
        flagArrayList.add(Flag("Argentina", R.drawable.argentina))
        flagArrayList.add(Flag("Paraguay", R.drawable.paraguay))
        flagArrayList.add(Flag("Pakistan", R.drawable.pakistan))
        flagArrayList.add(Flag("Mexico", R.drawable.mexico))
        flagArrayList.add(Flag("Uzbekistan", R.drawable.uzbekistan))
        flagArrayList.add(Flag("Nepal", R.drawable.nepal))
        flagArrayList.add(Flag("Madagascar", R.drawable.madagascar))
        flagArrayList.add(Flag("Italy", R.drawable.italy))
        flagArrayList.add(Flag("Denmark", R.drawable.denmark))
        flagArrayList.add(Flag("Canada", R.drawable.canada))
    }

    fun buttonStoreCount() {
        imageFlags.setImageResource(flagArrayList[count].image!!)
        line1_answer.removeAllViews()
        line2_btnTop.removeAllViews()
        line3_btnBelow.removeAllViews()
        countryName = ""
        buttonStore(flagArrayList[count].name)
    }

    private fun buttonStore(countryName: String?) {
        val btnArray: ArrayList<Button> = randomBtn(countryName)
        for (i in 0..5) {
            line2_btnTop.addView(btnArray[i])
        }
        for (i in 6..11) {
            line3_btnBelow.addView(btnArray[i])
        }
    }

    private fun randomBtn(countryName: String?): java.util.ArrayList<Button> {
        val array = ArrayList<Button>()
        val arrayText = ArrayList<String>()

        countryName?.forEach {
            arrayText.add(it.toChar().toString())
        }
        if (arrayText.size != 12) {
            val str = "ABCDEFGHIJKLMNOPQRSTUVXYZ"
            for (i in arrayText.size until 12) {
                val random = Random().nextInt(str.length)
                arrayText.add(str[random].toString())
            }
        }
        arrayText.shuffle()

        for (i in 0 until arrayText.size) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0F
            )
            button.text = arrayText[i]
            button.setOnClickListener(this)
            array.add(button)
        }
        return array
    }

    override fun onClick(p0: View?) {
        val button1 = p0 as Button
        if (buttonArrayList.contains(button1)) {
            line1_answer.removeView(button1)
            var isHas = false
            line2_btnTop.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    countryName = countryName.substring(0, countryName.length - 1)
                    isHas = true
                }
            }
            line3_btnBelow.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    if (!isHas) {
                        countryName = countryName.substring(0, countryName.length - 1)
                    }
                }
            }

        } else {
            button1.visibility = View.INVISIBLE
            countryName += button1.text.toString().uppercase()
            val button2 = Button(this)
            button2.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            button2.text = button1.text
            button2.setOnClickListener(this)
            buttonArrayList.add(button2)
            line1_answer.addView(button2)
            checkStatement()

        }
    }

    private fun checkStatement() {
        if (countryName==flagArrayList[count].name?.uppercase()){
            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
            if (count==flagArrayList.size-1){
                count=0
            }else{
                count++
            }
            buttonStoreCount()
        } else {
            if (countryName.length==flagArrayList[count].name?.length){
                Toast.makeText(this, "wrong answer", Toast.LENGTH_SHORT).show()
                line1_answer.removeAllViews()
                line2_btnTop.removeAllViews()
                line3_btnBelow.removeAllViews()
                buttonStore(flagArrayList[count].name)
                countryName = ""
            }
        }
    }


}