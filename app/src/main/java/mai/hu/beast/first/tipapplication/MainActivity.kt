package mai.hu.beast.first.tipapplication

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat


private const val INITIAL_TIP_PER = 15

class MainActivity : ComponentActivity() {

    private lateinit var tipPer : TextView
    private lateinit var textView5 :EditText
    private lateinit var seekBar : SeekBar
    private lateinit var tipOutput :TextView
    private lateinit var billOutput : TextView
    private lateinit var qualityTip :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar=findViewById(R.id.seekBar)
        tipPer = findViewById(R.id.tipPer)
        textView5 = findViewById(R.id.textView5)
        tipOutput = findViewById(R.id.tipOutput)
        billOutput = findViewById(R.id.billOutput)
        qualityTip=findViewById(R.id.qualityTip)

        seekBar.progress = INITIAL_TIP_PER
        tipPer.text="$INITIAL_TIP_PER"
        qualityTip.text="Good"

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tipPer.text = "$progress%"
                calculateTipAndTotalB()
                tipQuality(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        textView5.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                calculateTipAndTotalB()
            }
        })
    }

    private fun tipQuality(tipPercent : Int) {


        val tipQuality = when(tipPercent){
            in 0..5 -> "Poor"
            in 6..15 -> "Good"
            in 16..21 ->"Excellent"
            in 22..25 -> "Andha paisa ? "
            else -> "OK bhai "
        }
       qualityTip.text = tipQuality

        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat()/seekBar.max,
            ContextCompat.getColor(this,R.color.red),
            ContextCompat.getColor(this,R.color.green)
        ) as Int
        qualityTip.setTextColor(color)
    }

    private fun calculateTipAndTotalB() {

        if(textView5.text.isEmpty()){
            tipOutput.text = " "
            billOutput.text= " "
            return
        }

        val billAmount = textView5.text.toString().toDouble()
        val tipPer = seekBar.progress

        val tipAmount = billAmount * tipPer / 100
        val finalBill = billAmount + tipAmount

        tipOutput.text = "%.2f".format(tipAmount)
        billOutput.text= "%.2f".format(finalBill)

    }
}

