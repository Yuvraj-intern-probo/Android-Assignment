package com.probo.androidassignment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var email = findViewById<EditText>(R.id.email)
        var pass = findViewById<EditText>(R.id.password)
        var conPass = findViewById<EditText>(R.id.repassword)
        val emailPattern = Regex("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$")
        val passPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$")

        /*Validation*/
        fun valPass(): Boolean {
            if(passPattern.containsMatchIn((pass.text.toString()))) {
                if(conPass.text.toString() !== pass.text.toString())
                {
                    Toast.makeText(this,"Password and Confirm Password doesn't match", Toast.LENGTH_LONG).show()
                    return false
                }
                return true
            }
            Toast.makeText(this,"Password does not match criteria", Toast.LENGTH_LONG).show()
            return false
        }
        fun valEmail(): Boolean {
            if(emailPattern.containsMatchIn(email.text.toString()))
                return valPass()
            Toast.makeText(this,"Email Invalid", Toast.LENGTH_LONG).show()
            return false
        }


        /*Loading Shared Preferences*/
        fun loadData()
        {
            val sharedPreferences = getSharedPreferences("oldUser", Context.MODE_PRIVATE)
            val savedEmail = sharedPreferences.getString("email_key",null)
            val savedPass = sharedPreferences.getString("pass_key",null)
            email.setText(savedEmail)
            pass.setText(savedPass)
            conPass.setText(savedPass)

        }

        /*Function to Save Data to Shared Preferences*/
        fun saveData(){
            val sharedPreferences = getSharedPreferences("oldUser", Context.MODE_PRIVATE)
            sharedPreferences.edit().apply{
                putString("email_key",email.text.toString())
                putString("pass_key",pass.text.toString())
            }.apply()
        }

        /*function called on click*/
        fun signUp() {
            if(valEmail())
            {
                saveData()
                Toast.makeText(this,"Submit Successful",Toast.LENGTH_SHORT).show()
            }
        }


        ////////////////////////////////////

        loadData()
        email.addTextChangedListener(textWatcher)
        pass.addTextChangedListener(textWatcher)

        val submit = findViewById<Button>(R.id.btn_submit)
        submit.setOnClickListener {
            signUp()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }


        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            var email = findViewById<EditText>(R.id.email).text.toString().trim()
            var pass = findViewById<EditText>(R.id.password).text.toString().trim()
            val submit = findViewById<Button>(R.id.btn_submit)
            submit.isClickable = (email.isNotEmpty() && pass.isNotEmpty())
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }






}