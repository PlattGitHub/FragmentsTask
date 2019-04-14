package com.example.fragmentstask

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Activity that displays fragments depending on orientation of the device.
 * In portrait mode it has [FragmentA].
 * In landscape mode it has [FragmentA] and [FragmentB].
 *
 * @author Alexander Gorin
 */
class MainActivity : AppCompatActivity(), FragmentA.OnButtonClickListener {

    var counter = 0
    private val portrait
        get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    private val landscape
        get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    override fun onButtonClicked() {
        counter++
        if (portrait) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_a_container, FragmentB.newInstance(counter), FragmentB.TAG)
                .addToBackStack(null)
                .commit()
        }
        if (landscape) {
            val fragmentB = supportFragmentManager.findFragmentByTag(FragmentB.TAG) as FragmentB?
            fragmentB?.setClicks(counter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNTER_KEY)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_a_container, FragmentA.newInstance()).commit()

        if (landscape) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_b_container, FragmentB.newInstance(counter), FragmentB.TAG)
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(COUNTER_KEY, counter)
    }

    private companion object {
        const val COUNTER_KEY = "COUNTER"
    }
}

