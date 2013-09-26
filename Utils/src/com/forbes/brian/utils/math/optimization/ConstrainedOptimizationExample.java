package com.forbes.brian.utils.math.optimization;

import java.util.Arrays;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.MultiDirectionalSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.PowellOptimizer;

/** 
 * Minimize
 * 		f(x,y) = x^2 + y^2
 * subject to inequality constraint
 * 		6 - x - 2*y <= 0
 * 	and equality constraint
 * 		3 - x + y = 0
 * 
 * 
 * Real solution is
 * 	x = 4, y = 1
 * 
 */
public class ConstrainedOptimizationExample {

	public static void main(String[] args) {

		basic();
		// uniform();
	}
	
	
	static double n = 12;
	static void uniform(){
		// Function to be minimized
		MultivariateFunction f =new Constraints.Uniform((int)n);

		ConstrainedOptimizer co = new ConstrainedOptimizer(f,(int)n,GoalType.MINIMIZE);

		// co.addEqualityConstraint(new Constraints.Sum((int) n, 1));
		
		for(int i = 0 ; i < n; ++i)
			co.addPositiveConstraint(new Constraints.Postive(i,.1));
		
		co.optimize();
	}
	
	static void basic(){
		// Function to be minimized
		MultivariateFunction f =
				new MultivariateFunction(){
			@Override
			public double value(double[] x) {
				return x[0]*x[0] + x[1]*x[1];
			}
		};
		// Inequality constraint function. This is less than or equal to zero
		MultivariateFunction g =
				new MultivariateFunction(){
			@Override
			public double value(double[] x) {
				return -( 6- x[0] - 2*x[1] );
			}
		};

		// Equality constraint function
		MultivariateFunction h =
				new MultivariateFunction(){
			@Override
			public double value(double[] x) {
				return 3 - x[0] + x[1];
			}
		};
		ConstrainedOptimizer co = new ConstrainedOptimizer(f,2,GoalType.MINIMIZE);
		co.addEqualityConstraint(h);
		co.addPositiveConstraint(g);
		
		co.setMaxIter(15);
		co.setMaxEval(200);
		co.optimize();
	}

}
