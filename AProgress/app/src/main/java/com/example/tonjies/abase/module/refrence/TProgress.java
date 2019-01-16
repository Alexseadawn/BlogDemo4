package com.example.tonjies.abase.module.refrence;

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
import com.example.tonjies.abase.util.AColor;
import com.example.tonjies.abase.util.ADensity;
import com.example.tonjies.abase.util.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 舍长 on 2019/1/10
 * describe:一个下方带文字的进度条
 */
public class TProgress extends View {

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

    //进度条评价文字集合
    private List<String> evaluates = new ArrayList<>();

    //文本的宽度
    private int textWidth;

    //文本的高度
    private int textHeight;

    //文本透明度
    private float textAlpha = 1;

    //文字与文字一段区间的宽度
    private float textDistance;

    //当前进度的百分比值
    private float result;

    //文字缩放的最终百分比
    private float textRadio = 0.8f;

    public TProgress(Context context) {
        super(context);
    }

    public TProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(AColor.changeAlpha(Color.parseColor("#9a9a9a"), (int) (textAlpha * 0xff)));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(3);

        //确定文字处于那一段区间
    }

    //重新计算控件的宽，高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    //返回高度值
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);//获取高度类型
        int size = MeasureSpec.getSize(heightMeasureSpec);//获取高度数值
        int minHeight = mCircleRadius * 2 + textHeight + ADensity.dip2px(4) + mCircleRadius * 2;
        //如果用户设定了指定大小
        if (mode == MeasureSpec.EXACTLY) {
            /**
             * 虽然用户已经指定了大小，但是万一指定的大小小于圆点指示器+提示文本的高度，
             * 还是会出现显示不全的情况，所以还要进行判断
             */
//            L.d("EXACTLY");
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
        //初始化List集合
//        evaluates.add("体验很差");
//        evaluates.add("体验一般");
//        evaluates.add("体验还行");
//        evaluates.add("体验很好");

        textWidth = (int) mTextPaint.measureText("体验很差");
        textHeight = (int) ((mTextPaint.descent() + mTextPaint.ascent()) / 2);
//        L.d("最大文本宽度");

        width = getWidth();//view的宽度
        height = getHeight();//view的高度

        //让左边距至少为半个圆点指示器的距离
        paddingLeft = getPaddingLeft();//距离左边的距离
        if (getPaddingLeft() < mCircleRadius) {
//            L.d("onLayout");
            paddingLeft = mCircleRadius;
        }
        //让右边距至少为半个圆点指示器的距离
        paddingRight = getPaddingRight();//距离右边的距离
        if (getPaddingRight() < mCircleRadius) {
            paddingRight = mCircleRadius;
        }

        //让左边距至少为文字宽度的一半
        if (paddingLeft < textWidth / 2) {
            paddingLeft = textWidth / 2;
        }
        //让右边距至少为文字宽度的一半
        if (paddingRight < textWidth / 2) {
            paddingRight = textWidth / 2;
        }


        //如果当前进度小于左边距
        setCurrentProgress();
        //最大进度长度等于View的宽度-(左边的内边距+右边的内边距)
        maxProgress = width - paddingLeft - paddingRight;
        //每一段文字区间的大小
        //当集合的长度为0，没有数据时
        if (evaluates.size() == 0) {
            textDistance = 0;
        }
        //当只有一个数据时，区间距离就是整个进度条
        else if (evaluates.size() == 1) {
            textDistance = maxProgress;
        }

        //当有多个数据时，区间距离就是整个进度条除以集合个数-1
        else {
            textDistance = maxProgress / (evaluates.size() - 1);
            L.d("textDistance" + textDistance);
        }
        float t = maxProgress / (evaluates.size() - 1);
        L.d("size:" + evaluates.size());
        L.d("max:" + maxProgress);
        L.d("count" + t);
        L.d("textDistance:" + textDistance);
    }

    //绘制控件
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 绘制中间的线段
         */
        canvas.drawLine(width / 2, 0, width / 2, height, progressPaint);
        /**
         * 1，绘制背景线段
         */
        canvas.drawLine(paddingLeft, height / 2, width - paddingRight, height / 2, bgPaint);
        /**
         * 2,绘制实际进度线段
         * 从（左边距，View高度的一半）开始，到（现在的触摸到的进度宽度，View高度的一半）还将绘制灰色背景线段
         */
        canvas.drawLine(paddingLeft, height / 2, currentProgress, height / 2, progressPaint);
        /**
         * 3,绘制圆点
         * 要支持阴影下过必须关闭硬件加速
         */
        setLayerType(LAYER_TYPE_SOFTWARE, null);//发光效果不支持硬件加速
        canvas.drawCircle(currentProgress, getHeight() / 2, mCircleRadius, circlePaint);
        /**
         * 4,绘制文字
         * 确定当前除以哪个区间：
         * 1，集合没有数据时，不会进入循环，不会进行文字绘制
         *   canvas.drawText(evaluates.get(i+1), tempProgress+paddingLeft, (float) (height / 2 + mCircleRadius * 2.8), mTextPaint);
         */
        mTextPaint.setTextSize(mTextSize);
        float tempProgress = currentProgress - paddingLeft;
        if (result == 100) {
            L.d("textDistance:" + textDistance);
            L.d("tempProgress:" + tempProgress);
        }
        for (int i = 0; i < evaluates.size(); i++) {
            float temp = tempProgress;
            float distance = textDistance;
            float half = textDistance / 2;
            ///后半部分
            if ((temp > ((i * distance) - half)) && (temp <= (half + (distance * i)))) {
                //后半段，透明度减低，变小
                if (temp - (i * distance) >= 0) {
                    float radio = (temp - (i * distance)) / half;
                    //textAlpha之间变小，radio逐渐变大
                    textAlpha = 1 - radio;

                    //要减去的范围，当textAlpha等于1时，文本最小
                    float size = (mTextSize - mTextSize * textRadio) * (radio);
                    mTextSize = mTextSize - size;
                    mTextPaint.setColor(AColor.changeAlpha(Color.parseColor("#9a9a9a"), (int) (textAlpha * 0xff)));
                    L.d("radio:" + radio);
                    L.d("后半段");
                    canvas.drawText(evaluates.get(i), tempProgress + paddingLeft, (float) (height / 2 + mCircleRadius * 2.8), mTextPaint);
                    //透明度增加，变大
                } else {
                    float radio = -(temp - (i * distance)) / half;
                    textAlpha = 1 - radio;
                    mTextSize = ADensity.dip2px(14);
                    float size = (mTextSize - mTextSize * textRadio) * (radio);
                    mTextSize = mTextSize - size;
                    mTextPaint.setColor(AColor.changeAlpha(Color.parseColor("#9a9a9a"), (int) (textAlpha * 0xff)));
                    L.d("前半段");
                    canvas.drawText(evaluates.get(i), tempProgress + paddingLeft, (float) (height / 2 + mCircleRadius * 2.8), mTextPaint);
                }
            }
//            if (tempProgress < textDistance / 2) {
//                canvas.drawText(evaluates.get(0), tempProgress + paddingLeft, (float) (height / 2 + mCircleRadius * 2.8), mTextPaint);
//            }
//            //第二段距离
//            if ((tempProgress > textDistance / 2) && (tempProgress <= textDistance * 1.5)) {
//                canvas.drawText(evaluates.get(1), tempProgress + paddingLeft, (float) (height / 2 + mCircleRadius * 2.8), mTextPaint);
//            }
//            //第三段距离
//            if ((tempProgress > textDistance * 1.5) && (tempProgress <= textDistance * 2.5)) {
//                canvas.drawText(evaluates.get(2), tempProgress + paddingLeft, (float) (height / 2 + mCircleRadius * 2.8), mTextPaint);
//            }
//            //第四段距离
//            if ((tempProgress > textDistance * 2.5) && (tempProgress <= textDistance * 3)) {
//                canvas.drawText(evaluates.get(3), tempProgress + paddingLeft, (float) (height / 2 + mCircleRadius * 2.8), mTextPaint);
//            }
        }

