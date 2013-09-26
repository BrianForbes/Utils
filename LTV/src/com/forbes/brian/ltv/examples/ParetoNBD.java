package com.forbes.brian.ltv.examples;

import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.*;
import org.apache.commons.math3.analysis.MultivariateFunction;

import jsat.math.optimization.NelderMead;

import java.io.BufferedReader;
import java.text.ParseException;
import java.util.*;

import static com.forbes.brian.ltv.functions.Functions.*;

import com.forbes.brian.ltv.functions.Functions;
import com.forbes.brian.ltv.functions.Params;
import com.forbes.brian.utils.io.*;
import com.forbes.brian.utils.math.optimization.ConstrainedOptimizer;
import com.forbes.brian.utils.math.optimization.Constraints;
import com.forbes.brian.utils.math.special.Hypergeometric;
import com.forbes.brian.utils.misc.Pair;
import com.forbes.brian.utils.misc.TimeUtils;

import flanagan.math.Maximization;
import flanagan.math.MaximizationFunction;

import java.sql.Date;
/**       ID     date   Num.purchased    total value            */
// 00004 0001 19970118  2                29.73

//(0.5534 , 10.58 ,  0.6059 , 11.66) -9595   | Solution parameters and log-likelihood


public class ParetoNBD {

	Data data;
	Date splitDate;


	public ParetoNBD(Date splitDate) throws Exception {
		this.splitDate = splitDate;
		List<Record> records = new Loader().getRecords();
		data = new Data(records,splitDate);
	}


	public static void main(String[] args) throws Exception {
		Date splitDate = new Date(TimeUtils.getTimestamp(1997, 9, 30).getTime());

		/*
		double x = 26;
		double tx =  30.86;
		double T = 31;
		double period = 52;
		*/

		ParetoNBD pnb = new ParetoNBD(splitDate); 

		pnb.run();

	}

	private MultivariateFunction getLogLik(){
		return 	
				new MultivariateFunction(){
			@Override
			public double value(double[] z) {
				double val = data.logLik(z);
				// System.out.println(val);
				return val;
			}
		};
	}

	
	
	private void run()throws Exception {
		int custNo = 1516;
		User u = data.trainUser(custNo);

		double x = data.getX(u);
		double tx = data.getTx(u);
		double T = data.getT();
		
		System.out.println(x);
		System.out.println(tx);
		System.out.println(T);
		
		System.out.println(Functions.likelihood(new double [] {10.5802,11.6562,.5534,.6061}, x, tx, T));
		System.out.println(Functions.likelihood(Functions.OPT_PARAMS, x, tx, T));
		
		MultivariateFunction f = getLogLik();

		System.out.println(f.value(new double [] {1,1,1,1}));
		
		System.out.println(f.value(new double [] {10.5802,11.6562,.5534,.6061}));
		
		ConstrainedOptimizer co = new ConstrainedOptimizer(f,4,GoalType.MAXIMIZE);
		for(int i = 0; i < 4; ++i)
			co.addPositiveConstraint(new Constraints.Postive(i, 0));
		
		//co.addPositiveConstraint(new Constraints.UpperBound(1, 100));
		
		co.setInitialGuess(new double [] {1,1,1,1});
		co.setMaxEval(2000);
		co.setEps(1);
		
		co.optimize();
		/*
		Params PARAMS =  
				new Params.Builder()
		.alpha(2)
		.beta(1)
		.r(5)
		.s(1).build();
		System.out.println(data.logLik(PARAMS));



		InitialGuess ig = new InitialGuess(new double[]{10, .2,1,5});

		for(int i = 0; i < 5; ++i){

			PowellOptimizer optimizerMult = new PowellOptimizer(1e-2, 1e-2); 
			PointValuePair solutionMult = optimizerMult.optimize(new 
					MaxEval(100), new ObjectiveFunction(f), GoalType.MAXIMIZE, ig
					, new MultiDirectionalSimplex(4)); 

			System.out.println("Min is: " + solutionMult.getValue() + 
					"\tobtained at:" + Arrays.toString(solutionMult.getKey())); 
			ig = new InitialGuess(solutionMult.getKey());
		}
		*/
		
		/*
		Likelihood lik = new Likelihood();

		Maximization max = new Maximization();
		// constrain maximization
		for(int i = 0; i < 4; ++i){
			int [] pIndices = {i};
			double [] plusOrMinus = {1};
			int dir = -1;
			double constraint = 0;
			max.addConstraint(pIndices, plusOrMinus, dir, constraint);
		}

		// initial state

		double[] z = {1,1,1,1}; // Initial point
		double[] step = new double[z.length];
		double stepSize = .1;
		Arrays.fill(step, stepSize);
		double ftol = 0.1;
		int maxIter = 1000;

		max.nelderMead(lik, z, step, ftol, maxIter);
		boolean converged = max.getConvStatus();

		System.out.println( max.getMaximum());
		while(!converged){

			double res = max.getMaximum();
			double [] w = max.getParamValues();
			for(int i = 0; i < 4; ++i) z[i] = Math.abs(w[i]);
			if(Double.isNaN(res)){
				for(int i = 0; i < 4; ++i) z[i] = Math.abs(w[i]);
				//step = new double[z.length];
				//stepSize *= .5;
				//Arrays.fill(step, stepSize);
				// System.out.println(String.format("%f %f %f %f", w[0],w[1],w[2],w[3]));
				// break;
			}
			else
				z = w;
			 System.out.println(String.format("%f %f %f %f", z[0],z[1],z[2],z[3]));
			// z = max.getParamValues(); 

			System.out.println( res );
			//start = z;
			max.nelderMead(lik, z, step, ftol, maxIter);
			converged = max.getConvStatus();
			// break;
		}
		System.out.println(String.format("%f %f %f %f", z[0],z[1],z[2],z[3]));
		 */
		/*
		double [] z = max.getParamValues(); 
		double res = max.getMaximum();
		System.out.println(max.getConvStatus());

		System.out.println(String.format("%f %f %f %f", z[0],z[1],z[2],z[3]));
		System.out.println(res);

		// Run one more time
		start = z;

		max.nelderMead(lik, start, step, ftol, maxIter);

		max.nelderMead(lik, start, step, ftol, maxIter);

		z = max.getParamValues(); 
		res = max.getMaximum();

		System.out.println(String.format("%f %f %f %f", z[0],z[1],z[2],z[3]));
		System.out.println(res);
		 */
		/*
		Params p = null;
		double min = -1E20;

		double [] last = start;
		for(int j = 0; j < 30; ++j){
			max.nelderMead(lik, start, step, ftol, maxIter);
			double [] z = max.getParamValues(); 
			double res = max.getMaximum();
			if(!Double.isNaN(res) && res > min && z[0] > 0 && z[1] > 0 && z[2] > 0 &&z[3] > 0 ){
				start = z;
				min = res;
				p = lik.toParams(z);

				System.out.println(max.getParamValues()[0]);
				System.out.println(max.getParamValues()[1]);
				System.out.println(max.getParamValues()[2]);
				System.out.println(max.getParamValues()[3]);
			}
			else{
				step = new double[start.length];
				stepSize *= .5;
				Arrays.fill(step, stepSize);
			}
			//else
			//	start = last;


		}
		System.out.println(min);
		System.out.println(p);
		 System.out.println( data.logLik(p)  );
		 */
		// result of maximization
		//double result = max.getMaximum()
		// System.out.println( data.logLik(PARAMS)  );
		// System.out.println( data.logLik(OPT_PARAMS)  );



		/*
		System.out.println(expectation(OPT_PARAMS,data.getX(u),data.getTx(u),data.getT(), 52));
		System.out.println(probActive(OPT_PARAMS,data.getX(u),data.getTx(u),data.getT()));
		System.out.println(likelihood(OPT_PARAMS,data.getX(u),data.getTx(u),data.getT()));
		 */


	}



}



