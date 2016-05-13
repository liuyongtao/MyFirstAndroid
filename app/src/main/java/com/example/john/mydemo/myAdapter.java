package com.example.john.mydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2016/5/12.
 */
public class myAdapter extends BaseAdapter implements Filterable
{
    private List<String> mOriginalValues;
    private List<String> mObject;
    private final Object mLock = new Object();
    private int mResouce;
    private MyFilter myFilter = null;
    private LayoutInflater inflater;

    public myAdapter(Context context, int TextViewResouceId, List<String> objects)
    {
        init(context,TextViewResouceId,objects);
    }

    private void init(Context context, int textViewResouceId, List<String> objects)
    {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mObject = objects;
        mResouce = textViewResouceId;
        myFilter = new MyFilter();
    }

    @Override
    public int getCount() {
        return mObject.size();
    }

    @Override
    public String getItem(int position) {
        return mObject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return getViewFromResouce(position,convertView,parent,mResouce);
        if(convertView == null)
        {
            convertView = inflater.inflate(mResouce, parent,false);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.text);
        String item = getItem(position);
        tv.setText(item);
        return tv;
    }

    private View getViewFromResouce(int position, View convertView,
                                    ViewGroup parent, int mResouce2) {
        System.out.println("执行到这一步");
        if(convertView == null)
        {
            convertView = inflater.inflate(mResouce2, parent,false);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.text);
        String item = getItem(position);
        tv.setText(item);
        return tv;
    }

    //返回过滤器
    @Override
    public Filter getFilter() {
        return myFilter;
    }

    //自定义过滤器
    private class MyFilter extends Filter
    {
        //得到过滤结果
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(mOriginalValues == null)
            {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<String>(mObject);
                }
            }
            ArrayList<String> values = new ArrayList<String>();
            for(int i = 0;i < mOriginalValues.size();i++)
            {
                String value = mOriginalValues.get(i);
                //自定义匹配规则
                if(value != null && constraint != null && value.contains(constraint))
                {
                    values.add(value);
                }
            }
            results.values = values;
            results.count = values.size();
            return results;
        }
        //发布过滤结果
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //把搜索结果赋值给mObject这样每次输入字符串的时候就不必
            //从所有的字符串中查找，从而提高了效率
            mObject = (List<String>)results.values;
            if(results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
            {
//                mObject=
                notifyDataSetInvalidated();
            }
        }

    }

}