package com.example.danhsachsinhvien_356;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.sql.StatementEvent;

public class MainActivity extends AppCompatActivity {

    private Button btn_them,btn_sua,btn_lammoi;
    private EditText txt_name, txt_msv;
    private Adapter adapter;
    private TextView textView;
    private ListView listView;
    private List<SinhVien> list_SV = new ArrayList<>();
    private SQLiteDatabase db;
    private int idupdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        initData();



        adapter = new Adapter(this, R.layout.dataitem,list_SV);
        listView.setAdapter(adapter);


        btn_lammoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_name.setText("");
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    insertData();
                    loadData();
                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Mã sinh viên bị trùng vui lòng thử lại ")
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SinhVien sinhVien = list_SV.get(i);
                txt_name.setText(sinhVien.getName351());
                textView.setText("Mã sinh viên cần sửa: " + sinhVien.getMsv351());
                idupdate = sinhVien.getMsv351();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int item = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn có xoá không ?")
                        .setCancelable(false)
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleData(item);
                                loadData();
                            }
                        })
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn sữa không ?")
                        .setCancelable(false)
                        .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateData();
                                loadData();
                                Toast.makeText(MainActivity.this, "Bạn đã sữa thành công", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        loadData();



    }

    private void initData(){
        db = openOrCreateDatabase("SinhVien.db",MODE_PRIVATE,null);
        String sql = "create table if not exists users (msv integer primary key autoincrement, name text )";
        db.execSQL(sql);
    }
    private void insertData(){
        String name = txt_name.getText().toString();
        String sql = "insert into users (name) " +
                "values ('" + name + "')";
        db.execSQL(sql);
    }
    private void updateData(){
        String name = txt_name.getText().toString();
        String sql = " update users " +
                "set name = '" + name + "' " +
                "where msv = " + idupdate ;

        db.execSQL(sql);
    }
    private void deleData(int i){
        int msv = list_SV.get(i).getMsv351();
        String sql = "delete from users where msv = " + msv;
        db.execSQL(sql);
    }
    private void loadData(){
        list_SV.clear();
        String sql = "select * from users";
        Cursor cursor =  db.rawQuery(sql, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);

            SinhVien sinhVien = new SinhVien();
            sinhVien.setMsv351(id);
            sinhVien.setName351(name);
            list_SV.add(sinhVien);

            cursor.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }


    private void AnhXa() {
        btn_lammoi = findViewById(R.id.btn_lammoi);
        btn_sua = findViewById(R.id.btn_sua);
        btn_them = findViewById(R.id.btn_them);
        txt_name = findViewById(R.id.txt_name);
        listView = findViewById(R.id.lv_sinhVien);
        textView = findViewById(R.id.ThongTin_MSV);

    }


}