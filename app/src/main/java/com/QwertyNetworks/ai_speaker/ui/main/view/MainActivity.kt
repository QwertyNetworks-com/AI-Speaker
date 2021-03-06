package com.QwertyNetworks.ai_speaker.ui.main.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.QwertyNetworks.ai_speaker.R
import com.QwertyNetworks.ai_speaker.databinding.MainActivityBinding
import com.QwertyNetworks.ai_speaker.db.preferences.PreferencesOther
import com.QwertyNetworks.ai_speaker.db.socket.SocketHandler
import com.QwertyNetworks.ai_speaker.ui.constance.Constance
import com.QwertyNetworks.ai_speaker.ui.main.MyStartActivity
import com.QwertyNetworks.ai_speaker.ui.main.fragments.MainFragment

class MyClass{
        companion object{
            @SuppressLint("StaticFieldLeak") var activity: Activity? = null
        }
    }

    val preferencesOther = PreferencesOther()

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MyClass.activity = this@MainActivity

        setSaveIsUserSystem(true)

        val state = getGetUserID()

        if(state == "") {
            val intent = Intent(this, ShowNextActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, MainFragment.newInstance(), "fragment_1")
                    .commit()
                println("this saving to shared: $state")
                var socketHandler = SocketHandler()
            }
        }
        initial()
    }

    fun initial() {
        val socket4 = SocketHandler()
        socket4.getSockets(applicationContext)
        socket4.setCountMessage(0)
    }

    override fun onStart() {
        super.onStart()
        setSaveIsUserSystem(true)
    }

    override fun onStop() {
        super.onStop()
        setSaveIsUserSystem(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        setSaveIsUserSystem(false)
    }

    fun setSaveIsUserSystem(isBool: Boolean) {
        preferencesOther.setToUserSystem(Constance.IS_USER_SYSTEM, isBool,Constance.USER_SYSTEM_IS, applicationContext)
    }

    fun getGetUserID(): String {
        val pref = getSharedPreferences(Constance.USER_INFORMATION, Context.MODE_PRIVATE)
        return pref?.getString(Constance.USER_ID_KEY, "").toString()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.confirmation)
            setMessage(R.string.isExit)

            setNegativeButton(R.string.no) { _, _ -> }

            setPositiveButton(R.string.yes) { _, _ ->
                super.onBackPressed()
                val intent = Intent(this@MainActivity, MyStartActivity::class.java)
                startActivity(intent)
                preferencesOther.setToSharedString(Constance.USER_ID_KEY,"",Constance.USER_INFORMATION,this@MainActivity)
            }
            setCancelable(true)
        }.create().show()
    }
}