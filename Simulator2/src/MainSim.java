/**
 * The primary section of the simulator.
 *
 * @author larsonp.
 *         Created Oct 21, 2014.
 */
public class MainSim {

	/**
	 * Begins the whole program.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("----Testing Conversions----");
		System.out.println(Converter.booleanString2Dec("00001"));
		System.out.println(Converter.intToBooleanString_withLength(10,5));
		System.out.println();
		Simulation sim = new Simulation();
		sim.initializeGUI();
	}

}
