package com.example.walkingbuddiesdogedition.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.walkingbuddiesdogedition.R
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var al = ArrayList<String>()
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        al.add("dog1")
        al.add("dog2")
        al.add("dog3")
        al.add("dog4")

        arrayAdapter = ArrayAdapter(this,
            R.layout.item,
            R.id.helloText, al )

        //set the listener and the adapter
        frame.adapter = arrayAdapter
        frame.setFlingListener(object: SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                Log.d("LIST", "removed object!")
                al.removeAt(0)
                arrayAdapter?.notifyDataSetChanged()
            }

            override fun onLeftCardExit(p0: Any?) {
                Toast.makeText(this@MainActivity, "Nah!", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(p0: Any?) {
                Toast.makeText(this@MainActivity, "Yes!", Toast.LENGTH_SHORT).show()

            }

            override fun onAdapterAboutToEmpty(p0: Int) {
                al.add("Dog $i")
                arrayAdapter?.notifyDataSetChanged()
                Log.d("LIST", "notified")
                i++
            }

            override fun onScroll(p0: Float) {
            }
        })
    }
    companion object {
        fun newIntent(context: Context?) = Intent(context, MainActivity::class.java)
    }
}