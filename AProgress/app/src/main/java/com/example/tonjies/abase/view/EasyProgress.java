package com.example.tonjies.abase.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.tonjies.abase.app.App;
import com.example.tonjies.abase.util.ADensity;
import com.example.tonjies.abase.util.L;

import java.util.ArrayList;
import java.util.List;

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

    //进度条的最大宽度
    private float maxProgress;

    //进度条当前的宽度
    private float currentProgress;

    //当前View的宽度
    private int width;

    //当前View的高度
    private int height;

    //距离左边的内边距
    private int paddingLeft;

    //距离右边的内边距
    private int paddingRight;

    //文本画笔
    private Paint mTextPaint;

    //文本的大小
    private float mTextSize = ADensity.dip2px(14);

    /**
     * 评价文字集合,如体验很好，体验一般，体验不错，体验很好4个等级
     * 之所以使用集合，不使用String数组是想让它保持可扩展性，我们后面课题提供一个接口，让用户自己决定要设置多少个评价等级
     */
    private List<String> evaluates = new ArrayList<>();

    //文本的宽度
    private int textWidth;

    //文本的高度
    private int textHeight;

    //文本的透明度
    private int textAlpha;

    //文本缩放后的最小的大小
    private float textSizeRadio = 0.8f;

    //一段区间的大小
    private float distance;

    //一半区间的大小
    private float half = distance / 2;


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
        bgPaint.setStrokeCap(Paint.Cap.ROUND);//线冒的头是圆的
        bgPaint.setStrokeWidth(ADensity.dip2px(3));//大小为3dp转px

        //设置进度画笔
        progressPaint = new Paint();
        progressPaint.setColor(Color.parseColor("#0DE6C2"));//绿色
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);//填充且描边
        progressPaint.setAntiAlias(true);//抗锯齿
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//线冒的头圆原的
        progressPaint.setStrokeWidth(ADensity.dip2px(3));//大小为3dp转px

        //圆点指示器
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);//设置抗锯齿
        circlePaint.setColor(Color.parseColor("#fafafa"));//颜色
        circlePaint.setShadowLayer(ADensity.dip2px(2), 0, 0, Color.parseColor("#38000000"));//外阴影颜色
        circlePaint.setStyle(Paint.Style.FILL);//填充

        //初始化文本画笔
        mTextPaint=new Paint();
    }

    //重新计算控件的宽，高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int minHeight = mCircleRadius * 2 + (ADensity.dip2px(2) * 2);
        int height = resolveSize(minHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    //返回高度值,作用和resolveSize方法一样
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);//获取高度类型
        int size = MeasureSpec.getSize(heightMeasureSpec);//获取高度数值
        //制定的最小高度标准
        int minHeight = mCircleRadius * 2 + (ADensity.dip2px(2) * 2);
        //如果用户设定了指定大小
        if (mode == MeasureSpec.EXACTLY) {
            /**
             * 虽然用户已经指定了大小，但是万一指定的大小小于圆点指示器的高度，
             * 还是会出现显示不全的情况，所以还要进行判断
             */
            L.d("EXACTLY");
            if (size < minHeight) {
                result = minHeight;
            } else {
                result = size;
            }
        }
        //如果用户没有设定明确的值
        else {
            //设定高度为圆点指示器的直径
            result = minHeight;
        }
        return result;
    }

    //初始化几个距离参数
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();//view的宽度
        height = getHeight();//view的高度

        //让左边距至少为半个圆点指示器的距离
        paddingLeft = getPaddingLeft();//距离左边的距离
        if (getPaddingLeft() < mCircleRadius) {
            L.d("onLayout");
            paddingLeft = mCircleRadius;
        }
        //让右边距至少为半个圆点指示器的距离
        paddingRight = getPaddingRight();//距离右边的距离
        if (getPaddingRight() < mCircleRadius) {
            paddingRight = mCircleRadius;
        }

        //让左边距至少为

        //如果当前进度小于左边距
        setCurrentProgress();
        //最大进度长度等于View的宽度-(左边的内边距+右边的内边距)
        maxProgress = width - paddingLeft - paddingRight;
    }

    //绘制控件
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        L.d("onDraw");
        //绘制背景线段
        L.d("paddLeft:" + paddingLeft + " paddRight:" + paddingRight);
        //从（左边距，View高度的一半）开始，到（View宽度-右边距，View高度的一半）还将绘制灰色背景线段
        canvas.drawLine(paddingLeft, height / 2, width - paddingRight, height / 2, bgPaint);
        //绘制实际进度线段
        //从（左边距，View高度的一半）开始，到（现在的触摸到的进度宽度，View高度的一半）还将绘制灰色背景线段
        canvas.drawLine(paddingLeft, height / 2, currentProgress, height / 2, progressPaint);
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
                setMotionProgress(event);
                return true;
            //移动
            case MotionEvent.ACTION_MOVE:
                //获取当前触摸点，赋值给当前进度
                setMotionProgress(event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    //设置进度值
    private void setMotionProgress(MotionEvent event) {
        //获取当前触摸点，赋值给当前进度
        currentProgress = (int) event.getX();
        //如果当前进度小于左边距
        setCurrentProgress();
        //看数学公式就可以了,实际百分比进度数值
        float result = ((currentProgress - paddingLeft) * 100) / maxProgress;
        //进行空值判断
        if (onProgressListener != null) {
            onProgressListener.onSelect((int) result);
        }
        invalidate();
    }


    //设置当前进度条进度,从1到100
    public void setProgress(int progress) {
        if (progress > 100 || progress < 0) {
            Toast.makeText(App.getContext(), "输入的进度值不符合规范", Toast.LENGTH_SHORT).show();
        }
        setCurrentProgress();
        //设置当前进度的宽度
        currentProgress = ((progress * maxProgress) / 100) + paddingLeft;
        onProgressListener.onSelect(progress);
        invalidate();
    }

    private void setCurrentProgress() {
        if (currentProgress < paddingLeft) {
            currentProgress = paddingLeft;
        }
        //如果当前进度大于宽度-右边距
        else if (currentProgress > width - paddingRight) {
            currentProgress = width - paddingRight;
        }
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
