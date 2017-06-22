package de.tum.ei.nano.jpadroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private static final int SIGNAL_FREQ = 0;
    private static final int PUMP_VOLT = 1;
    private static final int CRITICAL_I = 2;
    private static final int CAP_1 = 3;
    private static final int CAP_2 = 4;

    private int frqProgress = 0;
    private int voltProgress = 0;
    private int criticalIProgress = 0;
    private int cap1Progress = 0;
    private int cap2Progress = 0;

    OnVariableChangedListener varListener = (OnVariableChangedListener) MainActivity.getMainContext();

    public interface OnVariableChangedListener {

        void onVariableChanged(int name, int value);
    }

    public TableFragment() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_table, container, false);

        WebView circuitWebView = (WebView) view.findViewById(R.id.webView_circuit);
        circuitWebView.setBackgroundColor(Color.TRANSPARENT);
        circuitWebView.loadUrl("file:///android_asset/circuit.html");

        this.frqProgress = ((MainActivity)getActivity()).getFrqProgress();
        this.voltProgress = ((MainActivity)getActivity()).getVoltProgress();;
        this.criticalIProgress = ((MainActivity)getActivity()).getCriticalIProgress();
        this.cap1Progress = ((MainActivity)getActivity()).getCap1Progress();
        this.cap2Progress = ((MainActivity)getActivity()).getCap2Progress();

        SeekBar frqBar = (SeekBar) view.findViewById(R.id.seekBarSignalFrequency);
        SeekBar voltBar = (SeekBar) view.findViewById(R.id.seekBarPumpVoltage);
        SeekBar criticalIBar = (SeekBar) view.findViewById(R.id.seekBarCriticalCurrent);
        SeekBar signalCBar = (SeekBar) view.findViewById(R.id.seekBarSignalCapacitor);
        SeekBar idlerCBar = (SeekBar) view.findViewById(R.id.seekBarIdlerCapacitor);

        final TextView frqTxt = (TextView) view.findViewById(R.id.textViewSignalFrequency);
        final TextView voltTxt = (TextView) view.findViewById(R.id.textViewPumpVoltage);
        final TextView criticalITxt = (TextView) view.findViewById(R.id.textViewCriticalCurrent);
        final TextView signalCTxt = (TextView) view.findViewById(R.id.textViewSignalCapacitor);
        final TextView idlerCTxt = (TextView) view.findViewById(R.id.textViewIdlerCapacitor);

        frqBar.setProgress(frqProgress);
        voltBar.setProgress(voltProgress);
        criticalIBar.setProgress(criticalIProgress);
        signalCBar.setProgress(cap1Progress);
        idlerCBar.setProgress(cap2Progress);

        frqTxt.setText(Integer.toString(frqProgress + 1) + " GHz");
        voltTxt.setText(Integer.toString(voltProgress + 1) + " µV");
        criticalITxt.setText(Integer.toString(criticalIProgress + 1) + " µA");
        signalCTxt.setText(Double.toString((((double) cap1Progress) + 1.0)/100.0) + " nF");
        idlerCTxt.setText(Double.toString((((double) cap2Progress) + 1.0)/100.0) + " nF");

        frqBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub

                frqTxt.setText(Integer.toString(progress + 1) + " GHz");
                frqProgress = progress;
                varListener.onVariableChanged(SIGNAL_FREQ, progress);
            }
        });

        voltBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub

                voltTxt.setText(Integer.toString(progress + 1) + " µV");
                voltProgress = progress;
                varListener.onVariableChanged(PUMP_VOLT, progress);
            }
        });

        criticalIBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub

                criticalITxt.setText(Integer.toString(progress + 1) + " µA");
                criticalIProgress = progress;
                varListener.onVariableChanged(CRITICAL_I, progress);
            }
        });

        signalCBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub

                signalCTxt.setText(Double.toString((((double) progress) + 1.0)/100.0) + " nF");
                cap1Progress = progress;
                varListener.onVariableChanged(CAP_1, progress);
            }
        });

        idlerCBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub

                idlerCTxt.setText(Double.toString((((double) progress) + 1.0)/100.0) + " nF");
                cap2Progress = progress;
                varListener.onVariableChanged(CAP_2, progress);
            }
        });

        // Inflate the layout for this fragment
        return view; //inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
