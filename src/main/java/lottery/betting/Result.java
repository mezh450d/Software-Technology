package lottery.betting;

import java.io.Serializable;

public interface Result<T> extends Comparable<T>, Serializable {
//	static final long serialVersionUID = -7565813772046251748L;
	String toString();
}
