package com.example.fe_insta

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_btn.setOnClickListener {
            var text1 = editText3.text.toString()
            var text2 = editText2.text.toString()

            var dialog = AlertDialog.Builder(this@MainActivity)
            dialog.setTitle("알람")
            dialog.setMessage("id : "+ text1)
            dialog.show()
        }

    }
}