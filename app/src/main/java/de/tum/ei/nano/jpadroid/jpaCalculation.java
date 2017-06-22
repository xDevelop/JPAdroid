package de.tum.ei.nano.jpadroid;

/**
 * Created by ga73sut on 10.04.2017.
 */

import android.os.Environment;
import android.util.Log;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.*;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class jpaCalculation {

    private double C1;
    private double C2;
    private double omega1;
    private double V0;
    private double Ic;
    private static final double h = 6.62607004e-34;
    private static final double hbar = h/(2*Math.PI);
    private static final double e0 = 1.60217662e-19;

    private double[] initValues;
    private double[] stateVector;
    private double targetTime;

    private ArrayList<Double> Q1 = new ArrayList<Double>();
    private ArrayList<Double> Q2 = new ArrayList<Double>();
    private ArrayList<Double> Phi1 = new ArrayList<Double>();
    private ArrayList<Double> Phi2 = new ArrayList<Double>();
    private ArrayList<Double> a1 = new ArrayList<Double>();
    private ArrayList<Double> b1 = new ArrayList<Double>();
    private ArrayList<Double> a2 = new ArrayList<Double>();
    private ArrayList<Double> b2 = new ArrayList<Double>();
    private ArrayList<Double> a1D1 = new ArrayList<Double>();
    private ArrayList<Double> a11 = new ArrayList<Double>();
    private ArrayList<Double> b11 = new ArrayList<Double>();
    private ArrayList<Double> a12 = new ArrayList<Double>();
    private ArrayList<Double> b12 = new ArrayList<Double>();
    private ArrayList<Double> a1D2 = new ArrayList<Double>();
    private ArrayList<Double> b1D2 = new ArrayList<Double>();
    private ArrayList<Double> a2D2 = new ArrayList<Double>();
    private ArrayList<Double> a22 = new ArrayList<Double>();
    private ArrayList<Double> b22 = new ArrayList<Double>();
    private ArrayList<Double> Energy1 = new ArrayList<Double>();
    private ArrayList<Double> Energy2 = new ArrayList<Double>();
    private ArrayList<Double> time = new ArrayList<Double>();

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

    public ArrayList<Double> getA1() {
        return a1;
    }

    public void setA1(ArrayList<Double> a1) {
        this.a1 = a1;
    }

    public ArrayList<Double> getB1() {
        return b1;
    }

    public void setB1(ArrayList<Double> b1) {
        this.b1 = b1;
    }

    public ArrayList<Double> getA2() {
        return a2;
    }

    public void setA2(ArrayList<Double> a2) {
        this.a2 = a2;
    }

    public ArrayList<Double> getB2() {
        return b2;
    }

    public void setB2(ArrayList<Double> b2) {
        this.b2 = b2;
    }

    public ArrayList<Double> getA1D1() {
        return a1D1;
    }

    public void setA1D1(ArrayList<Double> a1D1) {
        this.a1D1 = a1D1;
    }

    public ArrayList<Double> getA11() {
        return a11;
    }

    public void setA11(ArrayList<Double> a11) {
        this.a11 = a11;
    }

    public ArrayList<Double> getB11() {
        return b11;
    }

    public void setB11(ArrayList<Double> b11) {
        this.b11 = b11;
    }

    public ArrayList<Double> getA12() {
        return a12;
    }

    public void setA12(ArrayList<Double> a12) {
        this.a12 = a12;
    }

    public ArrayList<Double> getB12() {
        return b12;
    }

    public void setB12(ArrayList<Double> b12) {
        this.b12 = b12;
    }

    public ArrayList<Double> getA1D2() {
        return a1D2;
    }

    public void setA1D2(ArrayList<Double> a1D2) {
        this.a1D2 = a1D2;
    }

    public ArrayList<Double> getB1D2() {
        return b1D2;
    }

    public void setB1D2(ArrayList<Double> b1D2) {
        this.b1D2 = b1D2;
    }

    public ArrayList<Double> getA2D2() {
        return a2D2;
    }

    public void setA2D2(ArrayList<Double> a2D2) {
        this.a2D2 = a2D2;
    }

    public ArrayList<Double> getA22() {
        return a22;
    }

    public void setA22(ArrayList<Double> a22) {
        this.a22 = a22;
    }

    public ArrayList<Double> getB22() {
        return b22;
    }

    public void setB22(ArrayList<Double> b22) {
        this.b22 = b22;
    }

    public ArrayList<Double> getTime() {
        return time;
    }

    public void setTime(ArrayList<Double> time) {
        this.time = time;
    }

    public jpaCalculation(double omega1, double C1, double C2, double V0, double Ic, double[] initValues, double[] stateVector) {

        this.omega1 = omega1;
        this.C1 = C1;
        this.C2 = C2;
        this.V0 = V0;
        this.Ic = Ic;

        //TODO: Check for problem dimension
        this.initValues = initValues;
        this.stateVector = stateVector;
        this.targetTime = 10.0*2.0*Math.PI/this.omega1;
    }

    public void classical() {

        FirstOrderIntegrator rkInt = new ClassicalRungeKuttaIntegrator(this.targetTime*1e-3);
        FirstOrderDifferentialEquations ode = new jpaClassicalEquation(this.omega1, this.C1, this.C2, this.V0, this.Ic);

        StepHandler stepHandler = new StepHandler() {

            public void init(double t0, double[] y0, double t) {

            }

            public void handleStep(StepInterpolator interpolator, boolean isLast) {
                double   t = interpolator.getCurrentTime();
                double[] var = interpolator.getInterpolatedState();

                Q1.add(var[0]);
                Q2.add(var[1]);
                Phi1.add(var[2]);
                Phi2.add(var[3]);
                time.add(t);

                Energy1.add((var[0]*var[0])/(2.0*C1) + (var[2]*var[2]*omega1*omega1*C1)/(2.0));
                Energy2.add((var[1]*var[1])/(2.0*C2) + (var[3]*var[3]*(2.0*e0/hbar*V0-omega1)*(2.0*e0/hbar*V0-omega1)*C2)/(2.0));

                if(isLast) {

//                    Do Nothing
//                    Log.d("ODE_Test", "Q1: " + var[0]);
//                    Log.d("ODE_Test", "time: " + t);
//                    Log.d("ODE_Test", "Size of Q1: " + Q1.size());
//                    Log.d("SD-Card-Path-Test", "Path: " + Environment.getExternalStorageDirectory());
                }
            }
        };

        rkInt.addStepHandler(stepHandler);
        rkInt.integrate(ode,0.0,this.initValues,this.targetTime, this.stateVector);

    }

    public void quantum() {

        FirstOrderIntegrator rkInt = new ClassicalRungeKuttaIntegrator(this.targetTime*1e-3);
        FirstOrderDifferentialEquations ode = new jpaQuantumEquation(this.omega1, this.C1, this.C2, this.V0, this.Ic);

        StepHandler stepHandler = new StepHandler() {

            public void init(double t0, double[] y0, double t) {

            }

            public void handleStep(StepInterpolator interpolator, boolean isLast) {
                double   t = interpolator.getCurrentTime();
                double[] var = interpolator.getInterpolatedState();

                a1.add(var[0]);
                b1.add(var[1]);
                a2.add(var[2]);
                b2.add(var[3]);
                a1D1.add(var[4]);
                a11.add(var[5]);
                b11.add(var[6]);
                a12.add(var[7]);
                b12.add(var[8]);
                a1D2.add(var[9]);
                b1D2.add(var[10]);
                a2D2.add(var[11]);
                a22.add(var[12]);
                b22.add(var[13]);
                time.add(t);

                Phi1.add(Math.sqrt(((2.0*hbar)/(omega1*C1)))*var[0]);
                Phi2.add(Math.sqrt(((2.0*hbar)/((2.0*e0/hbar*V0-omega1)*C2)))*var[2]);
                Q1.add(Math.sqrt(((2.0*hbar*omega1*C1)))*var[1]);
                Q2.add(Math.sqrt(((2.0*hbar*(2.0*e0/hbar*V0-omega1)*C2)))*var[3]);

                Energy1.add(hbar*omega1*(var[4]+(1.0/2.0)));
                Energy2.add(hbar*(2*e0/hbar*V0-omega1)*(var[11]+(1.0/2.0)));

                if(isLast) {

//                    Do Nothing
//                    Log.d("ODE_Test", "Q1: " + var[0]);
//                    Log.d("ODE_Test", "time: " + t);
//                    Log.d("ODE_Test", "Size of Q1: " + Q1.size());
//                    Log.d("SD-Card-Path-Test", "Path: " + Environment.getExternalStorageDirectory());

                }
            }
        };

        rkInt.addStepHandler(stepHandler);
        rkInt.integrate(ode,0.0,this.initValues,this.targetTime, this.stateVector);

    }

}
