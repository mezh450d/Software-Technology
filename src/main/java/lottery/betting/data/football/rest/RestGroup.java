package lottery.betting.data.football.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RestGroup {

	private int groupOrderID;
	private String groupName;

	public RestGroup() {}

	public int getGroupOrderID() {
		return groupOrderID;
	}

	public void setGroupOrderID(int groupOrderID) {
		this.groupOrderID = groupOrderID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
