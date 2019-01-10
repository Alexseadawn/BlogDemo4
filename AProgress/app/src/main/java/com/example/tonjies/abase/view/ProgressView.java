package com.example.tonjies.abase.view;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.tonjies.abase.app.App;
import com.example.tonjies.abase.util.DensityUtil;
import com.example.tonjies.abase.util.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 舍长 on 2019/1/9
 * describe:自定义评价进度条
 */
public class ProgressView extends View {

    //进度条背景画笔
    private Paint mbgPaint;

    //设置进度画笔
    private Paint mProgressPaint;

    //圆形按钮画笔
    private Paint mCirclePaint;

    //文本画笔
    private Paint mTextPaint;

    //文本的大小
    private float mTextSize = DensityUtil.dip2px(App.getContext(), 12);

    //线条背景
    private Path path;

    //当前进度
    private Path desPath;

    //当前View的宽度
    private int mWidth;

    //文本的最大宽度
    private int maxTextWidth;

    //进度条的最大长度
    private float maxLength;

    //当前进度条的进度
    private float progress;

    //进度条评价文字集合
    private List<String> evaluates = new ArrayList<>();

    //每一小段的距离
    private float aveWidth;

    //当前选中的位置
    private int currentPosition = 0;

    //开始点，结束点
    float startX = 0;
    float endX = 0;

    //圆点的半径
    private int mCircleRadius = DensityUtil.dip2px(App.getContext(), 12);

    //文本高度
    private int textHeight;

    //当前进度
    private static float currentProgres = 0;

