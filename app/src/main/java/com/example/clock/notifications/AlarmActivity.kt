package com.example.clock.notifications

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.example.clock.R
import com.example.clock.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        showOnLockScreenAndTurnScreenOn()
        setContentView(binding.root)

        val alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation)

        val icTurnOff = AnimationUtils.loadAnimation(this, R.anim.shake_animation)
        binding.icTurnOff.startAnimation(icTurnOff)

        val putAsideAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_put_aside)
        val turnOffAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_turn_off)

        binding.bgAnimationPutAside.startAnimation(putAsideAnimation)

        putAsideAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.bgAnimationPutAside.visibility = View.VISIBLE
                binding.bgAnimationTurnOff.visibility = View.INVISIBLE
                Log.d("TTTTT", "PUT ASIDE START")
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.bgAnimationPutAside.startAnimation(alphaAnimation)
                Log.d("TTTTT", "PUT ASIDE END")
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })


        turnOffAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.bgAnimationTurnOff.visibility = View.VISIBLE
                binding.bgAnimationPutAside.visibility = View.INVISIBLE
                Log.d("TTTTT", "TURN OFF START")
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.bgAnimationTurnOff.startAnimation(alphaAnimation)
                Log.d("TTTTT", "TURN OFF END")
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })

        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                if (binding.bgAnimationPutAside.isVisible) {
                    binding.bgAnimationTurnOff.startAnimation(turnOffAnimation)
                    Log.d("TTTTT", "IF")

                } else {
                    binding.bgAnimationPutAside.startAnimation(putAsideAnimation)
                    Log.d("TTTTT", "ELSE")
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
    }

    private fun showOnLockScreenAndTurnScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }

}