package rbhat.saiyajinlog;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import rbhat.saiyajin.listitems.HomeGridViewAdapter;

//git test comment
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView homeGrid = (GridView) findViewById(R.id.home_grid);
        HomeGridViewAdapter adapter = new HomeGridViewAdapter(this);
        homeGrid.setAdapter(adapter);
        homeGrid.setClickable(false);
        homeGrid.setFocusable(false);
        homeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String option = HomeGridViewAdapter.HOME_ITEMS[position];
                if(option.equals("New")) {
                    Intent intent = new Intent(MainActivity.this, NewWorkoutActivity.class);
                    startActivity(intent);
                }
                else if(option.equals("Show"))  {
                    Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                    startActivity(intent);
                }
                else if(option.equals("Browse")) {
                    Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
                    startActivity(intent);
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
