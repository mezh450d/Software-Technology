package lottery.betting.number;

import lottery.betting.Result;

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

		if (equalCount == 6) {
			if(superNumber == otherSuperNumber){
				return 100000;
			} else {
				return 3000;
			}
		}
		if (equalCount == 5) {
			if(superNumber == otherSuperNumber){
				return 675;
			} else {
				return 225;
			}
		}
		if (equalCount == 4) {
			if(superNumber == otherSuperNumber){
				return 12;
			} else {
				return 4;
			}
		}
		if (equalCount == 3) {
			if(superNumber == otherSuperNumber){
				return 2;
			} else {
				return 1;
			}
		}
		return 0;
	}
}
