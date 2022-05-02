package com.andrydev.calltimer.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.andrydev.calltimer.Preferences
import com.andrydev.calltimer.R
import com.andrydev.calltimer.view.onboarding.OnBoardingData
import com.andrydev.calltimer.view.onboarding.OnBoardingViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class OnBoardingIntroductionScreen : AppCompatActivity() {
    lateinit var preferences: Preferences
    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter?=null
    var tabLayout: TabLayout?=null
    var onBoardingViewPager: ViewPager? = null
    var next: TextView?=null
    var position=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences= Preferences(this)
        if (preferences.getInitIntroductionScreen()){
            val i= Intent(this,MainActivity::class.java)
            startActivity(i)
        }
        setContentView(R.layout.activity_on_boarding_introduction_screen)
        supportActionBar?.hide()
        tabLayout=findViewById(R.id.tab_indicator)
        next=findViewById(R.id.textsiguiente)
        val onBoardingData: MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("CallTimer","App diseñada para mantener el tono " +
                "de tus llamadas en silencio o vibración en el período de tiempo que lo decidas.",R.raw.waiting))
        onBoardingData.add(OnBoardingData("Trabajo","Podrás asistir a reuniones y " +
                "trabajar con eficiencia sin molestas interrupciones.",R.raw.teamwork))
        onBoardingData.add(OnBoardingData("Escuela","CallTimer te permitirá concentrarte en tus clases sin que el " +
                "tono de tus llamadas afecte ni a ti, ni a tus compañeros.",R.raw.teacher))
        onBoardingData.add(OnBoardingData("Familia y amigos","No te preocupes. No dejarás de recibir llamadas en ningún momento.",R.raw.carousel))

        setOnBoardingViewPagerAdapter(onBoardingData)
        position=onBoardingViewPager!!.currentItem
        next?.setOnClickListener {
            if (position<onBoardingData.size){
                position++
                onBoardingViewPager!!.currentItem=position
            }
            if (position==onBoardingData.size){
                preferences.saveInitIntroductionScreen(true)
                val i= Intent(this,MainActivity::class.java)
                startActivity(i)
            }
        }
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                position=tab!!.position
                if (tab.position==onBoardingData.size-1){
                    next!!.text="Comencemos"
                }
                else{
                    next!!.text="Siguiente"
                }
            }
        })

    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager=findViewById(R.id.screenPager)
        onBoardingViewPagerAdapter= OnBoardingViewPagerAdapter(this,onBoardingData)
        onBoardingViewPager!!.adapter=onBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }
}