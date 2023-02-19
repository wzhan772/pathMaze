/**
 * @author William Zhang 251215208 
 * the purpose of this class is to find the best path to complete the chamber
 *
 */
public class FindPath {
	
	private Map pyramidMap;

	/**
	 * constructor for the class
	 * 
	 * @param fileName takes in the name of the file containing
	 * descriptions of the chambers of the pyramid
	 */
	public FindPath(String fileName) {
		//try catch to catch any exceptions
		try {
			pyramidMap = new Map(fileName);
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}
	}

	/**
	 * finds a path from the entrance to all the treasure chambers that can be reached
	 * by satisfying all the requirements 
	 * 
	 * @return the stack
	 */
	public DLStack<Chamber> path() {
        DLStack<Chamber> stack = new DLStack<Chamber>();
        Chamber start = pyramidMap.getEntrance();
        int treasureNum = pyramidMap.getNumTreasures();
        int treasureNumFound = 0;
        //push the stack and mark it as pushed
        stack.push(start);
        start.markPushed();
        boolean found = false;
        //while loop until all the treasure is found or is the stack is empty
        while (!stack.isEmpty() && found == false) {
            Chamber currentChamber = stack.peek();
            if (treasureNumFound==treasureNum && currentChamber.isTreasure()) {
                found = true;
            //find the best alternative chamber to go to
            } else {
                Chamber bestAdj = this.bestChamber(currentChamber);
                if (bestAdj == null) {
                	stack.pop();
                    currentChamber.markPopped();
                } else {
                	//if a best alternative is found, push it onto the stack
                	stack.push(bestAdj);
                	bestAdj.markPushed();
                    if (bestAdj.isTreasure()) {
                    	treasureNumFound++;
                    }
                }
            }
        }
        return stack;
    }

	/**
	 * 
	 * @return the value of pyramidMap
	 */
	public Map getMap() {
		return pyramidMap;
	}

	/**
	 * returns true or false depending on whether the current chamber is dim or not 
	 * 
	 * @param currentChamber is the current chamber
	 * @return true or false
	 */
	public boolean isDim(Chamber currentChamber) {
		//if the chamber is not null, sealed, or lighted, get the neighbour of the chamber
		if (currentChamber != null) {
			if (!currentChamber.isSealed()) {
				if (!currentChamber.isLighted())
					for (int i = 0; i < 6; i++) {
						if (currentChamber.getNeighbour(i) != null) {
							//if the neighbour is lighted return true
							if (currentChamber.getNeighbour(i).isLighted()) {
								return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * selects the best chamber to move from the current chamber
	 * 
	 * @param currentChamber is the current chamber
	 * @return the next chamber
	 */
	public Chamber bestChamber(Chamber currentChamber) {
		//initialize values
		Chamber next = null;
		boolean treasure = false;
		boolean light = false;
		boolean dim = false;
		if (currentChamber != null) {
			int lightRoom = 0;
			int dimRoom = 0;
			//loop through to determine which chamber is the best based on the specifications
			for (int i = 0; i < 6; i++) {
				next = currentChamber.getNeighbour(i);
				//if the next chamber is not null, sealed, or marked, check if it is the treasure
				if (next != null) {
					if (!next.isSealed()) {
						if (!next.isMarked()) {
							if (next.isTreasure()) {
								treasure = true;
								break;
							//set the lighted and dim rooms as the index and true if the specifications are met
							} else if (!light && next.isLighted()) {
								lightRoom = i;
								light = true;
							} else if (!dim && isDim(next)) {
								dimRoom = i;
								dim = true;
							}
						}
					}
				}
			}
			//if there is no lighted dim or treasure then return null
			//else return the lighted or dim rooms
			if (!light && !dim && !treasure) {
				return null;
			} else if (light && !treasure) {
				return currentChamber.getNeighbour(lightRoom);
			} else if (!light && dim && !treasure){
				return currentChamber.getNeighbour(dimRoom);
			}
		}
		return next;
	}
}