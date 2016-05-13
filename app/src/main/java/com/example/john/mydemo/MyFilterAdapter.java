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
public class MyFilterAdapter extends BaseAdapter implements Filterable {
    private MyFilter mFilter;
    private List<String> mFilterData;
    private List<String> newFilterData;
    private LayoutInflater inflater;
    private int mResouce;

    public MyFilterAdapter( Context context,int textViewResouceId,List<String> array){
        this.newFilterData=array;
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFilter=new MyFilter();
        mResouce=textViewResouceId;
    }

    @Override
    public int getCount() {
        return newFilterData.size();
    }

    @Override
    public Object getItem(int position) {
        return newFilterData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView=inflater.inflate(mResouce, parent,false);
        }
        TextView textView= (TextView) convertView.findViewById(R.id.text);
        textView.setText(newFilterData.get(position));
        return textView;
    }
    @Override
    public Filter getFilter() {
        return mFilter;
    }
    //自定义过滤器
    private class MyFilter extends Filter
    {
        private final Object mLock = new Object();
        //得到过滤结果
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(mFilterData == null)
            {
                synchronized (mLock) {
                    mFilterData = new ArrayList<String>(newFilterData);
                }
            }
            ArrayList<String> values = new ArrayList<String>();
            for(int i = 0;i < mFilterData.size();i++)
            {
                String value = mFilterData.get(i);
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
        protected void publishResults(CharSequence constraint,FilterResults results) {
            newFilterData = (List<String>)results.values;
            if(results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
            {
                notifyDataSetInvalidated();
            }
        }

    }

}


