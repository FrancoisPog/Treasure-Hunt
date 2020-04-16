package treasure_hunt;


/**
 * <p><strong>Border_c</strong> is the class representing a cell on the matrix extremities.<p>
 * <p>When a border is queried by a hunter, it change is direction for the opposite.</p>
 * @see treasure_hunt.Cell
 * @see treasure_hunt.Board
 * 
 * 
 * @author François Poguet
 */
public class Border_c extends Cell {

	public Border_c(Position pos,Board cm) {
		super(pos,cm);
	}
	
	
	@Override
	public void process(Hunter h) {
		
		switch(h.getDirection()) {
			case 3:
				h.setDirection(7);
				break;
			case 4:
				h.setDirection(8);			
				break;
			case 2:
				h.setDirection(6);			
				break;
			case 1:
				h.setDirection(5);
				break;
			case 5:
				h.setDirection(1);
				break;
			case 7:
				h.setDirection(3);
				break;
			case 6:
				h.setDirection(2);			
				break;
			case 8:
				h.setDirection(4);
				break;
			default : 
				System.err.println("Error");
				break;
		}
		//System.out.println("["+h+"] : Bord -> "+h.getDirection());
	}

	@Override
	public String toString() {
		return " + ";
	}


	@Override
	public byte encode() {
		return 1;
	}


	

	
	
}
