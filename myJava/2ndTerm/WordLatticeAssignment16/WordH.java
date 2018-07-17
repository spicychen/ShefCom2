package WordLatticeAssignment16;


/**
 * WordH.java
 *
 * A word in a word lattice
 *
 * @author Phil Green
 * 2016
 */

public class WordH {
  private String word;
  private int  st;
  private int end;
  private int cost; // the logprob
  private int estRemCost;

// constructor
  public  WordH(String w, int s, int e, int p){
	    word=w;
	    st =s;
	    end=e;
	    cost = p;
	  }
  public  WordH(String w, int s, int e, int p, int erc){
    word=w;
    st =s;
    end=e;
    cost = p;
    estRemCost = erc;
  }
//accessors

  public String getWord(){return word;}
  public int  getStart(){return st;}
  public int getEnd() {return end;}
  public int getCost() {return cost;}
  public int getERCost() {return estRemCost;}

  public String toString() {return word+ " "+st+" "+ end +" " + cost+ " "+ estRemCost;}

}

