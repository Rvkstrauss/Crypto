// CS-355, Spring 2015, Project ONE SOLUTIONS
package Asst1;
import java.util.Scanner;
import java.util.Date;					    

public class Date_and_Timer { 

  // *********** main **********************************************
  public static void main(String[] args)  {
	  System.out.println("Max long is:  "+ Long.MAX_VALUE);
	  System.out.println("Run this program first with smaller values and increase gradually by factors of 10");
  
	  Scanner kbd = new Scanner(System.in);
	  long n;
	  String ans;
	  long start, end;
    
	  Date d = new Date();
	  System.out.println(d);
    
   
	  System.out.print("Enter a long integer:  ");
	  n = kbd.nextLong();
    
	  while (n > 1)  {
		  start = System.currentTimeMillis();
		  doStuff(n);
		  end = System.currentTimeMillis();

		  System.out.println("# milliseconds:  " + (end - start));

		  System.out.print("Enter a long integer:  ");
		  n = kbd.nextLong();
	  }
    
	  System.out.println("Done with whole program.");
	  d = new Date();

	  System.out.println(d);
    
	  System.out.println("\nBye!");  
  } 
  // ************ end main **********************************************
  
  public static void doStuff(long n) {
        
    for (long i = 1; i <= n; i++);
    	System.out.println("done counting");        
  }
} // end class Date_and_Timer


