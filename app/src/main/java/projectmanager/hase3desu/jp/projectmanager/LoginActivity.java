package projectmanager.hase3desu.jp.projectmanager;

import java.util.Map;
import java.util.HashMap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.app.ProgressDialog;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class LoginActivity extends AppCompatActivity {

    EditText mailText,passwordText,nameText;
    Button createAccountButton,loginButton,signinWithGithubButton;
    OnCompleteListener<AuthResult> createAccountListener,loginListener;

    InputMethodManager inputMethodManager;

    ProgressDialog mProgress;

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference mDataBaseReference;
    DatabaseReference userRef;
    LinearLayout mainLayout;

    boolean mIsCreateAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        mailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        nameText = (EditText) findViewById(R.id.nameText);
        mAuth = FirebaseAuth.getInstance();
        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("wait...");

        ////  createAccountListener
        createAccountListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // 成功した場合
                    // ログインを行う
                    String email = mailText.getText().toString();
                    String password = passwordText.getText().toString();
                    login(email, password);
                    changeMainActivity();
                } else {

                    // 失敗した場合
                    // エラーを表示する
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "アカウント作成に失敗しました", Snackbar.LENGTH_LONG).show();

                    // プログレスダイアログを非表示にする
                    mProgress.dismiss();
                }
            }
        };


        ////  loginListener
        loginListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // 成功した場合
                    user = mAuth.getCurrentUser();
                    userRef = mDataBaseReference.child(Const.UsersPATH).child(user.getUid());

                    if (mIsCreateAccount) {
                        // アカウント作成の時は表示名をFirebaseに保存する
                        String name = nameText.getText().toString();


                        Map<String, String> data = new HashMap<String, String>();
                        data.put("name", name);
                        userRef.setValue(data);

                        // 表示名をPrefarenceに保存する
                        saveName(name);
                    } else {
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Map data = (Map) snapshot.getValue();
                                saveName((String)data.get("name"));
                            }
                            @Override
                            public void onCancelled(DatabaseError firebaseError) {
                            }
                        });
                    }

                    // プログレスダイアログを非表示にする
                    mProgress.dismiss();

                    // Activityを閉じる
                    changeMainActivity();

                } else {
                    // 失敗した場合
                    // エラーを表示する
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "ログインに失敗しました", Snackbar.LENGTH_LONG).show();

                    // プログレスダイアログを非表示にする
                    mProgress.dismiss();
                }
            }
        };


        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードが出てたら閉じる
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String email = mailText.getText().toString();
                String password = passwordText.getText().toString();
                String name = nameText.getText().toString();

                if (email.length() != 0 && password.length() >= 6 && name.length() != 0) {
                    // ログイン時に表示名を保存するようにフラグを立てる
                    mIsCreateAccount = true;

                    createAccount(email, password);
                } else {
                    // エラーを表示する
                    Snackbar.make(v, "正しく入力してください", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードが出てたら閉じる
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String email = mailText.getText().toString();
                String password = passwordText.getText().toString();

                if (email.length() != 0 && password.length() >= 6) {
                    // フラグを落としておく
                    mIsCreateAccount = false;

                    login(email, password);
                } else {
                    // エラーを表示する
                    Snackbar.make(v, "正しく入力してください", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        mainLayout = (LinearLayout)findViewById(R.id.loginLayout);
        inputMethodManager =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        mailText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    inputMethodManager.hideSoftInputFromWindow(mailText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }
        });

        passwordText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    inputMethodManager.hideSoftInputFromWindow(mailText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }
        });

        nameText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    inputMethodManager.hideSoftInputFromWindow(mailText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }
        });
    }


    private void createAccount(String email, String password) {
        // プログレスダイアログを表示する
        mProgress.show();

        // アカウントを作成する
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(createAccountListener);
    }

    private void login(String email, String password) {
        // プログレスダイアログを表示する
        mProgress.show();

        // ログインする
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginListener);
    }

    private void saveName(String name) {
        // Preferenceに保存する
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name);
        editor.commit();
    }

    private void changeMainActivity(){
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}
