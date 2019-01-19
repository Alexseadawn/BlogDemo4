package com.example.tonjies.template.module.refrence;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.util.AGlide;
import com.example.common.util.FontSetting;
import com.example.common.util.L;
import com.example.common.util.AHelpter;
import com.example.tonjies.template.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 舍长
 * describe:
 */
public class JavaActivity extends AppCompatActivity {

    //使用黄油刀生成TextView对象
    @BindView(R.id.tvText)
    TextView tvText;
    //输入框
    @BindView(R.id.edText)
    EditText edText;
    //测试图片
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        ButterKnife.bind(this);
        setTitle("tonjies的页面");
    }


    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //菜单选项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            //日志工具类
            case R.id.one:
                L.d("打印d级别的Log请求，Log是helloWorld");
                L.d(true);//传入Boolean类型
                Toast.makeText(this, "one", Toast.LENGTH_SHORT).show();
                break;
            //吐司工具类,传入String字符串
            case R.id.two:
                AHelpter.s("你好，世界");
                break;
            //吐司工具类，传入Boolean类型
            case R.id.three:
                AHelpter.s(true);
                break;
            //字体工具类使用assets文件夹类的字体文件
            case R.id.four:
                FontSetting.setFont(this, tvText, "fonts/MengYuanti.ttf");
                Toast.makeText(this, "four", Toast.LENGTH_SHORT).show();
                break;
            //使用AHelpter工具类获取EditView的值
            case R.id.five:
                String s = E.g(edText);
                //使用T工具进行空值判断，这里也可以传入两个String进行判断，这在登录页面很常见
                if (AHelpter.e(s)) {
                    AHelpter.s("输入框的内容是" + s);
                } else {
                    AHelpter.s("输入框的内容是空");
                }
                Toast.makeText(this, "five", Toast.LENGTH_SHORT).show();
                break;
            //测试Gilde封装工具类
            case R.id.six:
//                Glide.with(this).load("https://ww1.sinaimg.cn/large/0065oQSqly1fu7xueh1gbj30hs0uwtgb.jpg").into(iv);
                //传入图片链接，以及ImageView对象，记得初始化
                AGlide.getUrl("https://ww1.sinaimg.cn/large/0065oQSqly1fu7xueh1gbj30hs0uwtgb.jpg",iv);
                Toast.makeText(this, "six", Toast.LENGTH_SHORT).show();
                break;
            //
            case R.id.seven:
                Toast.makeText(this, "seven", Toast.LENGTH_SHORT).show();
                break;
            //
            case R.id.eight:
                Toast.makeText(this, "eight", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}