//        mTextPaint.setColor(AColor.changeAlpha(Color.parseColor("#9a9a9a"), (int) (textAlpha * 0xff)));

    }

    //开始x
    float startX;
    //结束x
    float endX;

    //触摸
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //按住
            case MotionEvent.ACTION_DOWN:
                //设置进度值
//                setMotionProgress(event);
                startX = event.getX();
                return true;
            //移动
            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                float dx = endX - startX;
                startX = endX;
                currentProgress = currentProgress + dx;
                //如果当前进度小于左边距
                setCurrentProgress();
                //看数学公式就可以了,实际百分比进度数值
                result = ((currentProgress - paddingLeft) * 100) / maxProgress;
                if (result == 100) {
                    L.d("curr:" + currentProgress);
                }
                //进行空值判断
                if (onProgressListener != null) {
                    onProgressListener.onSelect((int) result);
                }
                invalidate();
                //获取当前触摸点，赋值给当前进度
//                setMotionProgress(event);
                return true;
            //抬起，需要一个进度回弹的效果
            case MotionEvent.ACTION_UP:
                float temp = currentProgress - paddingLeft;
                float distance = textDistance;
                float half = textDistance / 2;
                float progressDistance;
                L.d("maxProfress");
                progressDistance = maxProgress / (evaluates.size() - 1);
                float result;
                for (int i = 0; i < evaluates.size(); i++) {
                    ///后半部分
                    if ((temp > ((i * distance) - half)) && (temp <= (half + (distance * i)))) {
                        currentProgress = progressDistance * i + paddingLeft;
                    }
                }
                //如果当前进度小于左边距
                setCurrentProgress();
                //看数学公式就可以了,实际百分比进度数值
                result = ((currentProgress - paddingLeft) * 100) / maxProgress;
                if (result == 100) {
                    L.d("curr:" + currentProgress);
                }
                //进行空值判断
                if (onProgressListener != null) {
                    onProgressListener.onSelect((int) result);
                }
                invalidate();
                return true;
        }
        return true;
    }

    //设置进度值
    private void setMotionProgress(MotionEvent event) {
//        L.d("Max:" + maxProgress);
//        L.d("dis" + textDistance);
        //获取当前触摸点，赋值给当前进度
        currentProgress = (int) event.getX();

        //如果当前进度小于左边距
        setCurrentProgress();
        //看数学公式就可以了,实际百分比进度数值
        result = ((currentProgress - paddingLeft) * 100) / maxProgress;
        if (result == 100) {
            L.d("curr:" + currentProgress);
        }
        //透明度变化
//        textAlpha = 1 - (float) (result * 0.01);
        //文字大小变化
//        L.d("mTextSizeChange"+mTextSize * (result * 0.01));
//        L.d("mTextSize:"+mTextSize);

//        mTextSize = mTextSize * (1 - (float) (result * 0.01));
//        mTextSize = ADensity.dip2px(14);
//        float size = (mTextSize - mTextSize * (1 - (float) (result * 0.01))) * (1 - textRadio);
//        L.d("minues" + (mTextSize - size));
//        L.d("progress:" + result);
//        L.d("size:" + size);
//        L.d("mTextSize" + mTextSize);
//        mTextSize = mTextSize - size;
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
        else if (currentProgress >= width - paddingRight) {
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

    //设置评价字符串集合
    public void setEvaluates(String[] strings) {
        evaluates.clear();
        evaluates.addAll(Arrays.asList(strings));
        //每一段文字区间的大小
        //当集合的长度为0，没有数据时
        if (evaluates.size() < 2) {
            //
            L.d("至少需要两个评价等级");
        }
        //当有多个数据时，区间距离就是整个进度条除以集合个数-1
        else {
            textDistance = maxProgress / (evaluates.size() - 1);
            L.d("textDistance" + textDistance);
        }
    }
}
