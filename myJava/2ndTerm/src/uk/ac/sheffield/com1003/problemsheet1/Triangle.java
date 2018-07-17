package uk.ac.sheffield.com1003.problemsheet1;

public class Triangle {
	private int[] sides;

	/**
	 * Constructs a triangle given three side lengths
	 * 
	 * @param firstSide the first side
	 * @param secondSide the second side
	 * @param thirdSide the third side
	 */
	public Triangle(int firstSide, int secondSide, int thirdSide) {
		// put the parameters into an private array internal to the object
		sides = new int[3];
		sides[0] = firstSide;
		sides[1] = secondSide;
		sides[2] = thirdSide;

		// The rest of the code in the constructor sorts the sides of the
		// triangle
		// in the sides array into length order. This makes the invalid triangle
		// test easy
		// to perform in the getType() method

		int temp; // used to temporarily store a side length when doing a length
				  // swap

		if (sides[0] > sides[1]) {
			// swap sides at array indexes 0 and 1
			temp = sides[0];
			sides[0] = sides[1];
			sides[1] = temp;
		}

		if (sides[1] > sides[2]) {
			// swap sides at array indexes 1 and 2
			temp = sides[1];
			sides[1] = sides[2];
			sides[2] = temp;
		}
	}

	/**
	 * Get the length of a side
	 * 
	 * @param side the side number (0-2)
	 * @return the side's length, or -1 if an invalid side was supplied as a
	 *         parameter
	 */
	public int getSide(int side) {
		// check the side number is in the valid range
		if (side >= 0 && side < 3) {
			return sides[side];
		} else {
			// invalid side number given, return -1
			return -1;
		}
	}

	/**
	 * Returns a message indicating the type of the triange i.e. "equilateral",
	 * "isosceles", "scalene" or "invalid".
	 */
	public String getType() {

		// test whether the sides are of valid lengths
		if (sides[0] <= 0 || sides[1] <= 0 || sides[2] <= 0) {
			return "invalid";
		}

		// test for an invalid triangle
		if (sides[0] + sides[1] < sides[2]) {
			return "invalid";
		}

		// if two sides are equal, the triangle type is isosceles
		if (sides[0] == sides[1] || sides[1] == sides[2]
				|| sides[2] == sides[0]) {
			return "isosceles";
		}

		// if all three sides are of equal length, it's an equilateral triangle
		if (sides[0] == sides[1] && sides[1] == sides[2]) {
			return "equilateral";
		}

		// none of the above, therefore it's a scalene triangle
		return "scalene";
	}
}