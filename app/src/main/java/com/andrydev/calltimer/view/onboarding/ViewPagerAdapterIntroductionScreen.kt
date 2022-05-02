package com.andrydev.calltimer.view.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.airbnb.lottie.LottieAnimationView
import com.andrydev.calltimer.R


class OnBoardingViewPagerAdapter(private var context: Context, private var onBoardingDataList:List<OnBoardingData>):
    PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view= LayoutInflater.from(context).inflate(R.layout.introduction_screen,null)

        val image: LottieAnimationView = view.findViewById(R.id.lottieAnimationView)
        val title: TextView = view.findViewById(R.id.title)
        val desc: TextView = view.findViewById(R.id.desc)

        image.setAnimation(onBoardingDataList[position].imageUrl)
        title.text=onBoardingDataList[position].tittle
        desc.text=onBoardingDataList[position].desc

        container.addView(view)
        return view
    }
}