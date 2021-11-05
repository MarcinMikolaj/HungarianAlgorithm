package project;

import java.util.Comparator;

import project.pojo.NW;

public class NWComparator implements Comparator<NW> {

	@Override
	public int compare(NW o1, NW o2) {
		if(o1.getNumberOfZeros() < o2.getNumberOfZeros()) return 1;
		if(o1.getNumberOfZeros() > o2.getNumberOfZeros()) return -1;
		
		return 0;
	}

}
