package com.forbes.brian.ltv.functions;

public class Params {
	final double alpha,beta,r,s;
	public Params(Params that){
		this.alpha = that.alpha;
		this.beta = that.beta;
		this.r = that.r;
		this.s = that.s;
	}
	private Params(double alpha, double beta, double r, double s) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.r = r;
		this.s = s;
	}
	public static class Builder{
		double alpha,beta,r,s;
		public Builder alpha(double a){alpha = a; return this;}
		public Builder beta(double a){beta = a; return this;}
		public Builder r(double a){r = a; return this;}
		public Builder s(double a){s = a; return this;}
		public Params build(){
			return new Params(alpha,beta,r,s);
		}
	}
	@Override
	public String toString() {
		return "Params [alpha=" + alpha + ", beta=" + beta + ", r=" + r
				+ ", s=" + s + "]";
	}
	
	
}