/*



	private void initUsers() throws Exception {
		users = new HashMap<Integer,User>();
		for(Record r : train){
			int id = r.getId();
			User u = users.get(id);
			if(u == null) u = new User(id);
			u.add(r);
			users.put(id, u);
		}
	}

	public double toDouble(Date date){
		return dates.indexOf(date);
	}

	private void initDates(){
		Set<Date> dateSet = new HashSet<Date>();
		for(Record r : data)
			dateSet.add(r.getDate());
		dates = new ArrayList<Date>(dateSet);
		Collections.sort(dates);
	}




private void optimize(){
	// NelderMead nm = new NelderMead();




	Maximization max = new Maximization();

	MaximizationFunction f = new Likelihood();

	double[] start = {1,1,1,.5};
	double[] step = new double[start.length];
	Arrays.fill(step, .01);
	// convergence tolerance
	double ftol = 0.01;

	// maximal number of iterations
	int maxIter = 5000;

	// Nelder and Mead maximisation procedure
	max.nelderMead(f, start, step, ftol, maxIter);

	// result of maximization
	double result = max.getMaximum();

	System.out.println(result);

}
 */

/*
class Likelihood implements MaximizationFunction {
	@Override
	public double function(double[] z) {
		double sum = 0;
		double T = dates.size() - 1;
		for(User u : users.values()){
			double tx = toDouble(u.lastDate());
			double x = u.getTrans().size();//u.getTotalTrans();
			double lik = Functions.likelihood(z[0], z[1], z[2],.6061, x, tx, T);
			if(Double.isNaN(lik))
				continue;
			sum += Math.log(lik);
		}
		System.out.println(String.format("%f , %f, %f",z[0],z[1],z[2]) +" -> " + sum);
		return sum;
	}
}

public double logLik(double alpha,double r,double beta,double s){
	double sum = 0;
	double T = dates.size() - 1;
	for(User u : users.values()){
		double tx = toDouble(u.lastDate());
		double x = u.getTotalTrans();
		double lik = Functions.likelihood(alpha, r, beta, s, x, tx, T);
		if(Double.isNaN(lik))
			continue;
		// System.out.println(lik + " | " + Math.log(lik));
		sum += Math.log(lik);
	}
	System.out.println(String.format("%f , %f, %f, %f",alpha,r,beta,s) +" -> " + sum);
	return sum;
}
 */

