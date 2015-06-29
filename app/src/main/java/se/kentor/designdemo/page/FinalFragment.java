package se.kentor.designdemo.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.kentor.designdemo.R;

/**
 * Created by Ilya Klyukin.
 */
public class FinalFragment extends Fragment {

    public FinalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_final, container, false);
        return rootView;
    }
}
