package com.example.john.mydemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.Arrays;


public class MainActivity extends Activity implements View.OnClickListener {
    private AutoCompleteTextView auto;
    private Button btn;
    private String[] str={"测试4","联系","测试1","测试2","测试3"};
    private MyFilterAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
       // initClick();
        mAdapter=new MyFilterAdapter(this,R.layout.item,Arrays.asList(str));
        auto.setAdapter(mAdapter);
        auto.setCompletionHint("最近搜索的记录");
        auto.setThreshold(1);
    }

    private void initView() {
        auto= (AutoCompleteTextView) findViewById(R.id.auto);
        btn= (Button) findViewById(R.id.btn);
    }

    private void initClick(){
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn:
                saveHistory("search_history",auto); //保存查找记录
                initAuto();
                break;
        }
    }

    //初始化自动填充下拉框
    private void initAuto(){
        SharedPreferences sharepre=getSharedPreferences("search_history",MODE_PRIVATE);
        String History=sharepre.getString("history","nothing");
        if(!History.equals("nothing")){
            str=History.split(",");
            if(str.length>6){
                String[] data=new String[6];
                System.arraycopy(str,0,data,0,6);
                StringBuilder sb=new StringBuilder();
                for(int j=0;j<data.length;j++){
                    if(j==data.length-1){
                        sb.append(data[j]);
                    }else{
                        sb.append(data[j]+",");
                    }
                }
                str=data;
                sharepre.edit().putString("history",sb.toString()).commit();
                //adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,data);
            }
            //mAdapter=new MyFilterAdapter(str,MainActivity.this);
            auto.setAdapter(mAdapter);
            auto.setCompletionHint("最近搜索的记录");
            auto.setThreshold(1);
        }
    }

    //保存最近查找的记录
    private void saveHistory(String field,AutoCompleteTextView auto) {    //filed是文件名
        String text=auto.getText().toString();
        SharedPreferences share=getSharedPreferences(field,MODE_PRIVATE);
        String search_History=share.getString("history","");
        if(!search_History.contains(text+",")){
            StringBuilder sBuilder=new StringBuilder(search_History);
            sBuilder.insert(0,text+",");
            share.edit().putString("history",sBuilder.toString()).commit();
        }
    }


}
