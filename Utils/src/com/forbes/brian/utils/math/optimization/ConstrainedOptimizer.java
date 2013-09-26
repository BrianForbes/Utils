package com.forbes.brian.utils.math.optimization;

import java.util.*;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.SimplePointChecker;
import org.apache.commons.math3.optim.SimpleValueChecker;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.MultiDirectionalSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.PowellOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;

public class ConstrainedOptimizer {

	private int maxIter = 5;
	private int maxEval = 200;
	private double eps = 1E-3;
	
	private MultivariateFunction f;
	private List<MultivariateFunction> G;
	private List<MultivariateFunction> H;
	
	private int varNumber; // Number of variables in input functions
	
	private boolean optimized;
	private double [] min;
	private double minValue;
	
	private GoalType goal;
	
	private double [] initialGuess;
	
	/** Case of a single equality and inequality constraint */
	public ConstrainedOptimizer(MultivariateFunction objectiveFunction, int varNumber, GoalType goal){
		this.f = objectiveFunction;
		this.varNumber = varNumber;
		this.goal = goal;
		initialGuess = new double[varNumber];
		
		G = new ArrayList<MultivariateFunction>();
		H = new ArrayList<MultivariateFunction>();
		
		optimized = false;
	}
	
	public void addPositiveConstraint(MultivariateFunction g){
		G.add(g);
	}
	
	public void addEqualityConstraint(MultivariateFunction h){
		H.add(h);
	}
	
	
	
	public int getMaxIter() {
		return maxIter;
	}

	public void setMaxIter(int maxIter) {
		this.maxIter = maxIter;
	}

	public int getMaxEval() {
		return maxEval;
	}

	public void setMaxEval(int maxEval) {
		this.maxEval = maxEval;
	}

	public double getEps() {
		return eps;
	}

	public void setEps(double eps) {
		this.eps = eps;
	}

	public double [] getPoint(){
		if(!optimized){
			System.out.println("Please call optimize() first.");
			System.exit(1);
		}
		return min;
	}
	
	public double getValue(){
		if(!optimized){
			System.out.println("Please call optimize() first.");
			System.exit(1);
		}
		return minValue;
	}

	public void optimize() throws IllegalArgumentException {
		optimize(initialGuess); 
	}
	
	public void setInitialGuess(double [] guess) throws IllegalArgumentException {
		if(guess.length != varNumber)
			throw new IllegalArgumentException("Initial guess dimension must equal " + varNumber);
		initialGuess = guess;
	}
	
	private PenaltyFunction getObjectiveFunction(double alpha, double beta){
		PenaltyFunction objFun = new PenaltyFunction( f);
		for(MultivariateFunction g : G)
			objFun.addPositiveConstraint(g, alpha);
		for(MultivariateFunction h : H)
			objFun.addEqualityConstraint(h, beta);
		return objFun;
	}
	
	private void optimize(double [] initialGuess) throws IllegalArgumentException {
		if(initialGuess.length != varNumber)
			throw new IllegalArgumentException("Initial guess must have dimension " + varNumber);
		String name = goal.toString().substring(0, 3).toLowerCase();
		
		double alpha = 1;
		double beta = 1;

		PointValuePair solutionMult = null;
		
		for(int iter = 0; iter < maxIter; ++iter){
			System.out.print(iter + " .. ");
			PenaltyFunction objFun = getObjectiveFunction(alpha,beta);
			
			MultivariateOptimizer optimizerMult =
							new PowellOptimizer(eps, eps); 
							// new SimplexOptimizer(eps, eps); 
			//SimplexOptimizer optimizerMult = new SimplexOptimizer(EPS, EPS); 
			solutionMult = optimizerMult.optimize(
					new MaxEval(maxEval), new ObjectiveFunction(objFun), goal, 
					new InitialGuess(initialGuess), new MultiDirectionalSimplex(varNumber)); 

			double [] X = solutionMult.getKey();
			// Set new initial guess to previous solution, and make parameters larger
			for(int i = 0; i < varNumber; ++i)
				initialGuess[i] = X[i];

			alpha *= 10;
			beta *= 10;
			
			System.out.println(name +" is: " +solutionMult.getValue() + 
					"\nobtained at :  " + Arrays.toString(solutionMult.getKey()));
		}
		System.out.println();
		min = solutionMult.getKey();
		minValue = solutionMult.getValue();
		
		System.out.println(name +" is: " +minValue + 
				"\nobtained at :  " + Arrays.toString(min));
		optimized = true;
	}

}
