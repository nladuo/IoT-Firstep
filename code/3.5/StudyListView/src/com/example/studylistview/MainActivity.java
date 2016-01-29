package com.example.studylistview;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	ListView listView;
	List<ItemBean> datas;
	ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView = (ListView) findViewById(R.id.lv_main);
        datas = new ArrayList<ItemBean>();
        for (int i = 1; i <= 20; i++) {
			datas.add(new ItemBean(i + ""));
		}
        adapter = new ItemAdapter(datas, MainActivity.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "µã»÷ÁË"+datas.get(position).getText(),
						Toast.LENGTH_SHORT).show();
			}
		});
    }
}
