package de.tum.ei.nano.jpadroid;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static de.tum.ei.nano.jpadroid.MainActivity.getMainContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlotFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plot, container, false);

        LineChart phi_q_chart = (LineChart) view.findViewById(R.id.phi_q_chart);
        LineChart energy_chart = (LineChart) view.findViewById(R.id.energy_chart);

        ArrayList<Double> time = ((MainActivity)getActivity()).getTime();
        ArrayList<Double> Q1 = ((MainActivity)getActivity()).getQ1();
        ArrayList<Double> Q2 = ((MainActivity)getActivity()).getQ2();
        ArrayList<Double> Phi1 = ((MainActivity)getActivity()).getPhi1();
        ArrayList<Double> Phi2 = ((MainActivity)getActivity()).getPhi2();
        ArrayList<Double> a1D1 = ((MainActivity)getActivity()).getA1D1();
        ArrayList<Double> a2D2 = ((MainActivity)getActivity()).getA2D2();
        ArrayList<Double> Energy1 = ((MainActivity)getActivity()).getEnergy1();
        ArrayList<Double> Energy2 = ((MainActivity)getActivity()).getEnergy2();

        ArrayList<Integer> colors = ((MainActivity)getActivity()).getColorList();

//        Log.d("ODE_Test", "Size of Plot Entries Energy1: " + Energy1.size());
//        Log.d("ODE_Test", "Size of Plot Entries time: " + time.size());

        if (!Q1.isEmpty() && !time.isEmpty()) {

//            Log.d("ODE_Test", "Value of Energy1(900): " + Energy1.get(900));

            List<Entry> entriesQ1 = new ArrayList<Entry>();
            List<Entry> entriesQ2 = new ArrayList<Entry>();
            List<Entry> entriesPhi1 = new ArrayList<Entry>();
            List<Entry> entriesPhi2 = new ArrayList<Entry>();
            List<Entry> entriesEnergy1 = new ArrayList<Entry>();
            List<Entry> entriesEnergy2 = new ArrayList<Entry>();
            List<Entry> entriesA1D1 = new ArrayList<Entry>();
            List<Entry> entriesA2D2 = new ArrayList<Entry>();

            for (int i = 1; i < time.size(); i++) {


                entriesQ1.add(new Entry(time.get(i).floatValue(),Q1.get(i).floatValue()));
                entriesQ2.add(new Entry(time.get(i).floatValue(),Q2.get(i).floatValue()));
                entriesPhi1.add(new Entry(time.get(i).floatValue(),Phi1.get(i).floatValue()));
                entriesPhi2.add(new Entry(time.get(i).floatValue(),Phi2.get(i).floatValue()));
                entriesEnergy1.add(new Entry(time.get(i).floatValue(),Energy1.get(i).floatValue()));
                entriesEnergy2.add(new Entry(time.get(i).floatValue(),Energy2.get(i).floatValue()));

                if (a1D1.size() == a2D2.size() && a1D1.size() == time.size()) {

                    entriesA1D1.add(new Entry(time.get(i).floatValue(),a1D1.get(i).floatValue()));
                    entriesA2D2.add(new Entry(time.get(i).floatValue(),a2D2.get(i).floatValue()));
                }

            }

            Log.d("ODE_Test", "Size of Entries: " + entriesQ1.size());

            LineDataSet dataSetQ1 = new LineDataSet(entriesQ1, "Q1"); // add entries to dataset
            dataSetQ1.setHighlightEnabled(false);
            dataSetQ1.setDrawHighlightIndicators(false);
            dataSetQ1.setDrawValues(false);
            dataSetQ1.setDrawCircles(false);
            dataSetQ1.setColors(colors.get(0));

            LineDataSet dataSetQ2 = new LineDataSet(entriesQ2, "Q2"); // add entries to dataset
            dataSetQ2.setHighlightEnabled(false);
            dataSetQ2.setDrawHighlightIndicators(false);
            dataSetQ2.setDrawValues(false);
            dataSetQ2.setDrawCircles(false);
            dataSetQ2.setColors(colors.get(1));

            LineDataSet dataSetPhi1 = new LineDataSet(entriesPhi1, "Phi1"); // add entries to dataset
            dataSetPhi1.setHighlightEnabled(false);
            dataSetPhi1.setDrawHighlightIndicators(false);
            dataSetPhi1.setDrawValues(false);
            dataSetPhi1.setDrawCircles(false);
            dataSetPhi1.setColors(colors.get(2));

            LineDataSet dataSetPhi2 = new LineDataSet(entriesPhi2, "Phi2"); // add entries to dataset
            dataSetPhi2.setHighlightEnabled(false);
            dataSetPhi2.setDrawHighlightIndicators(false);
            dataSetPhi2.setDrawValues(false);
            dataSetPhi2.setDrawCircles(false);
            dataSetPhi2.setColors(colors.get(3));

            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getMainContext());
            boolean showPhoton = SP.getBoolean("switch_show_photon_number", false);


                LineDataSet dataSetEnergy1 = new LineDataSet(entriesEnergy1, "Energy1"); // add entries to dataset
                dataSetEnergy1.setHighlightEnabled(false);
                dataSetEnergy1.setDrawHighlightIndicators(false);
                dataSetEnergy1.setDrawValues(false);
                dataSetEnergy1.setDrawCircles(false);
                dataSetEnergy1.setColors(colors.get(0));

                LineDataSet dataSetEnergy2 = new LineDataSet(entriesEnergy2, "Energy2"); // add entries to dataset
                dataSetEnergy2.setHighlightEnabled(false);
                dataSetEnergy2.setDrawHighlightIndicators(false);
                dataSetEnergy2.setDrawValues(false);
                dataSetEnergy2.setDrawCircles(false);
                dataSetEnergy2.setColors(colors.get(1));

            if (showPhoton == true) {

                dataSetEnergy1 = new LineDataSet(entriesA1D1, "Signal Photon Number"); // add entries to dataset
                dataSetEnergy1.setHighlightEnabled(false);
                dataSetEnergy1.setDrawHighlightIndicators(false);
                dataSetEnergy1.setDrawValues(false);
                dataSetEnergy1.setDrawCircles(false);
                dataSetEnergy1.setColors(colors.get(0));

                dataSetEnergy2 = new LineDataSet(entriesA2D2, "Idler Photon Number"); // add entries to dataset
                dataSetEnergy2.setHighlightEnabled(false);
                dataSetEnergy2.setDrawHighlightIndicators(false);
                dataSetEnergy2.setDrawValues(false);
                dataSetEnergy2.setDrawCircles(false);
                dataSetEnergy2.setColors(colors.get(1));

            }

            YAxis rightYAxisQPhi = phi_q_chart.getAxisRight();
            rightYAxisQPhi.setEnabled(false);

            YAxis yAxisQPhi = phi_q_chart.getAxisLeft();
            yAxisQPhi.setValueFormatter(new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    NumberFormat formatter = new DecimalFormat();

                    formatter = new DecimalFormat("0.#E0");

                    return formatter.format(value);
                }
            });

            XAxis xAxisQPhi = phi_q_chart.getXAxis();
            xAxisQPhi.setValueFormatter(new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    NumberFormat formatter = new DecimalFormat();

                    formatter = new DecimalFormat("0.#E0");

                    return formatter.format(value);
                }
            });

            xAxisQPhi.setPosition(XAxis.XAxisPosition.BOTTOM);

            YAxis rightYAxisEnergy = energy_chart.getAxisRight();
            rightYAxisEnergy.setEnabled(false);

            YAxis yAxisEnergy = energy_chart.getAxisLeft();
            yAxisEnergy.setValueFormatter(new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    NumberFormat formatter = new DecimalFormat();

                    formatter = new DecimalFormat("0.#E0");

                    return formatter.format(value);
                }
            });

            XAxis xAxisEnergy = energy_chart.getXAxis();
            xAxisEnergy.setValueFormatter(new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    NumberFormat formatter = new DecimalFormat();

                    formatter = new DecimalFormat("0.#E0");

                    return formatter.format(value);
                }
            });

            xAxisEnergy.setPosition(XAxis.XAxisPosition.BOTTOM);


            List<ILineDataSet> dataSetsQPhi = new ArrayList<ILineDataSet>();
            dataSetsQPhi.add(dataSetQ1);
            dataSetsQPhi.add(dataSetQ2);
            dataSetsQPhi.add(dataSetPhi1);
            dataSetsQPhi.add(dataSetPhi2);

            List<ILineDataSet> dataSetsEnergy = new ArrayList<ILineDataSet>();
            dataSetsEnergy.add(dataSetEnergy1);
            dataSetsEnergy.add(dataSetEnergy2);

            LineData dataQPhi = new LineData(dataSetsQPhi);
            LineData dataEnergy = new LineData(dataSetsEnergy);



            phi_q_chart.setPinchZoom(true);
            phi_q_chart.setData(dataQPhi);
            Description phi_q_description = phi_q_chart.getDescription();
            phi_q_description.setEnabled(false);
            phi_q_chart.invalidate();

            energy_chart.setPinchZoom(true);
            energy_chart.setData(dataEnergy);
            Description energy_description = energy_chart.getDescription();
            energy_description.setEnabled(false);
            energy_chart.invalidate();

        }

        return view;

    }

}
