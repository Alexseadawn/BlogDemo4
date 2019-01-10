package com.example.tonjies.abase.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.tonjies.abase.app.App;
import com.example.tonjies.abase.util.ADensity;
import com.example.tonjies.abase.util.L;

/**
 * Created by 舍长 on 2019/1/10
 * describe:一个简单的进度条
 */
public class EasyProgress extends View {

    //灰色背景线段的画笔
    private Paint bgPaint;

    //实际进度绿色线段的画笔
    private Paint progressPaint;

    //圆点指示器的画笔
    private Paint circlePaint;

    //圆点指示器的半径
    private int mCircleRadius = ADensity.dip2px(12);

    //进度条的最大进度值
    private int maxProgress;

    //进度条当前的进度值
    private int currentProgress;

    //当前View的宽度
    private int width;

    //当前View的高度
    private int height;

    //距离左边的内边距
    private int paddLeft;

    //距离右边的内边距
    private int paddRight;


    public EasyProgress(Context context) {
        super(context);
    }

    public EasyProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();//初始化画笔
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //进度条背景画笔
        bgPaint = new Paint();
        bgPaint.setColor(Color.parseColor("#F0F0F0"));//灰色
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);//填充且描边
        bgPaint.setAntiAlias(true);//抗锯齿
        bgPaint.setStrokeCap(Paint.Cap.ROUND);//线冒的头是原的
        bgPaint.setStrokeWidth(ADensity.dip2px(3));//大小为3dp转px

        //设置进度画笔
        progressPaint = new Paint();
        progressPaint.setColor(Color.parseColor("#0DE6C2"));//绿色
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);//填充且描边
        progressPaint.setAntiAlias(true);//抗锯齿
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//线冒的头是原的
        progressPaint.setStrokeWidth(ADensity.dip2px(3));//大小为3dp转px

        //圆点指示器
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);//设置抗锯齿
        circlePaint.setColor(Color.parseColor("#fafafa"));//颜色
        circlePaint.setShadowLayer(ADensity.dip2px(2), 0, 0, Color.parseColor("#38000000"));//外阴影颜色
        circlePaint.setStyle(Paint.Style.FILL);//填充
    }

    //初始化几个距离参数
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();//view的宽度
        height = getHeight();//view的高度

        //让左边距至少为半个圆点指示器的距离
        paddLeft = getPaddingLeft();//距离左边的距离
        if (getPaddingLeft() < mCircleRadius) {
            paddLeft = mCircleRadius;
        }
        ;

        //让右边距至少为半个圆点指示器的距离
        paddRight = getPaddingRight();//距离右边的距离
        if (getPaddingRight() < mCircleRadius) {
            paddRight = mCircleRadius;
        }
        //最大进度长度等于View的宽度-(左边的内边距+右边的内边距)
        maxProgress = width - getPaddingLeft() - getPaddingRight();
    }

    //绘制控件
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景线段
        canvas.drawLine(paddLeft, height / 2, width - paddRight, height / 2, bgPaint);
        //绘制实际进度线段
        canvas.drawLine(paddLeft, height / 2, currentProgress, height / 2, progressPaint);
        //要支持阴影下过必须关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);//发光效果不支持硬件加速
        //绘制圆点
        canvas.drawCircle(currentProgress, getHeight() / 2, mCircleRadius, circlePaint);
    }

    //触摸
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //按住
            case MotionEvent.ACTION_DOWN:
                //设置进度值
                setProgress(event);
                return true;
            //移动
            case MotionEvent.ACTION_MOVE:
                //获取当前触摸点，赋值给当前进度
                setProgress(event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    //设置进度值
    private void setProgress(MotionEvent event) {
        //获取当前触摸点，赋值给当前进度
        currentProgress = (int) event.getX();
        //如果当前进度小于左边距
        if (currentProgress < paddLeft) {
            currentProgress = paddLeft;
        }
        //如果当前进度大于宽度-右边距
        else if (currentProgress > width - paddRight) {
            currentProgress = width - paddRight;
        }
        //看数学公式就可以了,实际百分比进度数值
        int result = ((currentProgress - paddLeft) * 100) / maxProgress;
        onProgressListener.onSelect(result);
        invalidate();
    }

    //当前选中进度的回调
    private OnProgressListener onProgressListener;

    public interface OnProgressListener {
        void onSelect(int progress);
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }
}
