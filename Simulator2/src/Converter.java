
/**
 * TODO Put here a description of what this class does.
 *
 * @author larsonp.
 *         Created Oct 21, 2014.
 */
public class Converter {

	/**
	 * This converts from a string to an int. don't screw up. 
	 * @param input
	 * @return
	 */
	public static int booleanString2Dec(String input){
		return Integer.parseInt(input);
	}
	
	/**
	 * This converts from a integer to a string. Don't mess up.
	 *
	 * @param i
	 * @return
	 */
	public static String intToBooleanString(int i){
		return Integer.toBinaryString(i);
	}
}
