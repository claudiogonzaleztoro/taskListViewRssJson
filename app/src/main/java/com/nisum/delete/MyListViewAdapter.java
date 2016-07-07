package com.nisum.delete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NIS1175m on 7/5/16.
 */
public class MyListViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<AtpItemResponse> mDataList = new ArrayList<AtpItemResponse>();

    public MyListViewAdapter(Context cxt){
        mInflater = LayoutInflater.from(cxt);
    }

    public void setData(List<AtpItemResponse> dataList){
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AtpItemResponse data = mDataList.get(position);

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.listitem,null);
            System.out.println("item created for "+position);
        }

        ((TextView)convertView.findViewById(R.id.textView2)).setText(data.title);

        return convertView;

    }
}
