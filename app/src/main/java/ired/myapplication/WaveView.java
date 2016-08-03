package ired.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by kevin on 16/8/3.
 */

public class WaveView extends View {

    private int w;
    private int h;
    private int waveCount;
    private int centerY;
    private int waveLength = 1000;
    private int offset ;

    private Paint paint = new Paint();
    private Path  path ;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paint.setAntiAlias(true);
        paint.setDither(true);

        path = new Path();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,waveLength);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                offset = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        waveCount = w/waveLength +2;
        centerY = h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.reset();
        path.moveTo(-waveLength,centerY);

        for(int i = 0; i < waveCount;i++){
            path.quadTo(-3*waveLength/4+offset+waveLength*i,centerY+100,-waveLength/2+offset+waveLength*i,centerY);
            path.quadTo(-waveLength/4+offset+waveLength*i,centerY-100,waveLength*i+offset,centerY);
        }

        path.lineTo(w,h);
        path.lineTo(0,h);
        path.close();

        canvas.drawPath(path,paint);


    }
}
