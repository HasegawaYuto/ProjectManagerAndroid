package projectmanager.hase3desu.jp.projectmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import android.support.v4.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    FragmentTransaction transaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tasksItem:
                    //mTextMessage.setText("Tasks");
                    return true;
                case R.id.personalItem:
                    //mTextMessage.setText("Personal");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Integer tmp = 1;

        transaction = getSupportFragmentManager().beginTransaction();

        if(tmp == 1) {
            FragmentPersonal fragment = new FragmentPersonal();
            transaction.add(R.id.container, fragment);
        }else{
            FragmentPersonalTask fragment = new FragmentPersonalTask();
            transaction.add(R.id.container, fragment);
        }
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        transaction = getSupportFragmentManager().beginTransaction();
        if (item.getItemId() == R.id.menu_project) {
            FragmentProjectDetail fragment = new FragmentProjectDetail();
            transaction.addToBackStack(null);
            transaction.replace(R.id.container, fragment);
        }

        if (item.getItemId() == R.id.menu_task) {
            FragmentTaskDetail fragment = new FragmentTaskDetail();
            transaction.addToBackStack(null);
            transaction.replace(R.id.container, fragment);
        }

        transaction.commit();
        return true;
    }

}
