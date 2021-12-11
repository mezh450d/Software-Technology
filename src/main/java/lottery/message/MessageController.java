package lottery.message;

import lottery.community.Community;
import lottery.community.CommunityManagement;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class MessageController {
	private final MessageManagement management;
	private final CommunityManagement communityManagement;

	MessageController(MessageManagement management, CommunityManagement communityManagement) {
		this.management = management;
		this.communityManagement = communityManagement;
	}

	@GetMapping(path = "/message")
	String message(@LoggedIn UserAccount user, Model model ) {
		int messageCount = management.messageCount(user.getUsername());
			if(messageCount >= 10 && !communityManagement.findPersonalCommunities(user).isEmpty()){
				Message message = new Message(user, "Sie wurden aus der Gemeinschaft entfernt" ,
					"Sie haben schon 10 Mitteilungen für unzureichende Deckung erhalten",
					LocalDateTime.now());
				management.save(message);
				for (Community community : communityManagement.findPersonalCommunities(user)) {
					communityManagement.removeFromCommunity(community, user);
				}
		}


		model.addAttribute("messages", management.findEntriesByUser(user.getUsername()));
		return "message";
	}

}

