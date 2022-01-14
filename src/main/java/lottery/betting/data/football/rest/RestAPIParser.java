package lottery.betting.data.football.rest;

import lottery.betting.data.Category;
import lottery.betting.data.football.FootballMatch;
import lottery.betting.data.football.Score;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.salespointframework.core.Currencies.EURO;

@Service
public class RestAPIParser {

	private final WebClient webClient;

	//URI
	private static final String uriSingleBL1MatchIncomplete = "/bl1/2021/";
	private static final String uriNextBL1Matchday = "/bl1";
	private static final String uriSingleBL2MatchIncomplete = "/bl2/2021/";
	private static final String uriNextBL2Matchday = "/bl2";

	public RestAPIParser(WebClient.Builder builder){
		webClient = builder.baseUrl("https://api.openligadb.de/getmatchdata").build();
	}

	public List<FootballMatch> getMatches(String uri){
		return parseRestMatches(fetchMatchesFromOpenLigaDB(uri));
	}

	public List<FootballMatch> getNextBL1MatchDay(){
		return parseRestMatches(fetchMatchesFromOpenLigaDB(uriNextBL1Matchday));
	}
	public List<FootballMatch> getNextBL2MatchDay(){
		return parseRestMatches(fetchMatchesFromOpenLigaDB(uriNextBL2Matchday));
	}

	public List<FootballMatch> getSpecificMatchDay(int matchDay){
		if(1 > matchDay || matchDay > 32){
			return null;
		} else {
			List<FootballMatch> completeMatchDay = new ArrayList<>();
			completeMatchDay.addAll(parseRestMatches(fetchMatchesFromOpenLigaDB(
					uriSingleBL1MatchIncomplete + matchDay)));
			completeMatchDay.addAll(parseRestMatches(fetchMatchesFromOpenLigaDB(
					uriSingleBL2MatchIncomplete + matchDay)));
			return completeMatchDay;
		}
	}

	public List<FootballMatch> getCompleteBLSeason(){
		List<FootballMatch> season = new ArrayList<>();
		for(int i = 1; i <= 32; i++){
			season.addAll(getSpecificMatchDay(i));
		}
		return season;
	}

	/**
	 * fetches data from OpenLigaDB
	 * @param uri, additional uri added to the baseURL
	 * @return an array of matches in the RestMatch-Template
	 */
	public RestMatch[] fetchMatchesFromOpenLigaDB(String uri){
		return webClient
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(RestMatch[].class)
				.block();
	}

	/**
	 * parses an array of the class RestMatch to the used class FootballMatch
	 * @param restMatches, the array of RestMatches received from OpenLigaDB
	 * @return List containing all requested FootballMatches
	 */
	public List<FootballMatch> parseRestMatches(RestMatch[] restMatches){
		List<FootballMatch> matches = new ArrayList<>();
		for(RestMatch match : restMatches){
			FootballMatch parsedMatch = new FootballMatch(match.toString(), Money.of(1, EURO),
					LocalDateTime.parse(match.getMatchDateTime()), Category.FOOTBALL,
					match.getTeam1().getTeamName(), match.getTeam2().getTeamName());
			if(match.isMatchIsFinished()){
				parsedMatch.setResult(new Score(match.getMatchResults().get(0).getPointsTeam1(),
						match.getMatchResults().get(0).getPointsTeam2()));
			}
			matches.add(parsedMatch);
		}
		return matches;
	}
}
