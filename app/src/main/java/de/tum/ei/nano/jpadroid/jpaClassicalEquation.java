package de.tum.ei.nano.jpadroid;

/**
 * Created by ga73sut on 12.04.2017.
 */

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class jpaClassicalEquation implements FirstOrderDifferentialEquations{

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
    private static final double hbar = h/(2*Math.PI);
    private static final double e0 = 1.60217662e-19;

    public jpaClassicalEquation(double omega1, double C1, double C2, double V0, double Ic) {

        this.C1 = C1;
        this.C2 = C2;
        this.V0 = V0;
        this.Ic = Ic;
        this.omega0 = 2.0*e0/hbar*this.V0;
        this.omega1 = omega1;
        this.omega2 = this.omega0 - this.omega1;
        this.L1 = 1.0/(this.C1*omega1*omega1);
        this.L2 = 1.0/(this.C2*omega2*omega2);
    }

    @Override
    public int getDimension() {

        return 4;
    }

    @Override
    public void computeDerivatives(double t, double[] var, double[] varDot) throws MaxCountExceededException, DimensionMismatchException {

        varDot[0] = -var[2]/L1 - Ic*Math.sin(omega0*t) - 2*e0*Ic/hbar*Math.cos(omega0*t)*(var[2]+var[3]);
        varDot[1] = -var[3]/L2 - Ic*Math.sin(omega0*t) - 2*e0*Ic/hbar*Math.cos(omega0*t)*(var[2]+var[3]);
        varDot[2] = var[0]/C1;
        varDot[3] = var[1]/C2;

    }
}
