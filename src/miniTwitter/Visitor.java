package miniTwitter;

//This is the Visitor interface for the Visitor Design Pattern
public interface Visitor {

	int visit(UserTotal total);
	int visit(MessagesTotal totalMessages);
	int visit(GroupTotal totalGroups);
	int visit(PositivePercentage positive);
	
}
