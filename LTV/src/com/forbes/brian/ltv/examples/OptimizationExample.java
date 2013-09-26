package com.forbes.brian.ltv.examples;


import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.*;
import org.apache.commons.math3.analysis.MultivariateFunction;

import java.util.Arrays;

import flanagan.math.Maximization;
import flanagan.math.MaximizationFunction;

public class OptimizationExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MultivariateFunction f =
			new MultivariateFunction(){
				@Override
				public double value(double[] z) {
				double val = Math.exp(-Math.abs(z[0] - 5.4)) * 
						Math.exp(-Math.abs(z[1] - 2.2)) + 1.1; 
				System.out.println(String.format("%f , %f , %f ",z[0],z[1],val));
				return val;
			}
		};
		
        SimplexOptimizer optimizerMult = new SimplexOptimizer(1e-3, 1e-6); 
        PointValuePair solutionMult = optimizerMult.optimize(new 
        		MaxEval(200), new ObjectiveFunction(f), GoalType.MAXIMIZE, new 
        		InitialGuess(new double[]{0, 0}), new MultiDirectionalSimplex(2)); 

        System.out.println("Min is: " + solutionMult.getValue() + 
        		"\tobtained at:" + Arrays.toString(solutionMult.getKey())); 
		

	}
	
	static class fun implements MaximizationFunction {
		
		@Override
		public double function(double[] z) {
			double val = Math.exp(-Math.abs(z[0] - 5.4)) * 
					Math.exp(-Math.abs(z[1] - 2.2)) + 1.1; 
			System.out.println(String.format("%f , %f , %f ",z[0],z[1],val));
			return val;
		}
	}

}
