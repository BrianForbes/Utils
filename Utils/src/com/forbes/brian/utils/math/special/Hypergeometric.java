package com.forbes.brian.utils.math.special;

public class Hypergeometric {

	
	public static void main(String[] args) throws Exception {
		System.out.println( Hypergeometric.Gauss(.3, 2, 1.2, .75) );
	}
	
	public static double Gauss(double a,double b,double c,double z) throws IllegalArgumentException {
		if(Math.abs(z) >= 1)
			return Double.NaN;
		if (a<=0 | b<=0 | c<=0 | z<=0) return Double.NaN;
		final double EPS = 1E-13;
		double j = 0;
		double uj = 1;
		double y = uj;
		double diff = 1;
		while(diff > EPS){
			double lastY = y;
			++j;
			uj = uj*(a+j-1)*(b+j-1)*z / (c+j-1) / j;
			y += uj;
			diff = Math.abs(lastY - y);
		}
		return y;
	}
	
}
