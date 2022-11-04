package com.example.danhsachsinhvien_356;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<SinhVien> list_SinhVien;

    public Adapter(Context context, int layout, List<SinhVien> list_SinhVien) {
        this.context = context;
        this.layout = layout;
        this.list_SinhVien = list_SinhVien;
    }

    @Override
    public int getCount() {
        return list_SinhVien.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        // ánh xạ
        TextView tv_msv = view.findViewById(R.id.tv_msv);
        TextView tv_name = view.findViewById(R.id.tv_ten);

        //Gán giá trị

        SinhVien sinhVien = list_SinhVien.get(i);
        tv_msv.setText("MSV: " + sinhVien.getMsv351());
        tv_name.setText("Họ tên: " + sinhVien.getName351());

        return view;
    }
}
