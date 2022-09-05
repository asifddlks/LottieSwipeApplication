package com.asifddlks.lottieswipeapplication

import android.animation.ValueAnimator
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import com.asifddlks.coordinatorlayoutapplication.OnSwipeTouchListener
import com.asifddlks.lottieswipeapplication.databinding.ActivityPlayGroundBinding
import kotlin.math.abs

class PlayGroundActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayGroundBinding
/*
    var x : Double? = 0.0
    var y : Double? = 0.0*/

    var buttonPoint: PointF = PointF(0f,0f)
    var view1Point: PointF = PointF(0f,0f)
    var view2Point: PointF = PointF(0f,0f)
    var view3Point: PointF = PointF(0f,0f)

    val TAG = "PlayGroundActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayGroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //moveViewWithTouch()

        Log.d(TAG,"frame: ${binding.lottieAnimationView.frame}")
        Log.d(TAG,"maxFrame: ${binding.lottieAnimationView.maxFrame}")
        Log.d(TAG,"minFrame: ${binding.lottieAnimationView.minFrame}")


        swipeGesture()

    }

    private fun swipeGesture() {
        binding.viewLayer2.setOnTouchListener(object: OnSwipeTouchListener() {
            override fun onSwipeLeft() {
                //onBackPressed()
            }
            override fun onSwipeRight() {
                //onBackPressed()
            }

            override fun onSwipeTop() {
                //super.onSwipeTop()
                Log.d(TAG,"onSwipeTop")
//                binding.viewLayer2.animate()
//                    //.x(motionEvent.rawX + view1Point.x)
//                    .y(binding.viewLayer2.height.toFloat())
//                    .setDuration(100)
//                    .start()
            }

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                Log.d(TAG,"onTouch: motionEvent.rawY: ${event.rawY}")
                Log.d(TAG,"onTouch: motionEvent.y: ${event.y}")
                Log.d(TAG,"onTouch: view.y: ${v.y}")
                Log.d(TAG,"onTouch: view.height: ${v.height}")
                Log.d(TAG,"onTouch: abs(v.y/v.height): ${abs(v.y/v.height)}")

                when (event.action){
                    MotionEvent.ACTION_DOWN -> {
                        view2Point.x = v.x - event.rawX
                        view2Point.y = v.y - event.rawY
                        v.y = 0f
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        v.animate()
                            //.x(motionEvent.rawX + view1Point.x)
                            .y(0f)
                            .setDuration(1000)
                            .start()
                        binding.depositBottomLottieAnimationView.animate().alpha(1f).setDuration(1000).start()
                    }

                    MotionEvent.ACTION_MOVE -> {

                        if(v.y <= 0){
                            v.animate()
                                //.x(motionEvent.rawX + view1Point.x)
                                .y(event.rawY + view2Point.y)
                                .setDuration(0)
                                .start()

                            //binding.depositBottomLottieAnimationView.alpha = max((1 - abs(v.y/v.height) * 5),1f)
                            val value = (abs(v.y/v.height) * 8)
                            binding.depositBottomLottieAnimationView.alpha = (1 - value)
                            Log.d(TAG,"depositBottomLottieAnimationView.alpha: ${value}")
                            animateLottieWithProgress(binding.depositTornPartLottieAnimationView,value)
                        }
                        // animate the image on x axis line only according to your finger movements

                        true
                    }
                    else -> {
                        true
                    }
                }
                return super.onTouch(v, event)

            }
        })
    }

    private fun moveViewWithTouch() {

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // Custom animation speed or duration.

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        /*binding.button.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    buttonPoint.x = view.x - motionEvent.rawX
                    buttonPoint.y = view.y - motionEvent.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {

                    //binding.lottieAnimationView.frame = (motionEvent.rawX/100).toInt()

                    // Custom animation speed or duration.
                    val animator = ValueAnimator.ofFloat(0f, 1f)
                    animator.addUpdateListener {
                        binding.lottieAnimationView.progress = (motionEvent.rawX/100)
                    }
                    animator.start()
                    // animate the image on x axis line only according to your finger movements
                    binding.button.text = motionEvent.rawX.toString()
                    binding.button.animate()
                        .x(motionEvent.rawX + buttonPoint.x)
                        //.y(motionEvent.rawY + buttonPoint.y)
                        .setDuration(0)
                        .start()
                    true
                }
                else -> {
                    true
                }
            }
        }*/


    }

    fun animateLottieWithProgress(lottieView: com.airbnb.lottie.LottieAnimationView,progress:Float){
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener {
            Log.d(TAG,"progress: $progress")
            Log.d(TAG,"(progress/10f).toFloat(): ${(progress/10f)}")
            //lottieView.progress = (progress/100f)
            lottieView.progress = (progress/10f) * 2.2f
        }
        animator.start()
    }
}