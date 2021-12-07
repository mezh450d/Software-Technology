package lottery.message;


import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class MessageManagement {
	private final MessageRepository entries;

	MessageManagement(MessageRepository messageRepository) {

		Assert.notNull(messageRepository, "messageRepository must not be null!");

		this.entries = messageRepository;
	}

	public void save(Message entry){
		entries.save(entry);
	}

	public Integer messageCount(String user){
		return (int)entries.findByUser(user).stream().count();
	}

	public Streamable<Message> findEntriesByUser(String user){
		return entries.findByUser(user);
	}

	public Streamable<Message> findAll() { return entries.findAll(); }
}
