package com.forbes.brian.utils.math.optimization;

import java.util.*;

import org.apache.commons.math3.analysis.MultivariateFunction;

/** Minimize
 * 		f(x)
 * 	subject to 
 * 		g_i(x) >= 0 
 * 		h_j(x) = 0
 *  */
public class PenaltyFunction implements MultivariateFunction {

	MultivariateFunction f; // function to be minimized
	List<MultivariateFunction> G; // inequality constraint function(s)
	List<MultivariateFunction> H; // equality constraint function(s)

	List<Double> alpha,beta; //weighting parameters for penality function

	public PenaltyFunction(MultivariateFunction objectiveFunction) {
		super();
		this.f = objectiveFunction;
		G = new ArrayList<MultivariateFunction>();
		H = new ArrayList<MultivariateFunction>();
		alpha = new ArrayList<Double>();
		beta = new ArrayList<Double>();
	}

	public void addPositiveConstraint(MultivariateFunction g, double alph){
		G.add(g);
		alpha.add(alph);
	}

	public void addEqualityConstraint(MultivariateFunction h, double bet){
		H.add(h);
		beta.add(bet);
	}

	@Override
	public double value(double[] x) {
		double val = f.value(x);

		for(int i = 0; i < G.size(); ++i)
			val += phi(alpha.get(i),- G.get(i).value(x)); // Put -G to get positivity

		for(int j = 0; j < H.size(); ++j){
			val += phi(beta.get(j),H.get(j).value(x));
			val += phi(beta.get(j), - H.get(j).value(x));
		}
		return val;
	}

	private static double phi(double lambda,double t){
		if( t < 0)
			return 0;
		return lambda*t*t;
	}

}