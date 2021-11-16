package kickstart.admin;

import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserInfo {
	private @Id @GeneratedValue long id;
	private String name;
	private String communityID;
	//betting amount of football match
	private Money bettingAmount;
	//menge of number betting
	private Integer menge;
	//football match result
	private String footballMatchResult;
	private int homeScore;
	private int guestScore;
	//number betting result
	private Integer result;
	private String fbWinOrLose;
	private String lottoWinOrLose;

	public UserInfo(String name, String communityID, Money bettingAmount, Integer menge, String footballMatchResult, Integer result, String fbWinOrLose, String lottoWinOrLose){
		this.name = name;
		this.communityID = communityID;
		this.bettingAmount = bettingAmount;
		this.menge = menge;
		this.footballMatchResult = footballMatchResult;
		this.result = result;
		this.fbWinOrLose = fbWinOrLose;
		this.lottoWinOrLose = lottoWinOrLose;
	}

	public long getId(){
		return  id;
	}

	public String getName() { return name; }

	public String getCommunityID() {return communityID;}

	public Money getBettingAmount() {return bettingAmount;}

	public Integer getMenge() {return menge;}

	public String getFootballMatchResult(int homeScore, int guestScore) {return footballMatchResult;}

	public Integer getResult() {return result;}

	public String getFbWinOrLose() {return fbWinOrLose;}

	public String getLottoWinOrLose() {return lottoWinOrLose;}


}
