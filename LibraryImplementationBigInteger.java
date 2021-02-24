package com.test.data.structure.learning.serviceImpl;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryImplementationBigInteger implements Comparable<LibraryImplementationBigInteger> {

	private static final char MINUS_CHAR = '-';
	private static final char PLUS_CHAR = '+';

	private ArrayList<Integer> numberDigits = new ArrayList<>();

	private boolean negative;

	private String stringNumber;

	LibraryImplementationBigInteger(String number) {

		if (number.equals("")) {
			stringNumber = "0";
			numberDigits.add(0);
		} else {
			char firstChar = number.charAt(0);
			if (firstChar == MINUS_CHAR || firstChar == PLUS_CHAR) {
				if (firstChar == MINUS_CHAR)
					negative = true;

				number = number.substring(1);
			}

			number = number.replaceFirst("^0+(?!$)", "");
			stringNumber = number;

			for (int index = 0; index < number.length(); index++) {
				int curDigNumericVal = Character.getNumericValue(number.charAt(index));

				if (curDigNumericVal == -1)
					throw new IllegalArgumentException();

				numberDigits.add(curDigNumericVal);
			}
		}
	}

	private boolean isNegative() {
		return negative;
	}

	private void flipNegativity() {
		if (stringNumber == "0")
			return;

		negative = !negative;
		if (stringNumber.charAt(0) == MINUS_CHAR) {
			stringNumber = stringNumber.substring(1);
		} else {
			stringNumber = MINUS_CHAR + stringNumber;
		}
	}

	LibraryImplementationBigInteger plus(LibraryImplementationBigInteger otherNumber) {

		if (negative && !otherNumber.isNegative()) {
			return otherNumber.minus(new LibraryImplementationBigInteger(stringNumber));
		}

		if (otherNumber.isNegative()) {
			return minus(new LibraryImplementationBigInteger(otherNumber.toString()));
		}

		ArrayList<Integer> longerNumber, shorterNumber;
		if (numberDigits.size() >= otherNumber.numberDigits.size()) {
			longerNumber = numberDigits;
			shorterNumber = otherNumber.numberDigits;
		} else {
			longerNumber = otherNumber.numberDigits;
			shorterNumber = numberDigits;
		}

		int lengthsDifferences = longerNumber.size() - shorterNumber.size();

		StringBuilder resultString = new StringBuilder();

		int carry = 0;

		for (int index = shorterNumber.size() - 1; index >= 0; index--) {
			int shorterNumberDigit = shorterNumber.get(index);
			int longerNumberDigit = longerNumber.get(index + lengthsDifferences);

			int newDigit = shorterNumberDigit + longerNumberDigit + carry;

			carry = newDigit / 10;
			newDigit = newDigit % 10;

			resultString.append(newDigit);
		}

		for (int index = lengthsDifferences - 1; index >= 0; index--) {
			int currDig = longerNumber.get(index);

			if (currDig + carry == 10) {
				resultString.append(0);
				carry = 1;
			} else {
				resultString.append(currDig + carry);
				carry = 0;
			}
		}

		if (carry > 0)
			resultString.append(carry);

		return new LibraryImplementationBigInteger(resultString.reverse().toString());
	}

	LibraryImplementationBigInteger minus(LibraryImplementationBigInteger otherNumber) {

		if (otherNumber.isNegative()) {
			return plus(new LibraryImplementationBigInteger(otherNumber.stringNumber));
		}
		if (this.compareTo(otherNumber) < 0) {
			LibraryImplementationBigInteger result = otherNumber.minus(this);
			result.flipNegativity();
			return result;
		}

		int lengthsDifferences = numberDigits.size() - otherNumber.numberDigits.size();

		StringBuilder resultString = new StringBuilder();

		int carry = 0;

		for (int index = otherNumber.numberDigits.size() - 1; index >= 0; index--) {
			int biggerNumDig = numberDigits.get(index + lengthsDifferences) - carry;
			int smallerNumDig = otherNumber.numberDigits.get(index);

			carry = 0;

			if (biggerNumDig < smallerNumDig) {
				carry = 1;
				biggerNumDig += 10;
			}

			resultString.append(biggerNumDig - smallerNumDig);
		}

		for (int index = lengthsDifferences - 1; index >= 0; index--) {
			int currDig = numberDigits.get(index);

			if (carry > currDig) {
				resultString.append(currDig + 10 - carry);
				carry = 1;
			} else {
				resultString.append(currDig - carry);
				carry = 0;
			}
		}

		return new LibraryImplementationBigInteger(resultString.reverse().toString());
	}

	@Override
	public int compareTo(LibraryImplementationBigInteger other) {

		if (isNegative() && !other.isNegative())
			return -1;

		else if (!isNegative() && other.isNegative()) {
			return 1;
		}

		else if (isNegative()) {
			if (numberDigits.size() > other.numberDigits.size())
				return -1;
			else if (numberDigits.size() < other.numberDigits.size())
				return 1;

			else
				for (int index = 0; index < numberDigits.size(); index++) {

					if (numberDigits.get(index) > other.numberDigits.get(index))
						return -1;

					else if (numberDigits.get(index) < other.numberDigits.get(index))
						return 1;
				}

			return 0;
		}


		if (numberDigits.size() > other.numberDigits.size()) {
			return 1;
		}

		else if (numberDigits.size() < other.numberDigits.size())
			return -1;

		else
			for (int index = 0; index < numberDigits.size(); index++) {

				if (numberDigits.get(index) > other.numberDigits.get(index))
					return 1;

				else if (numberDigits.get(index) < other.numberDigits.get(index))
					return -1;
			}

		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null)
			return false;

		if (getClass() != o.getClass())
			return false;

		LibraryImplementationBigInteger other = (LibraryImplementationBigInteger) o;

		return other.toString().equals(stringNumber);
	}

	@Override
	public String toString() {
		return stringNumber;
	}

	public static void main(String[] args) {
		
		 LibraryImplementationBigInteger ob1,ob2=null;
		 Scanner scanner = new Scanner(System.in);
		 String input = scanner.nextLine();
		 String[] strs = input.split("\\s+");
		 if(strs.length !=3) {
			 System.out.println("Invalid Input please try again");
		 }else{
			 String operation = strs[0];
			 String first = strs[1];
			 String second = strs[2];
			 ob1 = new LibraryImplementationBigInteger(first);
			  ob2 = new LibraryImplementationBigInteger(second);
			 if("ADD".equals(operation))
				 System.out.println(ob1.plus(ob2));
			 else if("SUB".equals(operation))
				 System.out.println(ob1.minus(ob2));
			 else
				 System.out.println("Invalid Operation");
		 }
		 scanner.close();
		
	}
}