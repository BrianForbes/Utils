package com.forbes.brian.ltv.functions;

import org.apache.commons.math3.linear.*;

import cc.mallet.optimize.*;

public class LikelihoodOptimizer 
	implements Optimizable.ByGradientValue, Optimizable.ByGradient {

	private final Optimizer opt;
	
	private final RealVector params;
	/*
	private final RealMatrix xMatrix;
	private final RealVector yVector;
	private final RealVector wVector;
	private final int localRows;
	private final RealVector betas;
	*/
	//private double reg;
	//private boolean isFitted;


	public LikelihoodOptimizer(RealVector params){	
		this.params = params;
		opt = new LimitedMemoryBFGS(this);
		/*
		betas = new ArrayRealVector(columns);
		xMatrix = new BlockRealMatrix(rows, columns);
		yVector = new ArrayRealVector(outcomes.toArray(new Double[outcomes.size()]));
		wVector = new ArrayRealVector(weights.toArray(new Double[weights.size()]));
		
		
		localRows = rows;
		isFitted = false;

		for(int row=0;row<localRows;row++){
			xMatrix.setRowVector(row, variates.get(row));
			yVector.setEntry(row, outcomes.get(row));
		}
		*/
	}

	/**Fit the model with the given regularization parameter. Higher
	 * regularization => smaller-in-magnitude parameters.*/
	public void optimize() {
		opt.optimize();
	}

	/**@return The (probably fitted) beta parameters.*/
	public RealVector getParams() {
		return params;
	}

	@Override
	public int getNumParameters() {
		return params.getDimension();
	}

	@Override
	public double getParameter(int index) {
		return params.getEntry(index);
	}
	@Override
	public void getParameters(double[] buffer) {
		for (int i=0;i<buffer.length;i++) buffer[i] = params.getEntry(i);
	}
	@Override
	public void setParameter(int index, double val) {
		params.setEntry(index, val);
	}
	@Override
	public void setParameters(double[] buffer) {
		for (int i=0;i<buffer.length;i++) params.setEntry(i, buffer[i]);
	}

	@Override
	public double getValue() {
		/*
		double accum = 0;
		for(int row=0;row<localRows;row++){
			RealVector x = xMatrix.getRowVector(row);
			double y = yVector.getEntry(row);
			double w = wVector.getEntry(row);
			double mu = betas.dotProduct(x);
			
			// Need to handle values larger than double range.
			double exp = Math.exp(mu);
			if(Double.isInfinite(exp))
				throw new RuntimeException("Poisson iteration returned infinite value.");
			
			accum += w * (-Math.exp(mu) + y * mu); //omits "- Gamma.logGamma(1+y)" as it's constant
			
		}
		double result = accum - reg / 2 * betas.dotProduct(betas);
		return result;
		*/
		return 0;
	}
	
	@Override
	public void getValueGradient(double[] buffer) {
		/*
		RealVector accum = betas.mapMultiply(-reg);//negative sign on regularization to subtract
		for (int row=0;row<localRows;row++){
			RealVector x = xMatrix.getRowVector(row);
			double y = yVector.getEntry(row);
			double w = wVector.getEntry(row);
			// if these represent population slices, need to multiply by w
			accum = accum.add(x.mapMultiply(w * (y - Math.exp(betas.dotProduct(x)))));
		}
		for(int i=0;i<buffer.length;i++) buffer[i] = accum.getEntry(i);
		*/
	}
}