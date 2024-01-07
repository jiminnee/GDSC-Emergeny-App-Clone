package com.gdsc.chapter4

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.gdsc.chapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)

        // MainActivity에서 이 버튼을 클릭하면 InputActivity 실행
        binding.goInputActivityButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("intentMessage", "응급의료정보")
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        binding.phoneNumLayer.setOnClickListener{
            with(Intent(Intent.ACTION_VIEW)){
                val phoneNumber = binding.phoneNumValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataUiUpdate()
    }

    private fun getDataUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.nameValueTextView.text = getString(NAME, "지민")
            binding.birthdateValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.phoneNumValueTextView.text = getString(PHONE_NUM, "미정")
            val warning = getString(WARN, "")

            binding.warnTextView.isVisible = warning.isNullOrEmpty().not()
            binding.warnValueTextView.isVisible = warning.isNullOrEmpty().not()

            if(!warning.isNullOrEmpty()) {
                binding.warnValueTextView.text = warning
            }
        }

    }

    private fun deleteData(){
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()){
            clear()
            apply()
            getDataUiUpdate()

        }
        Toast.makeText(this, "잘 지워졌습니다.", Toast.LENGTH_SHORT).show()
    }
}