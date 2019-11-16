package com.example.mysharedpreferenceusers

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val MAX_COUNT = 5

    lateinit var mySharedPred: SharedPreferences
    lateinit var myEditorPref: SharedPreferences.Editor
    lateinit var myOrientation: GradientDrawable.Orientation

    var name=""
    var phone =""

    private var userCountKey = "user_in"
    private var userKeyPostfix = "user_"

    private  var user_In = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mySharedPred = this.getSharedPreferences("com.example.mysharedpreferenceusers", Context.MODE_PRIVATE)
        myEditorPref = mySharedPred.edit()

        user_In = mySharedPred.getInt(userCountKey, 0)

        addUserButton.setOnClickListener { _->
            name = name_editText.text.toString()
            phone = phone_editText.text.toString()


            if (user_In == MAX_COUNT){
                Toast.makeText(this.getApplicationContext(), "User maximum reached" , Toast.LENGTH_LONG).show()
                clearText()
            }else{
                var newUser = "name : " + name + "  phone : "+phone
                myEditorPref.putString(userKeyPostfix + user_In, newUser)
                myEditorPref.commit()
                myEditorPref.putInt(userCountKey, (user_In + 1))
                myEditorPref.commit()
                        clearText()
                        user_In++

                        displayExistingUsers()
            }

        }

    }

    override
    fun onResume(){
        super.onResume()
        displayExistingUsers()
    }

    fun clearText(){
        name_editText.text.clear()
        phone_editText.text.clear()

    }

    private fun displayExistingUsers() {

        if(user_In > 0){
            var myUsers = StringBuffer()

            for(i in 0 until user_In){
                var user = mySharedPred.getString(userKeyPostfix + i, "FAILED")
                myUsers.append(user + "\n")
            }
            user_textView.text = myUsers.toString()
        } else {
            user_textView.text = "No users"
        }

    }

/*
    @Override
    public fun onConfigurationChange(newConfig: Configuration){
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "Landscape", Toast.LENGTH_SHORT).show()
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "Potrait", Toast.LENGTH_SHORT).show()
        }
    }
*/


}
