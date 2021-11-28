package lottery.admin;

import lottery.community.Community;
import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AdminEntry {
	private @Id @GeneratedValue long id;
	private String name;
	private String communityName;
	//betting amount of football match
	private Money bettingAmount;
	//menge of number betting
	private Integer menge;
	//football match result
	private String footballMatchResult;
	//number betting result
	private Integer result;
	private String fbWinOrLose;
	private String lottoWinOrLose;

	public AdminEntry() {}

	public AdminEntry(long id, UserAccount name, Community communityName, Money bettingAmount, Integer menge,
					  String footballMatchResult, Integer result, String fbWinOrLose, String lottoWinOrLose) {
		this.id = id;
		this.name = name.getUsername();
		this.communityName = communityName.getName();
		this.bettingAmount = bettingAmount;
		this.menge = menge;
		this.footballMatchResult = footballMatchResult;
		this.result = result;
		this.fbWinOrLose = fbWinOrLose;
		this.lottoWinOrLose = lottoWinOrLose;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String  communityName) {
		this.communityName = communityName;
	}

	public Money getBettingAmount() {
		return bettingAmount;
	}

	public void setBettingAmount(Money bettingAmount) {
		this.bettingAmount = bettingAmount;
	}

	public Integer getMenge() {
		return menge;
	}

	public void setMenge(Integer menge) {
		this.menge = menge;
	}

	public String getFootballMatchResult() {
		return footballMatchResult;
	}

	public void setFootballMatchResult(String footballMatchResult) {
		this.footballMatchResult = footballMatchResult;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getFbWinOrLose() {
		return fbWinOrLose;
	}

	public void setFbWinOrLose(String fbWinOrLose) {
		this.fbWinOrLose = fbWinOrLose;
	}

	public String getLottoWinOrLose() {
		return lottoWinOrLose;
	}

	public void setLottoWinOrLose(String lottoWinOrLose) {
		this.lottoWinOrLose = lottoWinOrLose;
	}


}