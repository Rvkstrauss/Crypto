package Asst_3;
// My name is: Rivka Strauss

// Project3, CS-355, spring 2015

// You write verifyHamCycle
// You write verifyIsomorphism
// You write randomPermutation
// You write permuteGraph
// You write permuteHamCycle
// You write findHamCycle -- use nextPerm

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Project3_new_ZKP {
 
	public static Scanner inputA, inputB, inputC, inputD;
	public static Scanner kbd = new Scanner(System.in); 
	public static Random rand = new Random();	
	
	//**************************************************************************************
	public static boolean isPerm(int [] c){
		int count;
		for(int i = 0; i < c.length; i++){
			count = 0;
			for(int j = 0; j < c.length; j++){  
				if (c[j] == i){
				  count++;
				}
			}
			if (count != 1){
				return false;
			}
		}
		return true;
	}
	
//**************************************************************************************
	
  public static boolean verifyHamCycle(boolean [][] G, int [] c, int n) {
	  if(!isPerm(c)){
		  return false;
	  }
	  for (int i = 0; i < c.length; i++){
		  if(i == c.length - 1){
			  if(G[c[i]][c[0]] != true ){
				  return false;
			  }
		  }
		  else{
			  if (G[c[i]][c[i+1]] != true){
				  return false;
			  }
		  }
	  }
	  return true;
  }
  
//**************************************************************************************
  public static boolean verifyIsomorphism(boolean [][] G, boolean [][] H, int [] a, int n) {
	  
	  for(int row = 0; row < n; row++){
		  for(int col = 0; col < n; col++){
			  if (G[row][col] != H[a[row]][a[col]]){
				  return false;
			  }
		  }
	  }
	  return true;
  }  
//**************************************************************************************
  public static int [] randomPermutation(int n){
	  boolean [] check = new boolean [n];
	  int [] a = new int [n];
	  int var;
	  for(int i = 0; i < n; i++){
		  check[i] = false;
	  }
	  for (int i = 0; i < n; i++){
		  var = rand.nextInt(n);
		  if(i == 0){
			  a[i] = var;
			  check[var] = true;
		  }else{
			  if(!check[var]){
				  a[i] = var;
				  check[var] = true;
			  }else{
				  i--;
			  }
		  }
	  }
	  return a;
  }

//**************************************************************************************
  public static boolean [][] permuteGraph(boolean [][] G, int [] perm, int n)  {
	  boolean [][] H = new boolean [n][n];
	  for(int row = 0; row < n; row++){
		  for(int col = 0; col < n; col++){
			  H[perm[row]][perm[col]] = G[row][col];
		  }
	  }
	  return H;
  }
//**************************************************************************************
  public static int [] permuteHamCycle(int [] c, int [] a, int n)  {
    int [] pc = new int [n];
	for(int i = 0; i < n; i++){
		pc[i] = c[a[i]];
	}
    return pc;
  }


//**************************************************************************************
  public static int [] findHamCycle(boolean [][] G, int n)  {
  
    int [] c = new int [n];
    for(int i = 0; i < n; i++){
    	c[i] = i;
    }
	do{
		if(verifyHamCycle(G, c, n))
			return c;
  	}while(nextPerm(c, n));
	c[0] = -1;
    return c;
  }

//**************************************************************************************
  
public static void main(String[] args) throws IOException {
	
    int i = 0, j = 0, n = 0; // n = # vertices
    int [] a = null, c = null, pc = null;  // ham cycles or random permutations
    boolean [][] G = null;
    boolean [][] H = null;
    boolean pause = false;
  
    System.out.println("My name is Rivka Stauss, CS-355, Asst 3\n");
    System.out.print("Do you want to pause after each output?  ");
    char ans = kbd.nextLine().charAt(0);
    pause = (ans == 'y' || ans == 'Y');
    setUpFiles();
   
 
    System.out.println("Part A:  Verifying Hamiltonian Cycles.\n");
    
    while (inputA.hasNextInt()) {
    
      n = inputA.nextInt();
    
      // "c" is holding a potential Hamiltonian Cycle  
      c = getCycle(inputA, n); 
      G = getGraph(inputA, n);
    
      System.out.println("\nGraph G on " + n + " vertices, with potential Hamiltonian Cycle: ");
      outputArray(c, n);
    
      if (verifyHamCycle(G, c, n))
        System.out.println("\nThis IS INDEED a Hamiltonian Cycle in G.\n");
      else
        System.out.println("\nThis is NOT a Hamiltonian Cycle in G.\n");
        
      if (pause) {
        System.out.println("Hit Enter...");
        kbd.nextLine();
      }
   }  // end  while (inputA.hasNextInt()) {
        

    System.out.println("\nPart B:  Verifying Isomorphism.\n");
    while (inputB.hasNextInt()) {
    
      n = inputB.nextInt();
    
      // "a" is holding a potential isomorphism between G and H  
      G = getGraph(inputB, n);
      c = getCycle(inputB, n); 
      H = getGraph(inputB, n);
    
      System.out.println("\nGraph G and H on " + n + " vertices, with potential isomorphism:\n");
      outputArray(c, n);
    
      if (verifyIsomorphism(G, H, c, n))
        System.out.println("\nGraphs G and H are INDEED ISOMORPHIC!\n");
      else
        System.out.println("\nGraphs G and H are NOT isomorphic\n");
        
      if (pause) {
        System.out.println("Hit Enter...");
        kbd.nextLine();
      }
   }  // end  while (inputB.hasNextInt()) {
        
        

    System.out.println("\nPart C:  Permuting Graphs, Cycles, and Verifying.\n");

    while (inputC.hasNextInt()) {
    
      n = inputC.nextInt();
  
      // "c" is holding a Hamiltonian Cycle of G  
      c = getCycle(inputC, n); 
      G = getGraph(inputC, n);

      System.out.println("\nGiven graph G on " + n + " vertices, with Hamiltonian cycle:");
      outputArray(c, n);
      
      a = randomPermutation(n);
      H = permuteGraph(G, a, n);
      pc = permuteHamCycle(c, a, n);
      
      System.out.println("\nPermuted Hamiltonian Cycle pc:\n");
      outputArray(pc, n);
      
      System.out.println("\nH = G permuted by a is...\n" );
      outputGraph(H, n);
      System.out.println();
      
      System.out.println("\nCheck:  H isomorphic to G:  ");
      System.out.println(" " + verifyIsomorphism(G, H, a, n));
      System.out.println();
      
      System.out.print("\npc is a Hamiltonian Cycle in H:  ");
      System.out.print(" " + verifyHamCycle(H, pc, n));
      System.out.println();
             
      if (pause) {
        System.out.println("Hit Enter...");
        kbd.nextLine();
      }
   }  // end  while (inputC.hasNextInt()) {


      
    System.out.println("\nPart D:  Using Brute Force to find Hamiltonian Cycle.\n");

    while (inputD.hasNextInt()) {
    
      n = inputD.nextInt();
      System.out.println("\nGraph on " + n + " vertices...");
      
    
      G = getGraph(inputD, n);
      
      c = new int [n];
      
      c = findHamCycle(G, n);
      System.out.println("\nThis graph on " + n + " vertices \n");
      if (c[0] == -1){
        System.out.println("\nDoes NOT have any Hamiltonian Cycle.\n");
      }else  {
        System.out.println("\nDOES HAVE a Hamiltonian Cycle:\n");
        System.out.println();
        outputArray(c, n);
      }
      System.out.println();
      
      if (pause) {
        System.out.println("Hit Enter...");
        kbd.nextLine();
      }
    }  // end  while (inputD.hasNextInt()) {

   
    System.out.println("\nBye!"); 
   	System.out.println("\nBye!");
   	
 } // end main
    


  
//**************************************************************************************
  public static void setUpFiles() throws IOException  {
    
    FileReader fReader = new FileReader("IsCycleCorrect.txt");
		inputA = new Scanner(fReader);
    fReader = new FileReader("IsPermutationCorrect.txt");
		inputB = new Scanner(fReader);
    fReader = new FileReader("GraphsAndCyclesToPermute.txt");
		inputC = new Scanner(fReader);
    fReader = new FileReader("BruteForceAttempt.txt");
		inputD = new Scanner(fReader);
  }

//**************************************************************************************
  public static int [] getCycle(Scanner input, int n)  {

    int [] c = new int[n];
    for (int i = 0; i < n; i++)
      c[i] = input.nextInt();
    return c;
  }
    
//**************************************************************************************
  public static boolean [][] getGraph(Scanner input, int n)  {

    boolean [][] G = new boolean[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        G[i][j] = (input.nextInt() == 1);
    return G;
  }
    
  
//**************************************************************************************
  public static boolean nextPerm(int [] p, int n) {
    int k = -1, m = -1, i = 0, j = 0, temp = 0;
    
    for (i = n - 2; i >= 0; i--)
      if (p[i] < p[i+1]) {
        k = i;
        break;
      }
 
    if (k == -1)
      return false;
      
    for (i = n - 1; i >= 0; i--)
      if (p[k] < p[i]) {
        m = i;
        break;
      }

    temp = p[k];
    p[k] = p[m];
    p[m] = temp;
    
    int [] A = new int[n];
    
    for (i = k+1, j = n-1;  i < n;  i++, j--)
      A[i] = p[j];
    for (i = k+1; i < n; i++)
      p[i] = A[i];
 
    return true;
  }
   
//**************************************************************************************
  public static void outputArray(int [] a, int n){
	    int i;
	    System.out.println();
	    for (i = 0; i < n; i++){
	      System.out.print(a[i] + " ");
	    }
	    System.out.println();
	  }
    
//**************************************************************************************
  public static void outputGraph(boolean [][] G, int n){
	    
		int i, j;       
	    for (i = 0; i < n; i++) {
	      for (j = 0; j < n; j++){
	        if (G[i][j]){
	          System.out.print("1 ");
	        }else{
	          System.out.print("0 ");
	        }
	      }
	      System.out.println();
	    }   
	  }
//**************************************************************************************

} // end class
