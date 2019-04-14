package com.example.fragmentstask

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_a.view.*

/**
 * Simple fragment that has Button and callback to notify [MainActivity]
 *
 * @author Alexander Gorin
 */
class FragmentA : Fragment() {

    private var listener: OnButtonClickListener? = null

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
        if (context is OnButtonClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnButtonClickListener {
        fun onButtonClicked()
    }

    companion object {
        fun newInstance() = FragmentA()
    }
}
