package com.forbes.brian.utils.math.optimization;

import org.apache.commons.math3.analysis.MultivariateFunction;

public class Constraints {

	/** Constrains ith variable to be greater than min value */
	public static class Postive implements MultivariateFunction {
		int idx;
		double minVal;
		public Postive(int i,double min){
			idx = i;
			minVal = min;
		}
		@Override
		public double value(double[] p) {
			return p[idx] - minVal;
		}
	}
	
	
	public static class UpperBound implements MultivariateFunction {
		int idx;
		double value;
		public UpperBound(int i,double value){
			idx = i;
			this.value = value;
		}
		@Override
		public double value(double[] p) {
			return value - p[idx];
		}
	}
	
	/** All variables should sum to one value */
	public static class Sum implements MultivariateFunction {
		int n;
		double val;
		public Sum(int varNumber, double value){
			n = varNumber;
			val = value;
		}
		@Override
		public double value(double[] x) {
			double sum = 0;
			for(int i = 0; i < n; ++i)
				sum += x[i];
			return sum - val;
		}
	}
	
	/** Makes close to the uniform distribution */
	public static class Uniform implements MultivariateFunction {
		int n;
		public Uniform(int varNumber){
			n = varNumber;
		}
		@Override
		public double value(double[] x) {
			double val = 0;
			for(int i = 0; i < n; ++i){
				val += (x[i] - 1./(double)n)*(x[i] - 1./(double)n);
			}
			return val;
		}
	}

	/*
	@Override
	public double value(double[] x) {
		double val = 0;
		for(int i = 0; i < n; ++i){
			val += Math.abs(x[i] - 1./n);
		}
		return val;
	}
	*/
	
}
