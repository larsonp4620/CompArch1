package compiler;
/**
 * TODO Put here a description of what this class does.
 * 
 * @author larsonp. Created Oct 21, 2014.
 */
public class AssemblerConverter {

	/**
	 * This converts from a string to an int. don't screw up.
	 * 
	 * @param input
	 * @return
	 */
	public static int booleanString2Dec(String input) {
		return Integer.parseInt(input);
	}

	/**
	 * This converts from a integer to a string. Don't mess up.
	 * 
	 * @param i
	 * @return
	 */
	public static String intToBooleanString(int i) {
		String zeroes = "";
		String temp = Integer.toBinaryString(i);
		if (temp.length() < 16) {
			int needed = 16 - temp.length();
			for (int j = 0; j < needed; j++) {
				zeroes=zeroes+"0";
			}
		}
		return zeroes+temp;
	}
	
	/**
	 * Same as convert, but allows you to specify the length of the binary string it returns
	 *
	 * @param input
	 * @param length
	 * @return
	 */
	public static String intToBooleanString_withLength(int input, int length){
		String zeroes = "";
		String temp = Integer.toBinaryString(input);
		if (temp.length() < length) {
			int needed = length - temp.length();
			for (int j = 0; j < needed; j++) {
				zeroes=zeroes+"0";
			}
		}
		return zeroes+temp;
	}
}
