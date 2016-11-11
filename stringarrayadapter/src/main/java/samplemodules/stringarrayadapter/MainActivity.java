package samplemodules.stringarrayadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView namesListView;
    private ArrayList<String> names;
    private ArrayAdapter<String> namesAA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namesListView = (ListView)findViewById(R.id.listView1);
        names = new ArrayList<String>();
        names.clear();
        names.add(0, "test");
        namesAA = new ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, android.R.id.text1, names );
        namesListView.setAdapter(namesAA);

    }
}
