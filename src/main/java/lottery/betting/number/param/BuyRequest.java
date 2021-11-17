package lottery.betting.number.param;

import java.util.List;

public class BuyRequest {

	private List<Integer> numberList;
	private int superzahl;
	private int menge;

	public List<Integer> getNumberList() {
		return numberList;
	}

	public void setNumberList(List<Integer> numberList) {
		this.numberList = numberList;
	}

	public int getSuperzahl() {
		return superzahl;
	}

	public void setSuperzahl(int superzahl) {
		this.superzahl = superzahl;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}
}