    private int mAlpha = 255;

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景线段
        canvas.drawPath(path, mbgPaint);
        //绘制进度条
        canvas.drawPath(desPath, mProgressPaint);
        //绘制圆
        canvas.drawCircle(progress, getHeight() / 2, mCircleRadius, mCirclePaint);
        //绘制文字
        //确保进度的区间
        for (int i = 0; i < evaluates.size(); i++) {
            if (progress >= (aveWidth * i + maxTextWidth / 2)//前一段距离
                    && progress <= (aveWidth * (i + 1) + maxTextWidth / 2)//后一段距离
                    ) {
                //
                if ((progress - (aveWidth * i + maxTextWidth / 2)) <= aveWidth / 2) {
                    float ratio = (progress -  (aveWidth * i + maxTextWidth / 2)) / (aveWidth / 2);
                    float dt = DensityUtil.dip2px(App.getContext(), 1) * ratio;
                    mAlpha = (int) (ratio * 255);
                    mTextPaint.setAlpha(255 - mAlpha);
                    mTextPaint.setTextSize(mTextSize - dt);
                    canvas.drawText(evaluates.get(i), progress, textHeight, mTextPaint);
                } else {
                    float ratio = (progress -  (aveWidth * i + maxTextWidth / 2) - aveWidth / 2) / (aveWidth / 2);
                    float dt = DensityUtil.dip2px(App.getContext(), 1) * ratio;
                    mAlpha = (int) (ratio * 255);
                    mTextPaint.setTextSize(mTextSize + dt - DensityUtil.dip2px(App.getContext(), 1));
                    mTextPaint.setAlpha(mAlpha);
                    canvas.drawText(evaluates.get(i + 1), progress, textHeight, mTextPaint);
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取当前View的宽
        mWidth = getWidth();

        //设置标准提示字体宽度的矩形
        Rect rect = getTextBound("体验很好");
        //获取标准提示字体的宽度
        maxTextWidth = rect.width();
        L.d("max:" + maxTextWidth);
        //进度条的最大长度
        maxLength = mWidth - maxTextWidth / 2 * 2;

        //文本高度
        textHeight = getHeight() / 2 + DensityUtil.dip2px(App.getContext(), 22) + rect.height() / 2;

//        L.d("maxLength:" + maxLength);
        //绘制背景线的path路径,path路径从规定文本标准的一半宽度开始绘制，高度为当前View高度的一半
        path.moveTo(maxTextWidth / 2, getHeight() / 2);
        path.lineTo(maxLength + maxTextWidth / 2, getHeight() / 2);

        //设置每一小段的长度
        //当集合没有元素时，每一小段的长度为0
        if (evaluates.size() == 0) {
            aveWidth = 0;
        } else if (evaluates.size() == 1) {
            aveWidth = maxLength;
        } else {
            aveWidth = maxLength / (evaluates.size() - 1);
        }
        //设置当前进度
        this.progress = maxTextWidth / 2 + aveWidth * currentPosition;

        //绘制当前进度条
        desPath.reset();
        desPath.moveTo(maxTextWidth / 2, getHeight() / 2);
        //获取当前进度条的进度
        desPath.lineTo(progress, getHeight() / 2);


    }

    //返回一个矩形对象
    private Rect getTextBound(String text) {
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            //按下
            case MotionEvent.ACTION_DOWN:
//                L.d("ACTION_DOWN");
                startX = event.getX();
                progress = startX;
                setProgress();
                return true;
            //移动
            case MotionEvent.ACTION_MOVE:
//                L.d("ACTION_MOVE");
                endX = event.getX();
                float dx = endX - startX;
                startX = endX;
                progress = progress + dx;
                //当触摸的范围在进度条范围前面
                setProgress();
                return true;
            //抬手
            case MotionEvent.ACTION_UP:
                //抬手的时候需要回弹
                for (int i = 0; i < evaluates.size(); i++) {
                    if (progress > (maxTextWidth / 2 + aveWidth * i - aveWidth / 2)
                            &&
                            progress <= (maxTextWidth / 2 + aveWidth * i + aveWidth / 2)) {
                        progress = maxTextWidth / 2 + aveWidth * i;
                        if (progress <= (maxTextWidth / 2)) {
                            progress = maxTextWidth / 2;
                        } else if (progress >= (maxLength + maxTextWidth / 2)) {
                            progress = maxLength + maxTextWidth / 2;
                        }
                    }
                }
                //抬手重置进度path
                desPath.reset();
                desPath.moveTo(maxTextWidth / 2, getHeight() / 2);
                desPath.lineTo(progress, getHeight() / 2);
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置进度
     */
    private void setProgress() {
        //当触摸的范围在进度条范围前面
        if (progress <= (maxTextWidth / 2)) {
            progress = maxTextWidth / 2;
        }
        //当触摸的范围在进度条范围的后面
        else if (progress >= (maxLength+maxTextWidth/2)) {
            progress = maxLength + maxTextWidth / 2;
        }
        invalidate();
        desPath.reset();
        desPath.moveTo(maxTextWidth / 2, getHeight() / 2);
        desPath.lineTo(progress, getHeight() / 2);

        //计算当前位置的百分数
        float temp;
        if (progress > (maxTextWidth / 2)) {
            temp = progress - maxTextWidth / 2;
        } else {
            temp = 0;
        }

        currentProgres = (temp * 100) / maxLength;
        L.d("当前的进度是:" + currentProgres);
    }

    /**
     * 初始化画笔
     */
    private void init() {
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);//发光效果不支持硬件加速

        //进度条背景画笔
        mbgPaint = new Paint();
        mbgPaint.setColor(Color.parseColor("#F0F0F0"));
        mbgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mbgPaint.setAntiAlias(true);
        mbgPaint.setStrokeCap(Paint.Cap.ROUND);
        mbgPaint.setStrokeWidth(DensityUtil.dip2px(App.getContext(), 3));

        //设置进度画笔
        mProgressPaint = new Paint();
        mProgressPaint.setColor(Color.parseColor("#0DE6C2"));
        mProgressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeWidth(DensityUtil.dip2px(App.getContext(), 3));

        //圆
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.parseColor("#fafafa"));
        mCirclePaint.setShadowLayer(DensityUtil.dip2px(App.getContext(), 2), 0, 0, Color.parseColor("#38000000"));
        mCirclePaint.setStyle(Paint.Style.FILL);

        //文本画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setColor(Color.parseColor("#9a9a9a"));
        mTextPaint.setTextSize(mTextSize);

        path = new Path();
        desPath = new Path();
    }

    //设置评价字符串集合
    public void setEvalustes(String[] strings) {
        evaluates.clear();
        evaluates.addAll(Arrays.asList(strings));
        if (evaluates.size() == 0) {
            aveWidth = 0;
        } else if (evaluates.size() == 1) {
            aveWidth = maxLength;
        } else {
            aveWidth = maxLength / (evaluates.size() - 1);
        }
        invalidate();
    }
}
