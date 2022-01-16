package lottery.betting.data.football.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestMatch {

	private String matchDateTime, leagueShortcut;
	private RestGroup group;
	private RestTeam team1;
	private RestTeam team2;
	private boolean matchIsFinished;
	private List<RestResult> matchResults;

	public RestMatch() {
	}

	public String getMatchDateTime() {
		return matchDateTime;
	}

	public void setMatchDateTime(String matchDateTime) {
		this.matchDateTime = matchDateTime;
	}

	public String getLeagueShortcut() {
		return leagueShortcut;
	}

	public void setLeagueShortcut(String leagueShortcut) {
		this.leagueShortcut = leagueShortcut;
	}

	public RestGroup getGroup() {
		return group;
	}

	public void setGroup(RestGroup group) {
		this.group = group;
	}

	public RestTeam getTeam1() {
		return team1;
	}

	public void setTeam1(RestTeam team1) {
		this.team1 = team1;
	}

	public RestTeam getTeam2() {
		return team2;
	}

	public void setTeam2(RestTeam team2) {
		this.team2 = team2;
	}

	public boolean isMatchIsFinished() {
		return matchIsFinished;
	}

	public void setMatchIsFinished(boolean matchIsFinished) {
		this.matchIsFinished = matchIsFinished;
	}

	public List<RestResult> getMatchResults() {
		return matchResults;
	}

	public void setMatchResults(List<RestResult> matchResults) {
		this.matchResults = matchResults;
	}

	@Override
	public String toString(){
		return team1.getShortName() + "-" + team2.getShortName() + "/" + getMatchDateTime();
	}
}
