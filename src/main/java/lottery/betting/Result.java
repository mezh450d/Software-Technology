package lottery.betting;

import javax.persistence.Embeddable;

@Embeddable
public interface Result<T extends Result> extends Comparable<T>{
	String toString();
}
