package de.tum.ei.nano.jpadroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExamplesFragment extends Fragment {

    private static final int SIGNAL_FREQ = 0;
    private static final int PUMP_VOLT = 1;
    private static final int CRITICAL_I = 2;
    private static final int CAP_1 = 3;
    private static final int CAP_2 = 4;

    private int frqProgress = 9;
    private int voltProgress = 19;
    private int criticalIProgress = 9;
    private int cap1Progress = 9;
    private int cap2Progress = 0;

    private int[] omega1Progress = { 9, 34 };
    private int[] V0Progress = { 19, 19 };
    private int[] IcProgress = { 9, 6 };
    private int[] C1Progress = { 99, 4 };
    private int[] C2Progress = { 9, 9 };

//    OnVariableChangedListener varListener;

    OnVariableChangedListener varListener = (OnVariableChangedListener) MainActivity.getMainContext();

    public interface OnVariableChangedListener {

        void onVariableChanged(int name, int value);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = null;

        if (context instanceof Activity){

            activity = (Activity) context;

        }

        try {

            varListener = (OnVariableChangedListener) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException(activity.toString() + " must implement OnVariableListener");

        }
    }

    public ExamplesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_examples, container, false);

        ListView listview = (ListView) view.findViewById(R.id.listView_examples);

        // Loading Dialog for Typesetting
        // ProgressDialog.show(MainActivity.getMainContext(), "Loading", "Wait while typesetting the documentation...");

        String[] values = new String[] { "Signal Frequency < Idler Frequency", "Signal Frequency > Idler Frequency" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(MainActivity.getMainContext(), android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                varListener.onVariableChanged(SIGNAL_FREQ, omega1Progress[position]);
                varListener.onVariableChanged(PUMP_VOLT, V0Progress[position]);
                varListener.onVariableChanged(CRITICAL_I, IcProgress[position]);
                varListener.onVariableChanged(CAP_1, C1Progress[position]);
                varListener.onVariableChanged(CAP_2, C2Progress[position]);

                ((MainActivity)getActivity()).doMath();

                ((MainActivity)getActivity()).triggerFragmentTransition(1);
            }

        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
    }

}
