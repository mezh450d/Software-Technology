package lottery.home.message;

import lottery.community.Community;
import lottery.community.CommunityManagement;
import lottery.user.User;
import lottery.user.UserManagement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageManagementTests {
	@Resource
	MessageManagement messageManagement;

	@Autowired
	UserManagement userManagement;

	@Autowired
	CommunityManagement communityManagement;

	User user;

	Message message;
	Message message1;

	Community community;

	@BeforeAll
	void before(){
		for(Message message : messageManagement.findAll()){
			messageManagement.deleteMessage(message.getId());
		}
		user = userManagement.findByUsername("testUser");
		message = new Message(user.getUserAccount(), "Mahnung", "Test", LocalDateTime.now());
		message1 = new Message(user.getUserAccount(), "Mahnung1", "Test1", LocalDateTime.now());
		messageManagement.save(message);
		messageManagement.save(message1);
//		communityManagement.createCommunity(new CreateForm("jackpot", "123"));
//		community = communityManagement.findCommunityByName("jackpot");
	}

	@Test
	void testAllMessage(){
		List<Message> messageList = messageManagement.findAll().toList();
		assertThat(messageList.size()).isEqualTo(2);
	}


	@Test
	void testNotReadMessage(){
		List<Message> messageList = messageManagement.findNotReadByUser(user.getUserAccount().getUsername()).toList();
		assertThat(messageList.size()).isEqualTo(2);
	}

	@Test
	void testReadMessage(){
		List<Message> messageList = messageManagement.findReadByUser(user.getUserAccount().getUsername()).toList();
		assertThat(messageList.isEmpty()).isTrue();
	}

	@Test
	void testAllMessageByUser(){
		List<Message> messageList = messageManagement.findEntriesByUser(user.getUserAccount().getUsername()).toList();
		assertThat(messageList.size()).isEqualTo(2);
	}

//	@Test
//	void testCheckTenMessage(){
//		community.addUser(user.getUserAccount());
//		assertThat(communityManagement.findPersonalCommunities(user.getUserAccount())).isNotNull();
//		Message message2 = new Message(user.getUserAccount(), "Mahnung2", "Test2", LocalDateTime.now());
//		Message message3 = new Message(user.getUserAccount(), "Mahnung3", "Test3", LocalDateTime.now());
//		Message message4 = new Message(user.getUserAccount(), "Mahnung4", "Test4", LocalDateTime.now());
//		Message message5 = new Message(user.getUserAccount(), "Mahnung5", "Test5", LocalDateTime.now());
//		Message message6 = new Message(user.getUserAccount(), "Mahnung6", "Test6", LocalDateTime.now());
//		Message message7 = new Message(user.getUserAccount(), "Mahnung7", "Test7", LocalDateTime.now());
//		Message message8 = new Message(user.getUserAccount(), "Mahnung8", "Test8", LocalDateTime.now());
//		Message message9 = new Message(user.getUserAccount(), "Mahnung9", "Test9", LocalDateTime.now());
//		messageManagement.save(message2);
//		messageManagement.save(message3);
//		messageManagement.save(message4);
//		messageManagement.save(message5);
//		messageManagement.save(message6);
//		messageManagement.save(message7);
//		messageManagement.save(message8);
//		messageManagement.save(message9);
//		assertThat(communityManagement.findPersonalCommunities(user.getUserAccount())).isNullOrEmpty();
//	}
	@AfterAll
	void after(){
		for(Message message : messageManagement.findAll()){
			messageManagement.deleteMessage(message.getId());
		}
	}
}
