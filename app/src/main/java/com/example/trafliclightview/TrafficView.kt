package com.example.trafliclightview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

private enum class TrafficStatus() {
    RED,
    YELLOW,
    GREEN;

    fun next() = when (this) {
        RED -> YELLOW
        YELLOW -> GREEN
        else -> RED
    }
}

private const val CIRCLE_OFFSET = 20F

class TrafficView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = 0F
    private var circleHeight = 0F
    private var status = TrafficStatus.RED

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55F
    }

    init {
        isClickable = true
    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true
        status = status.next()
        invalidate()
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(min(w, h), min(w, h) * 3, oldw, oldh)
        circleHeight = ((min(w, h) * 3 / 3).toFloat())
        radius = min(
            (w - (CIRCLE_OFFSET * 2)) / 2,
            (circleHeight - (CIRCLE_OFFSET * 2)) / 2
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = if (status == TrafficStatus.RED)
            ContextCompat.getColor(context, R.color.red)
        else
            ContextCompat.getColor(context, R.color.off_red)
        canvas.drawCircle(width / 2F, circleHeight / 2F, radius, paint)

        paint.color = if (status == TrafficStatus.YELLOW)
            ContextCompat.getColor(context, R.color.yellow)
        else
            ContextCompat.getColor(context, R.color.off_yellow)

        canvas.drawCircle(width / 2F, (circleHeight / 2F) + circleHeight, radius, paint)

        paint.color = if (status == TrafficStatus.GREEN)
            ContextCompat.getColor(context, R.color.green)
        else
            ContextCompat.getColor(context, R.color.off_green)

        canvas.drawCircle(width / 2F, (circleHeight / 2F) + circleHeight * 2, radius, paint)

    }
}