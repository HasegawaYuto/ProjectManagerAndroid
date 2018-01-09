package projectmanager.hase3desu.jp.projectmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.view.Menu;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by hasegawayuto on 2018/01/07.
 */

public class FragmentPersonal extends Fragment {

    InputMethodManager inputMethodManager;
    LinearLayout mainLayout;
    EditText nameText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("DEBUG_PRINT","onCreateView");
        super.onCreateView(inflater, container, savedInstanceState);

        getActivity().setTitle("Personal");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("DEBUG_PRINT","onCreateOptionsMenu in FragmentPersonal");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_project).setVisible(true);
        menu.findItem(R.id.menu_project_detail).setVisible(false);
        menu.findItem(R.id.menu_task).setVisible(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("DEBUG_PRINT","onViewCreated");

        super.onViewCreated(view, savedInstanceState);

        nameText = (EditText) view.findViewById(R.id.personalUserNameText);
        nameText.clearFocus();
        mainLayout = (LinearLayout) view.findViewById(R.id.personalLayout);
        inputMethodManager =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(nameText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        nameText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    inputMethodManager.hideSoftInputFromWindow(nameText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }
        });

        Log.d("DEBUG_PRINT","onViewCreated 04");
    }
}
