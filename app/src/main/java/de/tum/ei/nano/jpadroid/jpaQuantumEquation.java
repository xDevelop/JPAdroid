package de.tum.ei.nano.jpadroid;

/**
 * Created by ga73sut on 12.04.2017.
 */

import android.util.Log;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class jpaQuantumEquation implements FirstOrderDifferentialEquations{

    private double C1 = 1.0;
    private double C2 = 1.0;
    private double L1 = 1.0;
    private double L2 = 1.0;
    private double V0 = 1.0;
    private double Ic = 1.0;
    private double omega1 = 1.0;
    private double omega2 = 1.0;
    private double omega0 = 1.0;
    private static final double h = 6.62607004e-34;
    private static final double hbar = h/(2.0*Math.PI);
    private static final double e0 = 1.60217662e-19;
    private double c1 = 1.0;
    private double c2 = 1.0;

    public jpaQuantumEquation(double omega1, double C1, double C2, double V0, double Ic) {

        this.C1 = C1;
        this.C2 = C2;
        this.V0 = V0;
        this.Ic = Ic;
        this.omega0 = 2.0*e0/hbar*this.V0;
        this.omega1 = omega1;
        this.omega2 = this.omega0 - this.omega1;
        this.L1 = 1.0/(this.C1*omega1*omega1);
        this.L2 = 1.0/(this.C2*omega2*omega2);
        this.c1 = Math.sqrt(hbar*omega1*L1/2.0);
        this.c2 = Math.sqrt(hbar*omega2*L2/2.0);

    }

    @Override
    public int getDimension() {

        return 14;
    }

    @Override
    public void computeDerivatives(double t, double[] var, double[] varDot) throws MaxCountExceededException, DimensionMismatchException {

        varDot[0] = omega1*var[1];
        varDot[1] = -omega1*var[0] - (4.0*e0*Ic)/(hbar*hbar)*(c1*c1*var[0]+c1*c2*var[2])*Math.cos(omega0*t) - (Ic*c1)/(hbar)*Math.sin(omega0*t);
        varDot[2] = omega2*var[3];
        varDot[3] = -omega2*var[2] - (4.0*e0*Ic)/(hbar*hbar)*(c2*c2*var[2]+c1*c2*var[0])*Math.cos(omega0*t) - (Ic*c2)/(hbar)*Math.sin(omega0*t);
        varDot[4] = -(2.0*Ic*c1)/(hbar)*var[1]*Math.sin(omega0*t) - (4.0*e0*Ic)/(hbar*hbar)*(c1*c1*var[6] + c1*c2*var[8] - c1*c2*var[10])*Math.cos(omega0*t);
        varDot[5] = 2.0*omega1*var[6] + (2.0*Ic*c1)/(hbar)*var[1]*Math.sin(omega0*t) + (4.0*e0*Ic)/(hbar*hbar)*(c1*c1*var[6] + c1*c2*var[8] - c1*c2*var[10])*Math.cos(omega0*t);
        varDot[6] = -2.0*omega1*var[5] - (2.0*Ic*c1)/(hbar)*var[0]*Math.sin(omega0*t) - (4.0*e0*Ic)/(hbar*hbar)*(c1*c1*var[5] + c1*c1*var[4] + c1*c2*var[7] + c1*c2*var[9])*Math.cos(omega0*t);
        varDot[7] = (omega1 + omega2)*var[8] + (Ic)/(hbar)*(c1*var[3] + c2*var[1])*Math.sin(omega0*t) - (2.0*e0*Ic)/(hbar*hbar)*(-c1*c1*var[8] - c2*c2*var[8] - c1*c1*var[10] - c1*c2*var[6] - c1*c2*var[13] + c2*c2*var[10])*Math.cos(omega0*t);
        varDot[8] = -(omega1 + omega2)*var[7] - (Ic)/(hbar)*(c1*var[2] + c2*var[0])*Math.sin(omega0*t) - (2.0*e0*Ic)/(hbar*hbar)*(c1*c1*var[7] + c2*c2*var[7] + c1*c1*var[9] + c1*c2*var[5] + c1*c2*var[12] + c1*c2*var[4] + c1*c2*var[11] + c2*c2*var[9])*Math.cos(omega0*t);
        varDot[9] = -(omega1 - omega2)*var[10] - (Ic)/(hbar)*(c1*var[3] + c2*var[1])*Math.sin(omega0*t) + (2.0*e0*Ic)/(hbar*hbar)*(-c1*c1*var[8] - c1*c1*var[10] + c2*c2*var[10] - c1*c2*var[13] + c1*c2*var[6] + c2*c2*var[8])*Math.cos(omega0*t);
        varDot[10] = (omega1 - omega2)*var[9] + (Ic)/(hbar)*(c1*var[2] - c2*var[0])*Math.sin(omega0*t) + (2.0*e0*Ic)/(hbar*hbar)*(c1*c1*var[7] + c1*c1*var[9] - c2*c2*var[9] + c1*c2*var[12] - c1*c2*var[4] + c1*c2*var[11] + c1*c2*var[5] + c2*c2*var[7])*Math.cos(omega0*t);
        varDot[11] = -(2.0*Ic*c2)/(hbar)*var[3]*Math.sin(omega0*t) - (4.0*e0*Ic)/(hbar*hbar)*(c2*c2*var[13] + c1*c2*var[8] + c1*c2*var[10])*Math.cos(omega0*t);
        varDot[12] = 2.0*omega2*var[13] + (2.0*Ic*c2)/(hbar)*var[3]*Math.sin(omega0*t) + (4.0*e0*Ic)/(hbar*hbar)*(c2*c2*var[13] + c1*c2*var[8] + c1*c2*var[10])*Math.cos(omega0*t);
        varDot[13] = -2.0*omega2*var[12] - (2.0*Ic*c2)/(hbar)*var[2]*Math.sin(omega0*t) - (4.0*e0*Ic)/(hbar*hbar)*(c2*c2*var[12] + c2*c2*var[11] + c1*c2*var[7] + c1*c2*var[9])*Math.cos(omega0*t);
    }
}
