package com.longshihan.repair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longshihan.repair.view.DatePicker;
import com.longshihan.repair.view.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Calendar calendar;
    private DatePicker dp_test;
    private TimePicker tp_test;
    private TextView tv_ok,tv_cancel;   //确定、取消button
    private Button btn_naozhong;
    private PopupWindow pw;
    private String selectDate,selectTime;
    //选择时间与当前时间，用于判断用户选择的是否是以前的时间
    private int currentHour,currentMinute,currentDay,selectHour,selectMinute,selectDay;
    //整体布局
    private RelativeLayout Rl_all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Rl_all = (RelativeLayout) findViewById(R.id.Rl_all);
        btn_naozhong = (Button) findViewById(R.id.btn_naozhong);
        calendar = Calendar.getInstance();

        btn_naozhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                View view = View.inflate(MainActivity.this, R.layout.time_popup, null);
                selectDate = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月"
                        + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                        + DatePicker.getDayOfWeekCN(calendar.get(Calendar.DAY_OF_WEEK));
                //选择时间与当前时间的初始化，用于判断用户选择的是否是以前的时间，如果是，弹出toss提示不能选择过去的时间
                selectDay = currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                selectMinute = currentMinute = calendar.get(Calendar.MINUTE);
                selectHour = currentHour = calendar.get(Calendar.HOUR_OF_DAY);

                selectTime = currentHour + "点" + ((currentMinute < 10)?("0"+currentMinute):currentMinute) + "分";
                dp_test = (DatePicker)view.findViewById(R.id.dp_test);
                tp_test = (TimePicker)view.findViewById(R.id.tp_test);
                tv_ok = (TextView) view.findViewById(R.id.tv_ok);
                tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
                //设置滑动改变监听器
                dp_test.setOnChangeListener(dp_onchanghelistener);
                tp_test.setOnChangeListener(tp_onchanghelistener);
                pw = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                //              //设置这2个使得点击pop以外区域可以去除pop
                //              pw.setOutsideTouchable(true);
                //              pw.setBackgroundDrawable(new BitmapDrawable());

                //出现在布局底端
                pw.showAtLocation(Rl_all, 0, 0,  Gravity.END);

                //点击确定
                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if(selectDay == currentDay ){   //在当前日期情况下可能出现选中过去时间的情况
                            if(selectHour < currentHour){
                                Toast.makeText(getApplicationContext(), "不能选择过去的时间\n        请重新选择", 0).show();
                            }else if( (selectHour == currentHour) && (selectMinute < currentMinute) ){
                                Toast.makeText(getApplicationContext(), "不能选择过去的时间\n        请重新选择", 0).show();
                            }else{
                                Toast.makeText(getApplicationContext(), selectDate+selectTime, 0).show();
                                pw.dismiss();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), selectDate+selectTime, 0).show();
                            pw.dismiss();
                        }
                    }
                });

                //点击取消
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        pw.dismiss();
                    }
                });
            }
        });
    }

    //listeners
    DatePicker.OnChangeListener dp_onchanghelistener = new DatePicker.OnChangeListener() {
        @Override
        public void onChange(int year, int month, int day, int day_of_week) {
            selectDay = day;
            selectDate = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
        }
    };
    TimePicker.OnChangeListener tp_onchanghelistener = new TimePicker.OnChangeListener() {
        @Override
        public void onChange(int hour, int minute) {
            selectTime = hour + "点" + ((minute < 10)?("0"+minute):minute) + "分";
            selectHour = hour;
            selectMinute = minute;
        }
    };
}
