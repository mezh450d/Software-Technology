package lottery.betting.data.number;

import lottery.betting.data.Result;

import java.util.Arrays;
import java.util.List;


public class SelectNumber implements Result<SelectNumber> {
	private final String numStr;
	private final int superNumber;

	public SelectNumber(String numStr, int superNumber){
	  this.numStr = numStr;
	  this.superNumber = superNumber;
	}

	public String getNumStr() {
		return numStr;
	}

	public int getSuperNumber() {
		return superNumber;
	}

	@Override
	public String toString(){
		return numStr + " // "+ superNumber;
	}

	@Override
	public int compareTo(SelectNumber o) {
		String otherNumStr = o.numStr;
		int otherSuperNumber = o.superNumber;

		String[] strArr1 = numStr.split(",");
		List<String> list = Arrays.asList(strArr1);
		String[] strArr2 = otherNumStr.split(",");

		int equalCount = 0;
		for (String s : strArr2) {
			if (list.contains(s)) {
				equalCount++;
			}
		}

		int compared = 0;

		switch(equalCount){
			case 3:
				if(superNumber == otherSuperNumber){
					compared = 2;
				} else {
					compared = 1;
				}
				break;
			case 4:
				if(superNumber == otherSuperNumber){
					compared = 12;
				} else {
					compared = 4;
				}
				break;
			case 5:
				if(superNumber == otherSuperNumber){
					compared = 400;
				} else {
					compared = 150;
				}
				break;
			case 6:
				if(superNumber == otherSuperNumber){
					compared = 50000;
				} else {
					compared = 1500;
				}
				break;
		}
		return compared;
	}
}
