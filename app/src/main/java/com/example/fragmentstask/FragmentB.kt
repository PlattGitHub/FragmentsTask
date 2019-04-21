package com.example.fragmentstask

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_b.*

/**
 * Simple fragment that has TextView to display how many times Button in [FragmentA] was clicked.
 *
 * @author Alexander Gorin
 */
class FragmentB : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("asdasd", "onCreateView")
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("asdasd", "onViewCreated")

        arguments?.getInt(COUNTER_ARG)?.let {
            setClicks(it)
        }
    }


    fun setClicks(value: Int) {
        clicks_text?.text = value.toString()
    }

    companion object {
        const val TAG = "TAG_FRAGMENT_B"
        private const val COUNTER_ARG = "COUNTER"
        fun newInstance(count: Int) = FragmentB().apply {
            arguments = Bundle().apply {
                putInt(COUNTER_ARG, count)
            }
        }
    }
}
