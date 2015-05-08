package Asst2_files;
// Rivka Strauss

import java.io.*;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RabinMillerTest{
	
	public static final int NUM_TRIALS = 30; 
	public static final BigInteger ZERO = new BigInteger("0");
	public static final BigInteger ONE = new BigInteger("1");
	public static final BigInteger TWO = new BigInteger("2");
	public static final BigInteger ONE_HUNDRED = new BigInteger("100");
	
	public static final int SHIFT = 54;
	
	public static final int PROBABLY_PRIME = 0;
	public static final int COMPOSITE_NO_FACTOR = 1;
	public static final int COMPOSITE_FACTOR_FOUND = 2;

	public static BigInteger factor;

	public static Scanner input;
    public static Scanner kbd = new Scanner(System.in);
	public static PrintWriter output;
		
//*************************************************************************************
	public static void main(String[] args) throws IOException{
		String test;
		BigInteger n;
		BigInteger e;
		BigInteger c;
		BigInteger d;
		BigInteger myN;
		String t;
		
		//testInputFile();
		generate_RSA_key();
		
		System.out.println("Would you like to encrypt or decrypt (Enter 'e' or 'd')");
		t = kbd.nextLine();
		if(t.equals("e")){    
			System.out.print("Enter String to convert to BigInteger:  ");
		    test = kbd.nextLine();
		    System.out.println("Enter recipient's value of n: ");
		    n = new BigInteger(kbd.nextLine());
		    System.out.println("Enter recipient's value of e");
		    e = new BigInteger(kbd.nextLine());
		    System.out.println("Your encrypted message reads: " + encrypt(n, e, test));    
		}else if(t.equals("d")){   
		    System.out.print("Enter the recieved message as BigInteger:  ");
		    c = new BigInteger(kbd.nextLine());
		    System.out.println("Enter your private key: ");
		    d = new BigInteger(kbd.nextLine());
		    System.out.println("Enter your public key: ");
		    myN = new BigInteger(kbd.nextLine());
		    System.out.println("The message you have decrypted reads: " + decrypt(c, d, myN));
		}else{
			System.out.println("Invalid");
		}
  			
   }
//**************************************************************************************
	public static BigInteger encrypt(BigInteger n, BigInteger e, String s){
		BigInteger m = new BigInteger("1");
		m = messageToBigInteger(s);
		BigInteger c = new BigInteger("0");	
		c = m.modPow(e, n);
		return c;
		
	}
//**************************************************************************************
	public static String decrypt(BigInteger c, BigInteger d, BigInteger n){
		BigInteger m = new BigInteger("0");
		m = c.modPow(d, n);
		String s = bigIntegerToMessage(m);
		return s;
	}
//*************************************************************************************
	
	public static BigInteger messageToBigInteger(String message){
		int i;
		String temp = "" ;	
		for (i = 0; i < message.length(); i++){
			temp += (int) (message.charAt(i)) - SHIFT;
		}
		return new BigInteger(temp);
	}			
		
//*************************************************************************************
	public static String bigIntegerToMessage(BigInteger m){
		char ch;
		String message = "";
		
		while (m.compareTo(ZERO) > 0){
			ch = (char)(m.mod(ONE_HUNDRED).intValue() + SHIFT);
			m = m.divide(ONE_HUNDRED);
			message = ch + message;
		}
		return message;
	}	
//*************************************************************************************	
// testInputFile--read numbers from file and call Rabin-Miller with many a's to test for primality.
	public static void testInputFile() throws IOException{
	  
	    int numBits = 0, trialNum = 0;
	    int [] results;
	    BigInteger n, a;
	    String base2Str;
		Random rand = new Random();

		System.out.print("Enter the name of the input file to test integers:  ");
		String inputFileName = kbd.nextLine();
	  
	    FileReader fReader = new FileReader(inputFileName);
		input = new Scanner(fReader);
			
		System.out.print("Enter the name of the output file for test results:  ");
		String outputFileName = kbd.nextLine();
		FileWriter fwriter = new FileWriter(outputFileName);
		output = new PrintWriter(fwriter);
			
		String line = input.nextLine();
		while (!line.equals("")){
				n = new BigInteger(line);   
				output.println();
				output.println("TESTING NEW integer: n = " + n);
	     
				base2Str = n.toString(2);
				numBits = base2Str.length();

				trialNum = 0;
				results = new int[3];

				while (trialNum < NUM_TRIALS){
					trialNum++;
					output.println();
					output.println("Trial # " + trialNum); 
					do {
						a = new BigInteger(numBits, rand);
						System.out.println(a);
					}while (a.compareTo(ONE) <= 0 || a.compareTo(n.subtract(ONE)) >= 0);
					System.out.println(RMTest(n, a, true));
					results[RMTest(n, a, true)]++;
				}
	      
				output.println();
				output.println("Summary for n = " + n);
				output.println("(out of " + NUM_TRIALS + " trials):");
				output.println("Probably Prime:  " + results[PROBABLY_PRIME]);
				output.println("Composite (no factor):  " + results[COMPOSITE_NO_FACTOR]);
				output.println("Composite (found):  " + results[COMPOSITE_FACTOR_FOUND]);
				output.print("Result:  ");
	      
				if (results[COMPOSITE_FACTOR_FOUND] > 0){
					output.println("This integer is composite, with factor: " + factor);
	        
				}else if (results[COMPOSITE_NO_FACTOR] > 0){
					output.println("This integer is composite, but no factors were found.");
	      
				}else{
					output.println("This integer is most probably prime.");
				}
				output.println();  
				output.println();
				output.println();
	          
				line = input.nextLine();
			}
	    
			System.out.println("Bye!");
	  
			output.close();    
	  	}// testInputFile()	
//*************************************************************************************
// This algorithm performs the Rabin-Miller test for integer n with value a, 1 < a < n-1
// If boolean "print" is true, use "output.println(....);" to print to output file
//  Otherwise, do not print to any file.
//
//  YOU WRITE THIS ONE!
		
	public static int RMTest(BigInteger n, BigInteger a, boolean print) {
	  	
		
		BigInteger k = ZERO, m = ZERO;
		for (BigInteger i = n.subtract(ONE); m.mod(TWO).equals(ZERO); k = k.add(ONE)){
			i = i.divide(TWO);
			m = i;
		}
		if(print){
			output.println("n = " + n);
			output.println("m = " + m);
			output.println("k = " + k);
			output.println("random a = " + a);
		}
		BigInteger b_curr = ZERO, b_prev;
		b_prev = b_curr;
		BigInteger i = ZERO;
		//begin testing
		b_curr = a.modPow(m, n);
		if(print){
			output.println("b_" + i + " = " + b_curr);
		}	
		if(b_curr.equals(ONE) || b_curr.equals(n.subtract(ONE))){
			if(print){
				output.println("probably prime");
			}
			return PROBABLY_PRIME;
		}
		i = i.add(ONE);
		
		while(i.compareTo(k.subtract(ONE)) < 0){
			b_prev = b_curr;
			b_curr = b_prev.modPow(TWO, n);
			if(print){
				output.println("b_" + i + " = " + b_curr);
			}
			if(b_curr.equals(n.subtract(ONE))){
				if(print){
					output.println("probably prime");
				}
				return PROBABLY_PRIME;
			}else if(b_curr.equals(ONE)){
				factor = b_prev.subtract(ONE).gcd(n);
				if(print){
					output.println("composite, factor found: " + factor);
				}
				return COMPOSITE_FACTOR_FOUND;
			}else{
				i = i.add(ONE);
			}
		}
		b_prev = b_curr;
		b_curr = b_prev.modPow(TWO, n);
		if(print){
			output.println("b_" + i + " = " + b_curr);
		}
		if(b_curr.equals(n.subtract(ONE))){
			if(print){
				output.println("probably prime");
			}
			return PROBABLY_PRIME;
		}else if(b_curr.equals(ONE)){
			factor = b_prev.subtract(ONE).gcd(n);
			if(print){
				output.println("composite, factor found: " + factor);
			}
			return COMPOSITE_FACTOR_FOUND;
		}else{
			if(print){
				output.println("composite, no factor found");
			}
			return COMPOSITE_NO_FACTOR;
		}
		
	} // end RMTest********************************************
		

//*************************************************************************************  
// generateRandomPrime -- YOU WRITE THIS ONE
	public static BigInteger generateRandomPrime(int numBits, Random rand, boolean print) {
	    
	    String base2String = "";
	    BigInteger result, a;
	    int rm = 1;
	    int trialNum = 0;   
	    
	    do{
		   	result = new BigInteger(numBits, rand);
		   	base2String = result.toString(2);
	    }while(base2String.length() < numBits);
		   	
		if (result.mod(TWO).compareTo(ZERO) == 0){
			result = result.add(ONE);
		}	
		    
		while(trialNum < NUM_TRIALS || rm != 0){
		   	trialNum++;
		    System.out.println(result);
		    do {
		    	a = new BigInteger(numBits, rand);
		    }while (a.compareTo(ONE) <= 0 || a.compareTo(result.subtract(ONE)) >= 0);
		    rm = RMTest(result, a, print);
		    System.out.println(rm);
		    if(rm >= 1){
		    	result = result.add(TWO);
		    	trialNum = 0;
		    }else{
		    	trialNum = 30;
		    }
		}	
		return result;		   
}
//*************************************************************************************
		public static void generate_RSA_key() throws IOException {
		  
		    Random r = new Random();
			BigInteger p = ZERO, q = ZERO, e = ZERO, d = ZERO, m = ZERO, n = ZERO, phi_n = ZERO;
		    boolean print = false;
		 
			System.out.print("Now, for generate_RSA_key, please enter the name of the output file:  ");
			String outputFileName = kbd.nextLine();
			FileWriter fwriter = new FileWriter(outputFileName);
			output = new PrintWriter(fwriter);
		    output.println();
		    
		 	p = generateRandomPrime(1200, r, print);
		 	output.println("p: " + p);
		 	q = generateRandomPrime(1500, r, print);
		 	output.println("q: " + q);
		 	n = p.multiply(q);
		    output.println("n: " + n);
		    phi_n = p.subtract(ONE).multiply(q.subtract(ONE));
		    output.println("phi_n: " + phi_n);
		    
		    do{
		    	e = generateRandomPrime(500, r, print);
		    }while(e.gcd(phi_n).compareTo(ONE) != 0);
		    
		    output.println("e: " + e);
		    d = e.modInverse(phi_n);
		    output.println("d: " + d);
		    						
		    output.close();
	  } // end generate_RSA_key
		


} // end class
