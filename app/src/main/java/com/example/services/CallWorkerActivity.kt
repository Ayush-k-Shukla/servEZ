package com.example.services

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.services.messages.ChatActivity
import com.example.services.models.User
import com.example.services.models.Worker
import com.example.services.shared.currentUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_call_worker.*
import kotlinx.android.synthetic.main.worker_tile.view.*

class CallWorkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_worker)
        val user = intent.getParcelableExtra<User>("UserKey")
        val worker = intent.getParcelableExtra<Worker>("WorkerKey")

        if(user?.profileImgURL=="NULL"||user?.profileImgURL==null){
        }else{
            Picasso.get().load(user?.profileImgURL).into(worker_profile_pic)
        }

        worker_phone_call.setOnClickListener{
           makeCall(user?.phone!!)
        }
        call_icon.setOnClickListener{
            makeCall(user?.phone!!)
        }
        updateUI()

        message_icon.setOnClickListener{
            val intent = Intent(this,ChatActivity::class.java)
            intent.putExtra("MY_ID", currentUser!!.uid)
            intent.putExtra("RCV_ID", user!!.uid)

            intent.putExtra("MY_CLASS", currentUser)
            intent.putExtra("RCV_CLASS", user)

            startActivity(intent)
        }
        // Write your code here

    }

    private fun updateUI(){
        val user = intent.getParcelableExtra<User>("UserKey")
        val worker = intent.getParcelableExtra<Worker>("WorkerKey")

        worker_name_call.text = user?.firstName+" "+user?.lastName
        worker_experience_call.text = worker?.experience
        worker_description_call.text = worker?.description
        worker_rating_call.text = worker?.rating.toString()+"%"
        worker_jobsdone_call.text = "Jobs Done: "+ worker?.jobDoneCount.toString()
        worker_phone_call.text = user?.phone
        worker_city_call.text = user?.city

        when(worker?.experience){
            "Beginner"->worker_experience_call.setTextColor(Color.parseColor("#697778"))
            "Specialist"->worker_experience_call.setTextColor(Color.parseColor("#09a0ab"))
            "Expert"->worker_experience_call.setTextColor(Color.parseColor("#076adb"))
        }

        if(worker?.rating in 0..20){
            worker_rating_call.setTextColor(Color.parseColor("#c70000"))
        } else if(worker?.rating in 21..49){
            worker_rating_call.setTextColor(Color.parseColor("#e37a1e"))
        }else if(worker?.rating in 50..60){
            worker_rating_call.setTextColor(Color.parseColor("#e3d31e"))
        }else if(worker?.rating in 61..80){
            worker_rating_call.setTextColor(Color.parseColor("#a1bd24"))
        }else{
            worker_rating_call.setTextColor(Color.parseColor("#47a326"))
        }
    }

    private  fun makeCall(mobileNumber:String){
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel: $mobileNumber")
        startActivity(intent)
    }


}