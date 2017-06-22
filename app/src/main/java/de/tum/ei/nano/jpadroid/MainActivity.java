package de.tum.ei.nano.jpadroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TableFragment.OnVariableChangedListener, ExamplesFragment.OnVariableChangedListener, SettingsFragment.OnSettingsChangedListener {

    private static final int SIGNAL_FREQ = 0;
    private static final int PUMP_VOLT = 1;
    private static final int CRITICAL_I = 2;
    private static final int CAP_1 = 3;
    private static final int CAP_2 = 4;

    private static final int TABLE = 0;
    private static final int PLOT = 1;
    private static final int DOC = 2;
    private static final int EXAMPLE = 3;
    private static final int SETTINGS = 4;


    NavigationView navigationView = null;
    Toolbar toolbar = null;

    int currentFragment = TABLE;

    private double omega1 = 10e9;
    private double V0 = 20e-6;
    private double Ic = 10e-6;
    private double C1 = 1e-9;
    private double C2 = 0.1e-9;
    private int frqProgress = 9;
    private int voltProgress = 19;
    private int criticalIProgress = 9;
    private int cap1Progress = 99;
    private int cap2Progress = 9;

    private ArrayList<Double> Q1 = new ArrayList<Double>();
    private ArrayList<Double> Q2 = new ArrayList<Double>();
    private ArrayList<Double> Phi1 = new ArrayList<Double>();
    private ArrayList<Double> Phi2 = new ArrayList<Double>();
    private ArrayList<Double> a1D1 = new ArrayList<Double>();
    private ArrayList<Double> a2D2 = new ArrayList<Double>();
    private ArrayList<Double> Energy1 = new ArrayList<Double>();
    private ArrayList<Double> Energy2 = new ArrayList<Double>();
    private ArrayList<Double> time = new ArrayList<Double>();

    double[] Q1Array = new double[Q1.size()];
    double[] Q2Array = new double[Q2.size()];
    double[] Phi1Array = new double[Phi1.size()];
    double[] Phi2Array = new double[Phi2.size()];
    double[] a1D1Array = new double[a1D1.size()];
    double[] a2D2Array = new double[a2D2.size()];
    double[] Energy1Array = new double[Energy1.size()];
    double[] Energy2Array = new double[Energy2.size()];
    double[] timeArray = new double[time.size()];

    public double getOmega1() {
        return omega1;
    }

    public void setOmega1(double omega1) {
        this.omega1 = omega1;
    }

    public double getV0() {
        return V0;
    }

    public void setV0(double v0) {
        V0 = v0;
    }

    public double getIc() {
        return Ic;
    }

    public void setIc(double ic) {
        Ic = ic;
    }

    public double getC1() {
        return C1;
    }

    public void setC1(double c1) {
        C1 = c1;
    }

    public double getC2() {
        return C2;
    }

    public void setC2(double c2) {
        C2 = c2;
    }

    public int getFrqProgress() {
        return frqProgress;
    }

    public void setFrqProgress(int frqProgress) {
        this.frqProgress = frqProgress;
    }

    public int getVoltProgress() {
        return voltProgress;
    }

    public void setVoltProgress(int voltProgress) {
        this.voltProgress = voltProgress;
    }

    public int getCriticalIProgress() {
        return criticalIProgress;
    }

    public void setCriticalIProgress(int criticalIProgress) {
        this.criticalIProgress = criticalIProgress;
    }

    public int getCap1Progress() {
        return cap1Progress;
    }

    public void setCap1Progress(int cap1Progress) {
        this.cap1Progress = cap1Progress;
    }

    public int getCap2Progress() {
        return cap2Progress;
    }

    public void setCap2Progress(int cap2Progress) {
        this.cap2Progress = cap2Progress;
    }

    public ArrayList<Double> getQ1() {
        return Q1;
    }

    public void setQ1(ArrayList<Double> q1) {
        Q1 = q1;
    }

    public ArrayList<Double> getQ2() {
        return Q2;
    }

    public void setQ2(ArrayList<Double> q2) {
        Q2 = q2;
    }

    public ArrayList<Double> getPhi1() {
        return Phi1;
    }

    public void setPhi1(ArrayList<Double> phi1) {
        Phi1 = phi1;
    }

    public ArrayList<Double> getPhi2() {
        return Phi2;
    }

    public void setPhi2(ArrayList<Double> phi2) {
        Phi2 = phi2;
    }

    public ArrayList<Double> getA1D1() {
        return a1D1;
    }

    public void setA1D1(ArrayList<Double> a1D1) {
        this.a1D1 = a1D1;
    }

    public ArrayList<Double> getA2D2() {
        return a2D2;
    }

    public void setA2D2(ArrayList<Double> a2D2) {
        this.a2D2 = a2D2;
    }

    public ArrayList<Double> getEnergy1() {
        return Energy1;
    }

    public void setEnergy1(ArrayList<Double> energy1) {
        Energy1 = energy1;
    }

    public ArrayList<Double> getEnergy2() {
        return Energy2;
    }

    public void setEnergy2(ArrayList<Double> energy2) {
        Energy2 = energy2;
    }

    public ArrayList<Double> getTime() {
        return time;
    }

    public void setTime(ArrayList<Double> time) {
        this.time = time;
    }

    //Set fragment initially
    private PlotFragment plotfragment = new PlotFragment();
    private TableFragment tablefragment = new TableFragment();
    private DocumentationFragment documentationfragment = new DocumentationFragment();
    private SettingsFragment settingsfragment = new SettingsFragment();
    private ExamplesFragment examplesfragment = new ExamplesFragment();
    private jpaCalculation calculation = new jpaCalculation(0.0, 0.0, 0.0, 0.0, 0.0, new double[]{0.0, 0.0, 0.0, 0.0}, new double[]{0.0, 0.0, 0.0, 0.0});

    private static Context mainContext;
    private ArrayList<Integer> colorList = new ArrayList<Integer>();

    public ArrayList<Integer> getColorList() {
        return colorList;
    }

    public static Context getMainContext() {
        return mainContext;
    }

    public boolean calculationValid = false;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("CurrentFragment", currentFragment);

//        switch (currentFragment) {
//
//            case PLOT:  getFragmentManager().putFragment(outState,"PlotFragment", plotfragment);
//                break;
//            case TABLE:  getFragmentManager().putFragment(outState,"TableFragment", tablefragment);
//                break;
//            case DOC:  getFragmentManager().putFragment(outState,"DocumentationFragment", documentationfragment);
//                break;
//            case EXAMPLE:  getFragmentManager().putFragment(outState,"ExamplesFragment", examplesfragment);
//                break;
//        }

        Q1Array = new double[Q1.size()];
        Q2Array = new double[Q2.size()];
        Phi1Array = new double[Phi1.size()];
        Phi2Array = new double[Phi2.size()];
        a1D1Array = new double[a1D1.size()];
        a2D2Array = new double[a2D2.size()];
        Energy1Array = new double[Energy1.size()];
        Energy2Array = new double[Energy2.size()];
        timeArray = new double[time.size()];

        if (Q1.size() == Q2.size() && Q1.size() == Phi1.size() && Phi1.size() == Phi2.size() && Q1.size() == time.size() && a1D1.size() == time.size() && a2D2.size() == a1D1.size() && Energy1.size() == a1D1.size() && Energy2.size() == a2D2.size()) {

            for (int i = 0; i < Q1.size(); i++) {

                Q1Array[i] = Q1.get(i);
                Q2Array[i] = Q2.get(i);
                Phi1Array[i] = Phi1.get(i);
                Phi2Array[i] = Phi2.get(i);
                a1D1Array[i] = a1D1.get(i);
                a2D2Array[i] = a2D2.get(i);
                Energy1Array[i] = Energy1.get(i);
                Energy2Array[i] = Energy2.get(i);
                timeArray[i] = time.get(i);
            }

        }
        else {

            for (int i = 0; i < Q1.size(); i++) {

                Q1Array[i] = Q1.get(i);
            }

            for (int i = 0; i < Q2.size(); i++) {

                Q2Array[i] = Q2.get(i);
            }

            for (int i = 0; i < Phi1.size(); i++) {

                Phi1Array[i] = Phi1.get(i);
            }

            for (int i = 0; i < Phi2.size(); i++) {

                Phi2Array[i] = Phi2.get(i);
            }

            for (int i = 0; i < a1D1.size(); i++) {

                a1D1Array[i] = a1D1.get(i);
            }

            for (int i = 0; i < a2D2.size(); i++) {

                a2D2Array[i] = a2D2.get(i);
            }

            for (int i = 0; i < Energy1.size(); i++) {

                Energy1Array[i] = Energy1.get(i);
            }

            for (int i = 0; i < Energy2.size(); i++) {

                Energy2Array[i] = Energy2.get(i);
            }

            for (int i = 0; i < time.size(); i++) {

                timeArray[i] = time.get(i);
            }
        }

        outState.putDoubleArray("Q1", Q1Array);
        outState.putDoubleArray("Q2", Q2Array);
        outState.putDoubleArray("Phi1", Phi1Array);
        outState.putDoubleArray("Phi2", Phi2Array);
        outState.putDoubleArray("a1D1", a1D1Array);
        outState.putDoubleArray("a2D2", a2D2Array);
        outState.putDoubleArray("Energy1", Energy1Array);
        outState.putDoubleArray("Energy2", Energy2Array);
        outState.putDoubleArray("time", timeArray);
        outState.putDouble("omega1", this.omega1);
        outState.putDouble("V0", this.V0);
        outState.putDouble("Ic", this.Ic);
        outState.putDouble("C1", this.C1);
        outState.putDouble("C2", this.C2);
        outState.putInt("frqProgress", this.frqProgress);
        outState.putInt("voltProgress", this.voltProgress);
        outState.putInt("criticalIProgress", this.criticalIProgress);
        outState.putInt("cap1Progress", this.cap1Progress);
        outState.putInt("cap2Progress", this.cap2Progress);
        outState.putBoolean("calculationValid", this.calculationValid);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainContext = this;

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.colorList.add(ContextCompat.getColor(mainContext, R.color.blue));
        this.colorList.add(ContextCompat.getColor(mainContext, R.color.red));
        this.colorList.add(ContextCompat.getColor(mainContext, R.color.green));
        this.colorList.add(ContextCompat.getColor(mainContext, R.color.purple));

        if (savedInstanceState != null) {

            currentFragment = savedInstanceState.getInt("CurrentFragment");

            this.calculationValid = savedInstanceState.getBoolean("calculationValid");
            this.omega1 = savedInstanceState.getDouble("omega1");
            this.V0 = savedInstanceState.getDouble("V0");
            this.Ic = savedInstanceState.getDouble("Ic");
            this.C1 = savedInstanceState.getDouble("C1");
            this.C2 = savedInstanceState.getDouble("C2");
            this.frqProgress = savedInstanceState.getInt("frqProgress");
            this.voltProgress = savedInstanceState.getInt("voltProgress");
            this.criticalIProgress = savedInstanceState.getInt("criticalIProgress");
            this.cap1Progress = savedInstanceState.getInt("cap1Progress");
            this.cap2Progress = savedInstanceState.getInt("cap2Progress");

            Q1Array = savedInstanceState.getDoubleArray("Q1");
            Q2Array = savedInstanceState.getDoubleArray("Q2");
            Phi1Array = savedInstanceState.getDoubleArray("Phi1");
            Phi2Array = savedInstanceState.getDoubleArray("Phi2");
            a1D1Array = savedInstanceState.getDoubleArray("a1D1");
            a2D2Array = savedInstanceState.getDoubleArray("a2D2");
            Energy1Array = savedInstanceState.getDoubleArray("Energy1");
            Energy2Array = savedInstanceState.getDoubleArray("Energy2");
            timeArray = savedInstanceState.getDoubleArray("time");

            recoverState();

//            switch (currentFragment) {
//
//                case PLOT:  plotfragment = (PlotFragment) getFragmentManager().getFragment(savedInstanceState, "PlotFragment");
//                    break;
//                case TABLE:  tablefragment = (TableFragment) getFragmentManager().getFragment(savedInstanceState, "TableFragment");
//                    break;
//                case DOC:  documentationfragment = (DocumentationFragment) getFragmentManager().getFragment(savedInstanceState, "DocumentationFragment");
//                    break;
//                case EXAMPLE:  examplesfragment = (ExamplesFragment) getFragmentManager().getFragment(savedInstanceState, "ExamplesFragment");
//                    break;
//            }


            triggerFragmentTransition(currentFragment);

        }
        else {

            triggerFragmentTransition(TABLE);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean validBefore = MainActivity.this.calculationValid;

                SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getMainContext());
                boolean settingTransition = SP.getBoolean("switch_auto_fragment_transition", false);

                if (MainActivity.this.calculationValid) {
                    //DO NOTHING
                } else {

                    doMath();
                }

                if (settingTransition) {


                    if (currentFragment == PLOT) {

                        if(validBefore) {

                            tablefragment = new TableFragment();
                            triggerFragmentTransition(TABLE);

                        } else {

                            plotfragment = new PlotFragment();
                            triggerFragmentTransition(PLOT);
                        }

                    } else if (currentFragment == TABLE) {

                        plotfragment = new PlotFragment();
                        triggerFragmentTransition(PLOT);
                    }

                } else {

                    if (currentFragment == PLOT) {

                        plotfragment = new PlotFragment();
                        triggerFragmentTransition(PLOT);
                    }
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void doMath() {

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getMainContext());
        boolean settingQuantum = SP.getBoolean("switch_preference_quantum", true);

        double initSignalPhotonNumber = Double.parseDouble(SP.getString("edit_text_signal_photon_number", "1" ));
        double initIdlerPhotonNumber = Double.parseDouble(SP.getString("edit_text_idler_photon_number", "0" ));
        double initQ1 = Double.parseDouble(SP.getString("edit_text_q1", "1e-3" ));
        double initQ2 = Double.parseDouble(SP.getString("edit_text_q2", "0" ));
        double initPhi1 = Double.parseDouble(SP.getString("edit_text_phi1", "0" ));
        double initPhi2 = Double.parseDouble(SP.getString("edit_text_phi2", "0" ));


        if (settingQuantum == false) {

            double[] stateVector = {0.0, 0.0, 0.0, 0.0};
//            double[] initValues = {1e-3, 0.0, 0.0, 0.0};
            double[] initValues = {initQ1, initQ2, initPhi1, initPhi2};

            calculation = new jpaCalculation(MainActivity.this.getOmega1(), MainActivity.this.getC1(), MainActivity.this.getC2(), MainActivity.this.getV0(), MainActivity.this.getIc(), initValues, stateVector);
            calculation.classical();

        }
        else {

            double[] stateVector = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
//            double[] initValues = {Math.sqrt(3.0)/2, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
            double[] initValues = {Math.sqrt(3.0)/2.0*initSignalPhotonNumber, 0.0, Math.sqrt(3.0)/2.0*initIdlerPhotonNumber, 0.0, initSignalPhotonNumber, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, initIdlerPhotonNumber, 0.0, 0.0};

            calculation = new jpaCalculation(MainActivity.this.getOmega1(), MainActivity.this.getC1(), MainActivity.this.getC2(), MainActivity.this.getV0(), MainActivity.this.getIc(), initValues, stateVector);
            calculation.quantum();

        }

        MainActivity.this.setQ1(calculation.getQ1());
        MainActivity.this.setQ2(calculation.getQ2());
        MainActivity.this.setPhi1(calculation.getPhi1());
        MainActivity.this.setPhi2(calculation.getPhi2());
        MainActivity.this.setA1D1(calculation.getA1D1());
        MainActivity.this.setA2D2(calculation.getA2D2());
        MainActivity.this.setEnergy1(calculation.getEnergy1());
        MainActivity.this.setEnergy2(calculation.getEnergy2());
        MainActivity.this.setTime(calculation.getTime());

        MainActivity.this.calculationValid = true;
        Snackbar.make(findViewById(R.id.mainActivity), "Calculation done...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            triggerFragmentTransition(SETTINGS);
            return true;
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            triggerFragmentTransition(PLOT);
        } else if (id == R.id.nav_slideshow) {
            triggerFragmentTransition(TABLE);
        } else if (id == R.id.nav_documentation) {
            triggerFragmentTransition(DOC);
        } else if (id == R.id.nav_examples) {
            triggerFragmentTransition(EXAMPLE);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onVariableChanged(int name, int value) {

        switch (name) {
            case SIGNAL_FREQ:  this.frqProgress = value; this.omega1 = (((double) this.frqProgress) + 1.0)*1e9;
                break;
            case PUMP_VOLT:  this.voltProgress = value; this.V0 = (((double) this.voltProgress) + 1.0)*1e-6;
                break;
            case CRITICAL_I:  this.criticalIProgress = value; this.Ic = (((double) this.criticalIProgress) + 1.0)*1e-6;
                break;
            case CAP_1:  this.cap1Progress = value; this.C1 = (((double) this.cap1Progress) + 1.0)*1e-11;
                break;
            case CAP_2:  this.cap2Progress = value; this.C2 = (((double) this.cap2Progress) + 1.0)*1e-11;
                break;
        }

        this.calculationValid = false;

    }

    @Override
    public void onSettingsChanged() {

        this.calculationValid = false;

    }

    public void triggerFragmentTransition(int fragmentNumber) {

        FragmentTransaction fragmentTransaction = null;

        switch (fragmentNumber) {


            case PLOT:

                if (!this.calculationValid) {
                    Snackbar.make(findViewById(R.id.mainActivity), "Refresh calculations please...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, this.plotfragment = new PlotFragment());
                currentFragment = PLOT;

                navigationView.getMenu().getItem(PLOT).setChecked(true);

                fragmentTransaction.commit();
                break;
            case TABLE:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, this.tablefragment = new TableFragment());
                currentFragment = TABLE;

                navigationView.getMenu().getItem(TABLE).setChecked(true);

                fragmentTransaction.commit();
                break;
            case DOC:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, this.documentationfragment = new DocumentationFragment());
                currentFragment = DOC;

                navigationView.getMenu().getItem(DOC).setChecked(true);

                fragmentTransaction.commit();
                break;
            case SETTINGS:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, this.settingsfragment = new SettingsFragment());
                currentFragment = SETTINGS;
                fragmentTransaction.commit();
                break;
            case EXAMPLE:
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, this.examplesfragment = new ExamplesFragment());
                currentFragment = EXAMPLE;

                navigationView.getMenu().getItem(EXAMPLE).setChecked(true);

                fragmentTransaction.commit();
                break;
        }

    }

    private void recoverState() {

        if (Q1Array.length == Q2Array.length && Q1Array.length == Phi1Array.length && Phi1Array.length == Phi2Array.length && Q1Array.length == timeArray.length && a1D1Array.length == timeArray.length && a2D2Array.length == a1D1Array.length && Energy1Array.length == timeArray.length && Energy1Array.length == Energy2Array.length) {

            for (int i = 0; i < Q1Array.length; i++) {

                Q1.add(Q1Array[i]);
                Q2.add(Q2Array[i]);
                Phi1.add(Phi1Array[i]);
                Phi2.add(Phi2Array[i]);
                a1D1.add(a1D1Array[i]);
                a2D2.add(a2D2Array[i]);
                Energy1.add(Energy1Array[i]);
                Energy2.add(Energy2Array[i]);
                time.add(timeArray[i]);
            }

        }
        else {

            for (int i = 0; i < Q1Array.length; i++) {

                Q1.add(Q1Array[i]);
            }

            for (int i = 0; i < Q2Array.length; i++) {

                Q2.add(Q2Array[i]);
            }

            for (int i = 0; i < Phi1Array.length; i++) {

                Phi1.add(Phi1Array[i]);
            }

            for (int i = 0; i < Phi2Array.length; i++) {

                Phi2.add(Phi2Array[i]);
            }

            for (int i = 0; i < a1D1Array.length; i++) {

                a1D1.add(a1D1Array[i]);
            }

            for (int i = 0; i < a2D2Array.length; i++) {

                a2D2.add(a2D2Array[i]);
            }

            for (int i = 0; i < Energy1Array.length; i++) {

                Energy1.add(Energy1Array[i]);
            }

            for (int i = 0; i < Energy2Array.length; i++) {

                Energy2.add(Energy2Array[i]);
            }

            for (int i = 0; i < timeArray.length; i++) {

                time.add(timeArray[i]);
            }
        }
    }
}
