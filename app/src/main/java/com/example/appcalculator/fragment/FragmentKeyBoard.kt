package com.example.appcalculator.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils.isDigitsOnly
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcalculator.R
import com.example.appcalculator.adapter.KeyBoardAdapter
import org.w3c.dom.Text
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class FragmentKeyBoard() : Fragment() {

    private var onOperator: Boolean = false
    private lateinit var mListener: onTextChangeListener
    private lateinit var mView: View
    private lateinit var mLvKeyBoard: RecyclerView
    private lateinit var mAdapter: KeyBoardAdapter

    private var mListKeyBoard = ArrayList<String>()

    private var mText: String = ""

    companion object { // tương đương static
        fun newInstance(): FragmentKeyBoard {
            return FragmentKeyBoard()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onTextChangeListener) {
            mListener = context as onTextChangeListener
        } else {
            throw ClassCastException(
                "$context must implement onTextChangeListener"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_key_board, container, false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        mLvKeyBoard = mView.findViewById<RecyclerView>(R.id.lv_key_board)
        mAdapter = KeyBoardAdapter(
            mListCharacter = mListKeyBoard as ArrayList<String>,
            mContext = context, mCallback = object : KeyBoardAdapter.Callback {
                override fun onClick(position: Int) {
                    if (isDigitsOnly(mListKeyBoard[position])) {
                        onOperator = false
                        mListener.onTextChange(mListKeyBoard[position])
                    } else if (isOperator(mListKeyBoard[position]) && !onOperator) {
                        onOperator = true
                        mListener.onTextChange(mListKeyBoard[position])
                    }
                    if (mListKeyBoard[position] == "C") {
                        mListener.onTextChange("CLEAR")
                    }
                    if (mListKeyBoard[position] == "="){
                        mListener.onTextChange("Execute")
                    }
                }

            })
        mLvKeyBoard.apply {
            layoutManager = GridLayoutManager(activity, 4)
            hasFixedSize()
            adapter = mAdapter
        }
    }

    private fun isOperator(s: String): Boolean {
        if ( s == "÷" || s == "x" || s == "+" || s == "-" || s == "+/-") {
            return true
        }
        return false
    }

    private fun initData() {
        mListKeyBoard.add("C")
        mListKeyBoard.add("()")
        mListKeyBoard.add("%")
        mListKeyBoard.add("÷")
        mListKeyBoard.add("7")
        mListKeyBoard.add("8")
        mListKeyBoard.add("9")
        mListKeyBoard.add("x")
        mListKeyBoard.add("4")
        mListKeyBoard.add("5")
        mListKeyBoard.add("6")
        mListKeyBoard.add("-")
        mListKeyBoard.add("1")
        mListKeyBoard.add("2")
        mListKeyBoard.add("3")
        mListKeyBoard.add("+")
        mListKeyBoard.add("+/-")
        mListKeyBoard.add("0")
        mListKeyBoard.add(",")
        mListKeyBoard.add("=")
    }


    interface onTextChangeListener {
        fun onTextChange(text: String)
    }

}