import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

	
	public class Simulation {
		/**
		 * Initializes GUI components which allow the simulator to be directed by the user.
		 */
		public void initializeGUI(){
			JPanel panel = new JPanel();
			
			JButton btn = new JButton("Test Simulator");
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					testSim();
				}
			});
			panel.add(btn);
			
			btn = new JButton("Test Machine Code");
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					testMachine();
				}
			});
			panel.add(btn);
			
			btn = new JButton("Test Assembly Code");
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					testAssembly();
				}
			});
			panel.add(btn);
			JFrame frame = new JFrame("Processor Simulator");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(panel);
			frame.pack();
			frame.setVisible(true);
		}
		
		public void testSim(){
			
		}
		
		public void testMachine(){
			String machineCode = loadFromFile(true);
			runMachineCode(machineCode);
		}
		
		public void testAssembly(){
			String assemblyCode = loadFromFile(false);
			// Convert assembly to machine
			runMachineCode(assemblyCode);
		}
		
		/**
		 * Loads text from a file
		 * @param machine
		 * @return
		 */
		public String loadFromFile(boolean machine){
			String returnStr = "";
			String filename;
			if(machine)
				filename = "machine.txt";
			else
				filename = "assembly.txt";
			
			return returnStr;
		}
		
		/**
		 * Runs the machine code on the circuit
		 * @param code
		 */
		public void runMachineCode(String code){
			Circuit circuit = new Circuit();
			
		}
	}