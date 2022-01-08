package lottery.betting.data.football.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestTeam {

	private String teamName;
	private String shortName;
	private String teamIconUrl;

	public RestTeam() {
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getTeamIconUrl() {
		return teamIconUrl;
	}

	public void setTeamIconUrl(String teamIconUrl) {
		this.teamIconUrl = teamIconUrl;
	}
}
