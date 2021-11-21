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
		return "SelectNumber: "+numStr +"  SuperNumber: "+ superNumber;
	}

	@Override
	public int compareTo(SelectNumber o) {
		return 0;
	}

	public int compareResult(String numStr, int superNum, String selectNumStr, int selectNum) {
		String[] strArr1 = numStr.split(",");
		List<String> list = Arrays.asList(strArr1);
		String[] strArr2 = selectNumStr.split(",");
		int equalCount = 0;
		for (int i = 0; i < strArr2.length; i++) {
			if (list.contains(strArr2[i])) {
				equalCount++;
			}
		}
		if (equalCount == 6 && superNum == selectNum) {
			return 1000;
		} else if (equalCount == 6 && superNum != selectNum) {
			return 900;
		}else if (equalCount == 5 && superNum == selectNum) {
			return 800;
		}else if (equalCount == 5 && superNum != selectNum) {
			return 700;
		}else if (equalCount == 4 && superNum == selectNum) {
			return 600;
		}else if (equalCount == 4 && superNum != selectNum) {
			return 300;
		}else if (equalCount == 3 && superNum == selectNum) {
			return 200;
		}else if (equalCount == 3 && superNum != selectNum) {
			return 100;
		}else if (equalCount == 2 && superNum == selectNum) {
			return 6;
		}
		return 0;
	}
}
