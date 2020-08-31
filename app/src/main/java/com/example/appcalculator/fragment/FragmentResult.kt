package com.example.appcalculator.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.example.appcalculator.Constants
import com.example.appcalculator.R
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class FragmentResult() : Fragment(), View.OnClickListener {

    private var isExecuted: Boolean = false
    private var mText: String = ""
    private lateinit var mFragment: View
    private lateinit var mIvBackSpace: AppCompatImageView
    private lateinit var mIvClock: AppCompatImageView
    private lateinit var mIvCalculator: AppCompatImageView
    private lateinit var mEdtCalculation: EditText
    private lateinit var mTvResult: TextView

    companion object {
        fun newInstance(): FragmentResult {
            return FragmentResult()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFragment = LayoutInflater.from(context).inflate(R.layout.fragment_result, container, false)
        return mFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments?.getString(Constants.KEY_TEXT) != null) {
            mText = arguments?.getString(Constants.KEY_TEXT).toString()
        }
        initView()
    }

    private fun initView() {
        mIvBackSpace = mFragment.findViewById(R.id.iv_backspace)
        mIvClock = mFragment.findViewById(R.id.iv_clock)
        mIvCalculator = mFragment.findViewById(R.id.iv_calculator)
        mEdtCalculation = mFragment.findViewById(R.id.edt_calculation)
        mTvResult = mFragment.findViewById(R.id.tv_result)

        mEdtCalculation.setText(mText)


        mIvBackSpace.setOnClickListener(this)
        mIvClock.setOnClickListener(this)
        mIvCalculator.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_backspace -> {
                val cursorPosition: Int = mEdtCalculation.selectionStart
                if (cursorPosition > 0) {
                    mEdtCalculation.text =
                        mEdtCalculation.text.delete(cursorPosition - 1, cursorPosition)
                    mEdtCalculation.setSelection(cursorPosition - 1)
                }
            }
            R.id.iv_clock -> Toast.makeText(context, "Nhật ký", Toast.LENGTH_SHORT).show()
            R.id.iv_calculator -> Toast.makeText(context, "Calculator", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateView(text: String) {
        mEdtCalculation.setSelection(mEdtCalculation.text.length)
        when (text) {
            "CLEAR" -> {
                mEdtCalculation.setText("")
                mTvResult.text = ""
            }
            "Execute" -> {
                if (mEdtCalculation.text.isNotEmpty()) {
                    mTvResult.text = ""
                    mEdtCalculation.setTextColor(resources.getColor(R.color.color_green))
                    mEdtCalculation.setText(CalculationText(mEdtCalculation.text.toString()))
                    isExecuted = true
                }
            }
            else -> {
                mText = if (isExecuted && TextUtils.isDigitsOnly(text)) {
                    ""
                } else {
                    mEdtCalculation.text.toString()
                }
                isExecuted = false
                mText += text
                mEdtCalculation.setText("")
                mEdtCalculation.append(mText)
                var inputMath = mEdtCalculation.text.toString().replace("x","*")
                inputMath = inputMath.replace("÷","/")
                mTvResult.text = CalculationText(inputMath)
            }
        }

    }

    private fun CalculationText(mText: String): String {
        val engine = ScriptEngineManager().getEngineByName("rhino")
        try {
            val result: Double = engine.eval(mText) as Double
            return result.toString()
        } catch (e: ScriptException) {
            Log.d("Calculator", " ScriptEngine error: " + e.message)
        }
        return ""
    }
}