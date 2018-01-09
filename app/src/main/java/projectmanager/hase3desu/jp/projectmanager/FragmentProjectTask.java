package projectmanager.hase3desu.jp.projectmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hasegawayuto on 2018/01/08.
 */

public class FragmentProjectTask extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        getActivity().setTitle("projectName");

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_project_task, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("DEBUG_PRINT","onCreateOptionsMenu in FragmentProjectTask");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_project).setVisible(false);
        menu.findItem(R.id.menu_project_detail).setVisible(true);
        menu.findItem(R.id.menu_task).setVisible(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
