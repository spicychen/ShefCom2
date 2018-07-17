package java2016.java2016.search2.Search2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SameStateTest {
		Chessboard cb1 = new Chessboard(7);
		NQueensState nq1 = new NQueensState(cb1,1,1);
		
		@Before
		public void initiallise(){
		cb1.placeQueen(1, 4);
		cb1.placeQueen(6, 0);
		}

	@Test //random chessboard
	public void test0() {
		System.out.println("test0: random chessboard");
		System.out.println(cb1);
		Chessboard cb2 = new Chessboard(7);
		cb2.placeQueen(4, 3);
		cb2.placeQueen(6, 6);
		NQueensState nq2 = new NQueensState(cb2,1,1);
		System.out.println(cb2);
		assertFalse(nq1.sameState(nq2));
	}
	
	@Test //backslashmirror
	public void test1() {
		System.out.println("test1: backslashmirror");
		System.out.println(cb1);
		Chessboard cb2 = new Chessboard(7);
		cb2.placeQueen(4, 1);
		cb2.placeQueen(0, 6);
		NQueensState nq2 = new NQueensState(cb2,1,1);
		System.out.println(cb2);
		assertTrue(nq1.sameState(nq2));
	}
	
	@Test //slashmirror
	public void test2() {
		System.out.println("test2: slashmirror");
		System.out.println(cb1);
		Chessboard cb2 = new Chessboard(7);
		cb2.placeQueen(2, 5);
		cb2.placeQueen(6, 0);
		NQueensState nq2 = new NQueensState(cb2,5,5);
		System.out.println(cb2);
		assertTrue(nq1.sameState(nq2));
	}
	
	@Test //horizontal
	public void test3() {
		System.out.println("test3: horizontal");
		System.out.println(cb1);
		Chessboard cb2 = new Chessboard(7);
		cb2.placeQueen(1, 2);
		cb2.placeQueen(6, 6);
		NQueensState nq2 = new NQueensState(cb2,1,5);
		System.out.println(cb2);
		assertTrue(nq1.sameState(nq2));
	}
	
	@Test //vertical
	public void test4() {
		System.out.println("test4: vertical");
		System.out.println(cb1);
		Chessboard cb2 = new Chessboard(7);
		cb2.placeQueen(5, 4);
		cb2.placeQueen(0, 0);
		NQueensState nq2 = new NQueensState(cb2,5,1);
		System.out.println(cb2);
		assertTrue(nq1.sameState(nq2));
	}
	
	@Test //inequalQueens
	public void test5() {
		System.out.println("test5: inequalQueens");
		System.out.println(cb1);
		Chessboard cb2 = new Chessboard(7);
		cb2.placeQueen(4, 1);
		NQueensState nq2 = new NQueensState(cb2,1,1);
		System.out.println(cb2);
		assertFalse(nq1.sameState(nq2));
	}

}
