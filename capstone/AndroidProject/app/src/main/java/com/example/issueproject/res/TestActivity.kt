package com.example.issueproject.res

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.issueproject.R
import com.google.firebase.messaging.FirebaseMessaging

class testActivity : AppCompatActivity() {
    companion object {
        const val TAG = "testActivity"
    }

    private val tvResult: TextView by lazy {
        findViewById(R.id.tv_result)
    }

    private val tvToken: TextView by lazy {
        findViewById(R.id.tv_token)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_test)
        setContentView(R.layout.activity_test)
        initFirebase()
        updateResult()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        updateResult(true)
    }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "INIT Success")
                tvToken.text = task.result
            }
        }
    }

    private fun updateResult(isNewIntent: Boolean = false) {
        //true -> notification 으로 갱신된 것
        //false -> 아이콘 클릭으로 앱이 실행된 것
        Log.d(TAG, "result Success")
        tvResult.text = (intent.getStringExtra("notificationType") ?: "앱 런처") + if (isNewIntent) {
            "(으)로 갱신했습니다."
        } else {
            "(으)로 실행했습니다."
        }
    }

}