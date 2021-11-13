package kickstart.betting.number;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
public class NumberEntry {

	private  @Id @GeneratedValue long id;
	private  String numberListStr;
	private  Integer superzahl;
	private Integer menge;
    private Long userId;
	private Integer result;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumberListStr() {
		return numberListStr;
	}

	public void setNumberListStr(String numberListStr) {
		this.numberListStr = numberListStr;
	}

	public Integer getSuperzahl() {
		return superzahl;
	}

	public void setSuperzahl(Integer superzahl) {
		this.superzahl = superzahl;
	}

	public Integer getMenge() {
		return menge;
	}

	public void setMenge(Integer menge) {
		this.menge = menge;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
