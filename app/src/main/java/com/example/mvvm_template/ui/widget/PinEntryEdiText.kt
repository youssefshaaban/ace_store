package com.example.mvvm_template.ui.widget

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Editable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.AppCompatEditText
import java.lang.RuntimeException

class PinEntryEdiText :AppCompatEditText {
    val XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"

    private var mSpace = 24f //24 dp by default, space between the lines

    private var mCharSize = 0f
    private var mNumChars = 4f
    private var mLineSpacing = 8f //8dp by default, height of the text from our lines

    private var mMaxLength = 4

    private var mClickListener: OnClickListener? = null
    private var mLineStroke = 1f //1dp by default

    private var mLineStrokeSelected = 2f //2dp by default

    private var mLinesPaint: Paint? = null
    var mStates = arrayOf(
        intArrayOf(R.attr.state_selected),
        intArrayOf(R.attr.state_focused),
        intArrayOf(-R.attr.state_focused)
    )

    var mColors = intArrayOf(
        Color.GREEN,
        Color.BLACK,
        Color.GRAY
    )

    var mColorStates: ColorStateList = ColorStateList(mStates, mColors)

    constructor(context:Context) : super(context)
    constructor(context: Context,attributeSet : AttributeSet):super(context,attributeSet){
        init(context, attributeSet)
    }
    constructor(context: Context,attributeSet : AttributeSet,defStyleAttr:Int):super(context,attributeSet,defStyleAttr){
        init(context, attributeSet)
    }

    constructor(context: Context,attributeSet : AttributeSet,defStyleAttr:Int,defStyleRes:Int):super(context,attributeSet,defStyleAttr){
        init(context, attributeSet)
    }

    fun init(context: Context,attributeSet : AttributeSet){
        val multi = context.resources.displayMetrics.density
        mLineStroke *= multi
        mLineStrokeSelected *= multi
        mLinesPaint = Paint(paint)
        mLinesPaint?.strokeWidth = mLineStroke

        if (!isInEditMode) {
            val outValue = TypedValue()
            context.theme.resolveAttribute(
                R.attr.colorControlActivated,
                outValue, true
            )
            val colorActivated: Int = outValue.data
            mColors[0] = colorActivated
            context.theme.resolveAttribute(
                R.attr.colorPrimaryDark,
                outValue, true
            )
            val colorDark: Int = outValue.data
            mColors[1] = colorDark
            context.theme.resolveAttribute(
                R.attr.colorControlHighlight,
                outValue, true
            )
            val colorHighlight: Int = outValue.data
            mColors[2] = colorHighlight
        }
        setBackgroundResource(0);
        mSpace *= multi //convert to pixels for our density
        mLineSpacing *= multi //convert to pixels for our density
        mMaxLength = attributeSet.getAttributeIntValue(XML_NAMESPACE_ANDROID, "maxLength", 4)
        mNumChars = mMaxLength.toFloat()

        //Disable copy paste
        //Disable copy paste
        super.setCustomSelectionActionModeCallback(object :
            android.view.ActionMode.Callback {
            override fun onCreateActionMode(p0: android.view.ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(p0: android.view.ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(p0: android.view.ActionMode?, p1: MenuItem?): Boolean {
                return false
            }

            override fun onDestroyActionMode(p0: android.view.ActionMode?) {

            }



        })

        // When tapped, move cursor to end of text.
        // When tapped, move cursor to end of text.
        super.setOnClickListener { v ->
            setSelection(text!!.length)
            mClickListener?.onClick(v)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener=l
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: android.view.ActionMode.Callback?) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
    }

    override fun onDraw(canvas: Canvas?) {
        //super.onDraw(canvas)
        val availableWidth = width - paddingRight - paddingLeft
        if (mSpace < 0) {
            mCharSize = (availableWidth / (mNumChars * 2 - 1));
        } else {
            mCharSize = (availableWidth - (mSpace * (mNumChars - 1))) / mNumChars;
        }
        var startX = paddingLeft
        val bottom = height - paddingBottom

        //Text Width

        //Text Width
        val text: Editable? = text
        val textLength: Int = text!!.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(getText(), 0, textLength, textWidths)
        for (i in 0 until mNumChars.toInt()) {
            updateColorForLines(i == textLength)
            canvas!!.drawLine(
                startX.toFloat(), bottom.toFloat(), startX + mCharSize,
                bottom.toFloat(), mLinesPaint!!
            )
            if (getText()!!.length > i) {
                val middle = startX + mCharSize / 2
                canvas!!.drawText(
                    text!!, i, i + 1, middle - textWidths[0] / 2, bottom - mLineSpacing,
                    paint
                )
            }
            startX += if (mSpace < 0) {
                (mCharSize * 2).toInt()
            } else {
                (mCharSize + mSpace).toInt()
            }
        }
    }

    private fun getColorForState(vararg states: Int): Int {
        return mColorStates.getColorForState(states, Color.GRAY)
    }
    private fun updateColorForLines(next: Boolean) {
        if (isFocused) {
            mLinesPaint!!.strokeWidth = mLineStrokeSelected
            mLinesPaint!!.color = getColorForState(R.attr.state_focused)
            if (next) {
                mLinesPaint!!.color = getColorForState(R.attr.state_selected)
            }
        } else {
            mLinesPaint!!.strokeWidth = mLineStroke
            mLinesPaint!!.color = getColorForState(-R.attr.state_focused)
        }
    }
}