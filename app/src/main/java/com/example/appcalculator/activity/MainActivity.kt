package com.example.appcalculator.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.appcalculator.Constants
import com.example.appcalculator.R
import com.example.appcalculator.fragment.FragmentKeyBoard
import com.example.appcalculator.fragment.FragmentResult


class MainActivity : AppCompatActivity(),FragmentKeyBoard.onTextChangeListener {
    private lateinit var mTransaction: FragmentTransaction
    private lateinit var mFragmentResult: FragmentResult
    private lateinit var mFragmentKeyBoard: FragmentKeyBoard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mFragmentKeyBoard = FragmentKeyBoard.newInstance()
        mTransaction = supportFragmentManager.beginTransaction()
        mFragmentResult = FragmentResult.newInstance()
        mTransaction.add(R.id.fragment_container_key_board, mFragmentKeyBoard)
        mTransaction.add(R.id.fragment_container_result,mFragmentResult)
        mTransaction.commit()
    }


    override fun onTextChange(text: String) {
        val bundle = Bundle().apply {
            putString(Constants.KEY_TEXT,text)
        }
        val mNewFragmentResult = supportFragmentManager.findFragmentById(R.id.fragment_container_result) as FragmentResult?

        if (mNewFragmentResult != null) {
            mNewFragmentResult.updateView(text)
        } else {
            val newTransaction = supportFragmentManager.beginTransaction()
            mFragmentResult = FragmentResult.newInstance()
            mFragmentResult.arguments = bundle
            newTransaction.replace(R.id.fragment_container_result,mFragmentResult)
            newTransaction.addToBackStack(null)
            newTransaction.commit()
        }
    }

}