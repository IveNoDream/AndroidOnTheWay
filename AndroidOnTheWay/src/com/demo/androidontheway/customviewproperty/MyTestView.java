package com.demo.androidontheway.customviewproperty;

import com.demo.androidontheway.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyTestView extends View {
	
	private String text = null;
	private int textAttr = -1;

	private static final String TAG = MyTestView.class.getSimpleName();
	public MyTestView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		TypedArray ta = context.obtainStyledAttributes(R.styleable.test);

        text = ta.getString(R.styleable.test_text);
        textAttr = ta.getInteger(R.styleable.test_textAttr, -1);

        Log.e(TAG, "text0 = " + text + " , textAttr = " + textAttr);

        ta.recycle();
	}
	
    public MyTestView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray taArray = context.obtainStyledAttributes(attrs, R.styleable.test);

        text = taArray.getString(R.styleable.test_text);
        textAttr = taArray.getInteger(R.styleable.test_textAttr, -1);

        Log.e(TAG, "text1 = " + text + " , textAttr = " + textAttr);

        taArray.recycle();
    }
    
    public MyTestView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray taArray = context.obtainStyledAttributes(attrs, R.styleable.test);

        text = taArray.getString(R.styleable.test_text);
        textAttr = taArray.getInteger(R.styleable.test_textAttr, -1);

        Log.e(TAG, "text2 = " + text + " , textAttr = " + textAttr);

        taArray.recycle();
    }

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setTextSize(30);
		canvas.drawText(text, 100, 100, paint);
	}
}
