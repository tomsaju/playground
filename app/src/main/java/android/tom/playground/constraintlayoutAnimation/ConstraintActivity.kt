package android.tom.playground.constraintlayoutAnimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.tom.playground.R
import android.tom.playground.R.id.backgroundImage
import android.tom.playground.R.id.constraint
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView


class ConstraintActivity : AppCompatActivity() {
    private var show = false
    private lateinit var constraint: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.circuit)
        constraint = findViewById(R.id.constraint) as ConstraintLayout
        val backgroundImage = findViewById(R.id.backgroundImage) as ImageView

        backgroundImage.setOnClickListener {
            if(show)
                hideComponents() // if the animation is shown, we hide back the views
            else
                showComponents() // if the animation is NOT shown, we animate the views
        }
    }

    private fun showComponents(){
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.circuit_detail)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }

    private fun hideComponents(){
        show = false

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.circuit)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }
}
