package com.probo.androidassignment

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult

var btn_gallery: Button?= null
var img_view: ImageView?= null
var view_Details: TextView?= null


class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        title = ""
        img_view = findViewById(R.id.imageView)
        btn_gallery = findViewById(R.id.btn_gallery)
        view_Details = findViewById(R.id.txt_Details)

        var resultLauncher = registerForActivityResult(StartActivityForResult()) {
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data: Intent? = result.data
                img_view?.setImageURI(data?.data)
                view_Details?.text = "email : "+ email?.text.toString().trim()+"\n"+"pass : "+pass?.text.toString().trim()
            }
        }

        btn_gallery?.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            resultLauncher.launch(gallery)
        }
    }
}