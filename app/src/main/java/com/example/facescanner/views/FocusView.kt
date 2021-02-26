package com.example.facescanner.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class FocusView : View {
    private var mTransparentPaint: Paint? = null
    private var mSemiBlackPaint: Paint? = null
    private val mPath: Path = Path()

    private var w = 0f

    private var x0 = 0f

    private var y0 = 0f

    constructor(context: Context?) : super(context) {
        initPaints()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaints()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initPaints()
    }

    private fun initPaints() {
        mTransparentPaint = Paint()
        mTransparentPaint!!.setColor(Color.TRANSPARENT)
        mTransparentPaint!!.setStrokeWidth(10F)
        mSemiBlackPaint = Paint()
        mSemiBlackPaint!!.setColor(Color.TRANSPARENT)
        mSemiBlackPaint!!.setStrokeWidth(10F)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val x = this.width
        val y = this.height
        val paint = Paint()
        paint.color = -0x56000000
        w = (Math.min(x, y) * 0.7).toFloat()
        x0 = (x - w) / 2
        y0 = (y - w) / 2
        val x1: Float = x0 + w
        val y1: Float = y0
        val y2: Float = y1 + w
        val x3: Float = x0
        val y3: Float = y0 + w

        //left

        //left
        canvas.drawRect(0f, y0, x3, y3, paint)
        //top
        //top
        canvas.drawRect(0f, 0f, x.toFloat(), y0, paint)
        //right
        //right
        canvas.drawRect(x1, y1, x.toFloat(), y2, paint)
        //bottom
        //bottom
        canvas.drawRect(0f, y3, x.toFloat(), y.toFloat(), paint)

        val paintLine = Paint()
        paintLine.color = -0x1
        canvas.drawLines(
            floatArrayOf(
                x0, y0, x1, y1,
                x1, y1, x1, y2,
                x1, y2, x3, y3,
                x3, y3, x0, y0
            ), paintLine
        )
    }
}