package com.example.fragmentstask

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_a.view.*

/**
 * Simple fragment that has Button and callbacks to notify [MainActivity]
 *
 * @author Alexander Gorin
 */
class FragmentA : Fragment() {

    private var listener: FragmentCallbacks? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)
        view.click_button.setOnClickListener {
            listener?.onButtonClicked()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentCallbacks) {
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentCallbacks")
        }
    }

    override fun onStart() {
        super.onStart()
        listener?.onShowFragment()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface FragmentCallbacks {
        fun onButtonClicked()
        fun onShowFragment()
    }

    companion object {
        fun newInstance() = FragmentA()
    }
}
