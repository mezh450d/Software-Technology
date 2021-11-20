package lottery.betting.number;

public class ReturnResult {

	private int code = 0;
	private String msg = "success";
	private Object data;

	public int getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}

	public String getMsg() {
		return msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
