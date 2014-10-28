import java.util.ArrayList;

public class Circuit {
	
	ArrayList<Clockable> components;
	ArrayList<Clockable> logic;
	
	public Circuit(){
		components = new ArrayList<Clockable>();
		components.add(new Register());
	}
}
