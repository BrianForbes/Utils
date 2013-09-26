package com.forbes.brian.ltv.functions;

import java.sql.Date;
import org.apache.commons.math3.special.Gamma;

import com.forbes.brian.utils.misc.TimeUtils;

import static com.forbes.brian.utils.math.special.Hypergeometric.Gauss;;

/**
 *  x = # transactions for a given customer in time interval [0,T]
 *  tx = time of most recent transaction of a given customer.
 *  	NOTE : for e.g. the 3rd day of the 30th week, tx = 30.43
 *  T = right endpoint of time period  
 *  a,r,b,s are the parameters we want to estimate.  
 *    */
public class Functions {

	public static final Params OPT_PARAMS =  
					new Params.Builder()
							.alpha(10.5802)
							.beta(11.6562)
							.r(.5534)
							.s(.6061).build();
	
	
//	[33.50569620750414, 818.409280822687, 2.3819660069529474, 1.4084593641754295]
	
	public static final Params params2 =  
			new Params.Builder()
					.alpha(33.505696207504)
					.beta(818.409280)
					.r(2.3819660)
					.s(1.408459364).build();
	
	//[11.968393153376928, 174.1867713123818, 1.5898913038967644, 3.657223778325108]
	
	public static void main(String [] args){
		//double [] p = {10.58,.5534,  11.66,.6061};

		double x = 26;
		double tx =  30.86;
		double T = 31;
		double period = 52;

		
		System.out.println(expectation(OPT_PARAMS,x,tx,T,  period));
		// Expected purchase number in 52 weeks = 1 year
		System.out.println(expectation(params2,x,tx,T,  period));
		// Probability of being 'active', or alive
		System.out.println(probActive(params2,x,tx,T));
		
		/** NEXT CHALLENGE : Get these variables, x,tx,T, from the data itself.
		 * The above are for user 1516. */
	
	}
	
	
	public static double likelihood(
			double [] params, // Pareto NBD model parameters
			double x,double tx,double T){
		double alpha = params[0];
		double beta = params[1];
		double r = params[2];
		double s = params[3];
		
		if (s<=0 | beta<=0 | r<=0 | alpha<=0) return Double.NaN;
		
		double maxab =  Math.max(alpha, beta);
		double absab = Math.abs(alpha - beta);
		double param2 = s+1;
		if(alpha < beta)
			param2 = r+x;

		double f1 = 0, f2 = 0;

		if(absab == 0){
			f1 = 1.0/ Math.pow( maxab + tx, r + s + x );
			f2 = 1.0/ Math.pow( maxab + T , r + s + x );
		}
		else{
			f1 = Gauss(r+s+x, param2, r+s+x+1, absab /(maxab + tx) ) ;
			f1 /= Math.pow(maxab + tx, r + s + x);
			f2 = Gauss(r+s+x, param2, r+s+x+1, absab /(maxab + T) ) ;
			f2 /= Math.pow(maxab + T, r + s + x);
		}

		double g1 = (Math.pow(alpha,r)*Math.pow(beta,s)/Gamma.gamma(r))*Gamma.gamma(r+x);

		double g2 = 1.0 /(Math.pow(alpha+T,r+x)*Math.pow(beta+T,s));

		double f = g1*(g2 + (f1 - f2)*s/(r + s + x));

		return f;
	}
	
	/*
	public static double toDouble(Date date){
		return (TimeUtils.getDayOfYear(date) / 7.0 );
	}
	*/
	
	public static double expectation(Params p,double x,double tx,double T, double t){
		  double tmp1 = (p.r+x) * (p.beta+T) / ((p.alpha+T) * (p.s-1));
		  double tmp2 = Math.pow((p.beta+T) / (p.beta+T+t),p.s-1);
		  double ce = tmp1 * (1-tmp2) * probActive(p,x,tx, T);
		  return ce;
		}

	public static double probActive(Params p,double x,double tx,double T){
		double maxab = Math.max(p.alpha,p.beta);
		double absab = Math.abs(p.alpha-p.beta);
		double param2 = p.s+1;
		if (p.alpha < p.beta) 
			param2 = p.r + x;

		double F0 = Math.pow(p.alpha + T , p.r + x)*Math.pow(p.beta + T,p.s);

		double F1 = Gauss(p.r+p.s+x, param2, p.r+p.s+x+1, absab / (maxab+tx)) / (Math.pow(maxab+tx,p.r+p.s+x));
		double F2 = Gauss(p.r+p.s+x, param2, p.r+p.s+x+1, absab / (maxab+T)) / (Math.pow(maxab+T,p.r+p.s+x));
		double pactive = 1 / (1 + (p.s / (p.r+p.s+x)) * F0 * (F1-F2));

		return pactive;

	}

	public static double likelihood(
			Params p,
			double x,double tx,double T){
		if (p.s<=0 | p.beta<=0 | p.r<=0 | p.alpha<=0) return Double.NaN;
		
		double maxab =  Math.max(p.alpha,p.beta);
		double absab = Math.abs(p.alpha-p.beta);
		double param2 = p.s+1;
		if(p.alpha < p.beta)
			param2 = p.r+x;

		double f1 = 0, f2 = 0;

		if(absab == 0){
			f1 = 1.0/ Math.pow( maxab + tx, p.r + p.s + x );
			f2 = 1.0/ Math.pow( maxab + T , p.r + p.s + x );
		}
		else{
			f1 = Gauss(p.r+p.s+x, param2, p.r+p.s+x+1, absab /(maxab + tx) ) ;
			f1 /= Math.pow(maxab + tx, p.r + p.s + x);
			f2 = Gauss(p.r+p.s+x, param2, p.r+p.s+x+1, absab /(maxab + T) ) ;
			f2 /= Math.pow(maxab + T, p.r + p.s + x);
		}

		double g1 = (Math.pow(p.alpha,p.r)*Math.pow(p.beta,p.s)/Gamma.gamma(p.r))*Gamma.gamma(p.r+x);

		double g2 = 1.0 /(Math.pow(p.alpha+T,p.r+x)*Math.pow(p.beta+T,p.s));

		double f = g1*(g2 + (f1 - f2)*p.s/(p.r + p.s + x));

		return f;
	}



}
