package samplemodules.customadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class customAdapter extends AppCompatActivity {
    // Exemple extracted from http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter
    private ListView listView1;
    private  View header;
    Weather weather_data[];
    WeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter);
        weather_data = new Weather[]
                {
                        new Weather(R.drawable.weather_cloudy, "Cloudy"),
                        new Weather(R.drawable.weather_showers, "Showers"),
                        new Weather(R.drawable.weather_snow, "Snow"),
                        new Weather(R.drawable.weather_storm, "Storm"),
                        new Weather(R.drawable.weather_sunny, "Sunny")
                };

        adapter = new WeatherAdapter(this,
                R.layout.listview_item_row, weather_data);


        listView1 = (ListView)findViewById(R.id.listView1);

        header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listView1.addHeaderView(header);

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


//                String item = ((TextView)view).getText().toString();
                Weather temp =  (Weather) parent.getAdapter().getItem(position);
                String item =" teste  - position " + position + temp.title;
                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                Weather temp2= new Weather(temp.icon,temp.title);
                int x = weather_data.length;
                weather_data = new Weather[]
                        {
                                new Weather(R.drawable.weather_cloudy, "Cloudy"),
                                new Weather(R.drawable.weather_showers, "Showers"),
                                new Weather(R.drawable.weather_snow, "Snow"),
                                new Weather(R.drawable.weather_storm, "Storm"),
                                new Weather(R.drawable.weather_sunny, "Sunny"),
                                new Weather(R.drawable.weather_storm, "Storm"),
                                new Weather(R.drawable.weather_sunny, "Sunny"),
                        };

                adapter.notifyDataSetChanged();

                //  weather_data.append( new Weather(R.drawable.weather_cloudy, "Cloudy"));
            }
        });

    }
}
