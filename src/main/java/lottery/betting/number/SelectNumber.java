package lottery.betting.number;

import lottery.betting.Result;
import lottery.betting.football.Score;

import javax.persistence.Entity;


public class SelectNumber implements Result<SelectNumber> {

	@Override
	public String toString(){
		return "";
	}

	@Override
	public int compareTo(SelectNumber o) {
		return 0;
	}

}
