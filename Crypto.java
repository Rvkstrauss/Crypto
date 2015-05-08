package Asst1;
import java.util.Scanner;
import java.math.BigInteger;
import java.util.Date;



public class Crypto{
	
	public static void main (String[] args){
		
		Scanner kbd = new Scanner(System.in);
		Date d = new Date();
		System.out.println(d);
		
		
		System.out.println("Testing Problem 1a--primeFactorization: \n");
		long n;
		
		do{
			System.out.println("Enter a long integer: ");
			n = kbd.nextLong();
			if(n > 1)
				System.out.println("The prime factorization of " + n +  " is: " + primeFactorization(n));
		}while (n > 1);
		
		System.out.println("Testing Problem 1b--extendedEuclidGCD: \n");
		
		long a,b;
		long [] xy = new long[2];
		do{	 		  
		    System.out.println("Enter a: ");
			a = kbd.nextLong();
			if (a > 1){
				System.out.println("Enter b: ");
				b = kbd.nextLong();
				long gcd = extendedEuclideanGCD(a,b,xy);
				System.out.println("gcd(" + a + ", " + b + ") = " + gcd 
			    		+ ", and " + gcd + " = (" + xy[0] + ")(" 
			    		+ a + ") + (" + xy[1] + ")(" + b + ").\n");
			}
		}while(a > 1);
		
		System.out.println("Testing Problem 1c--BigInteger Euclid GCD: \n");
		BigInteger c; 
		BigInteger e; 
		BigInteger [] vz = new BigInteger [2];
		
		do{	 		  
			System.out.println("Enter c: ");
			c = kbd.nextBigInteger();
			if (c.compareTo(BigInteger.ONE) > 0){
				System.out.println("Enter e: ");
				e = kbd.nextBigInteger();
				BigInteger gcd = bigIntegerEuclideanGCD(c,e,vz);
				System.out.println("gcd(" + c + ", " + e + ") = " + gcd 
			    		+ ", and " + gcd + " = (" + vz[0] + ")(" 
			    		+ c + ") + (" + vz[1] + ")(" + e + ").\n");
			}
		}while(c.compareTo(BigInteger.ONE) > 0);
		
		
		System.out.println("Bye!");
	}	
	
	
	public static String primeFactorization(long n){
		
		int count;
		long start, end;
		String output = "";
		start = System.currentTimeMillis();
		for	(int prime = 2; prime <= n; prime++){
			for (count = 0; n % prime == 0; count++){
				n = n / prime;
			}
			if (count >= 1)
				output = output + "(" + prime + "^" + count + ")";
				
		}
		end = System.currentTimeMillis();
		System.out.println("# milliseconds:  " + (end - start));
		return output + "\n";
	}
	
	public static long extendedEuclideanGCD(long a, long b, long [] xy){
		long r;
		long temp = 0;
		long [] q = {a,b};
		long [] x = {1,0};
		long [] y = {0,1};
		while(q[0] % q[1] != 0 && q[1] != 0){
			temp = q[0] % q[1];
			r = q[0] / q[1];
			q[0] = q[1];
			q[1] = temp;
			if(q[1] != 0){
				temp = x[0] - (x[1] * r);
				x[0] = x[1];
				x[1] = temp; 
				temp = y[0] - (y[1] * r);
				y[0] = y[1];
				y[1] = temp; 
			}
		}
		xy[0] = x[1];
		xy[1] = y[1];
	    return q[1];
 		
		
	}
	public static BigInteger bigIntegerEuclideanGCD(BigInteger c, BigInteger e, BigInteger [] vz){
		BigInteger r, temp;
		BigInteger [] q = {c, e};
		BigInteger [] x = {BigInteger.ONE, BigInteger.ZERO};
		BigInteger [] y = {BigInteger.ZERO, BigInteger.ONE};
		while(q[0].mod(q[1]) != BigInteger.ZERO && q[1] != BigInteger.ZERO ){
			temp = q[0].mod(q[1]);
			r = q[0].divide(q[1]);
			q[0] = q[1];
			q[1] = temp;
			if(q[1] != BigInteger.ZERO){
				temp = x[0].subtract((x[1].multiply(r)));
				x[0] = x[1];
				x[1] = temp; 
				temp = y[0].subtract((y[1].multiply(r)));
				y[0] = y[1];
				y[1] = temp; 	
			}
		}
		vz[0] = x[1];
		vz[1] = y[1];
	    return q[1];
	}
}
