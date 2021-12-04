package lottery.message;

import org.javamoney.moneta.Money;
import org.salespointframework.useraccount.UserAccount;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String user;
	private String topic;
	private String detail;
	private Money fine;
	private LocalDateTime date;
	private static DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
	@SuppressWarnings("unused")
	protected Message() {}

	public Message(UserAccount user, String topic, String detail, Money fine, LocalDateTime date){
		this.user = user.getUsername();
		this.topic = topic;
		this.detail= detail;
		this.fine = fine;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public Money getFine(){
		return fine;
	}

	public String getTopic(){
		return topic;
	}

	public String getDetail(){
		return detail;
	}

	public String getDate() { return date.format(formatDateTime); }

}
