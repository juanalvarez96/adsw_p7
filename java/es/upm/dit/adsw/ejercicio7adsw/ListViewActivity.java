package es.upm.dit.adsw.ejercicio7adsw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by juanalvarez on 15/05/16.
 */
public class ListViewActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        final ListView miListView=(ListView)findViewById(R.id.listView);
        final List<RssContent.EntryRss> entries=RssContent.ITEMS;
        final Button button=(Button)findViewById(R.id.button2);
        if(entries.isEmpty()){
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent z=new Intent(ListViewActivity.this,SearchActivity.class);
                    startActivity(z);
                }
            });
        }
        ArrayAdapter<RssContent.EntryRss> adapter=new ArrayAdapter<RssContent.EntryRss>(this,android.R.layout.simple_list_item_1,entries);
        miListView.setAdapter(adapter);
        miListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = "Seleccionado " + parent.getAdapter().getItem(position);
                Log.d(TAG, msg);
                Intent i=new Intent(ListViewActivity.this,DetailActivity.class);
                i.putExtra("clave",position);
                startActivity(i);
            }
        });
    }
}
