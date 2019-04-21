package com.example.fragmentstask

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Activity that displays fragments depending on orientation of the device.
 * In portrait mode it has [FragmentA].
 * In landscape mode it has [FragmentA] and [FragmentB].
 *
 * @author Alexander Gorin
 */
class MainActivity : AppCompatActivity(), FragmentA.FragmentCallbacks {

    private var counter = 0
    private var fragmentAfterRotationName: String = ""
    private val portrait
        get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    private val landscape
        get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    override fun onButtonClicked() {
        counter++
        if (portrait) {
            fragmentAfterRotationName = FRAGMENT_B
            replaceFragment(
                container = R.id.fragment_a_container,
                fragment = FragmentB.newInstance(counter),
                tag = FragmentB.TAG,
                backStack = true
            )
        }
        if (landscape) {
            val fragmentB = supportFragmentManager.findFragmentByTag(FragmentB.TAG) as FragmentB?
            fragmentB?.setClicks(counter)
        }
    }

    override fun onShowFragment() {
        if (portrait) fragmentAfterRotationName = FRAGMENT_A
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("asdasd", "onCreateActivity")

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNTER_KEY)
            fragmentAfterRotationName = savedInstanceState.getString(CURRENT_FRAGMENT) ?: ""
        }

        if (portrait) {
            if (fragmentAfterRotationName.isNotEmpty()) {
                when (fragmentAfterRotationName) {
                    FRAGMENT_A -> replaceFragment(
                        container = R.id.fragment_a_container,
                        fragment = FragmentA.newInstance()
                    )
                    FRAGMENT_B -> replaceFragment(
                        container = R.id.fragment_a_container,
                        fragment = FragmentB.newInstance(counter),
                        tag = FragmentB.TAG
                    )
                }
            } else {
                replaceFragment(R.id.fragment_a_container, FragmentA.newInstance())
            }
        }

        if (landscape) {
            replaceFragment(
                container = R.id.fragment_a_container,
                fragment = FragmentA.newInstance()
            )
            replaceFragment(
                container = R.id.fragment_b_container,
                fragment = FragmentB.newInstance(counter),
                tag = FragmentB.TAG
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (landscape) finish()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(COUNTER_KEY, counter)
        outState?.putString(CURRENT_FRAGMENT, fragmentAfterRotationName)
    }

    private companion object {
        const val COUNTER_KEY = "COUNTER"
        const val CURRENT_FRAGMENT = "CURRENT_FRAGMENT"
        const val FRAGMENT_A = "FRAGMENT_A"
        const val FRAGMENT_B = "FRAGMENT_B"
    }

    private fun replaceFragment(
        container: Int,
        fragment: Fragment,
        tag: String? = null,
        backStack: Boolean = false
    ) {
        supportFragmentManager.beginTransaction()
            .replace(container, fragment, tag)
            .apply {
                if (backStack) addToBackStack(null)
            }.commit()
    }
}

