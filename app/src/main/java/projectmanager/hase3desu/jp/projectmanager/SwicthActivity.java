package projectmanager.hase3desu.jp.projectmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SwicthActivity extends AppCompatActivity {

    Intent intent;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swicth);

        Log.d("DEBUG_PRINT","run SwitchActivity");

        Handler handle = new Handler();
        handle.postDelayed(new splashHandler(), 1000);
    }

    class splashHandler implements Runnable {
        public void run() {

            user = FirebaseAuth.getInstance().getCurrentUser();
            /*
            if (user !=null){
                FirebaseAuth.getInstance().signOut();
                user = null;
            }
            */

            if(user != null){
                intent = new Intent(getApplication(), MainActivity.class);
            }else{
                intent = new Intent(getApplication(), LoginActivity.class);
            }
            startActivity(intent);
            SwicthActivity.this.finish();
        }
    }
}
