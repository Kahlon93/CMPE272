package miniTwitter;

//This interface represents one of the "element" interface of the Visitor Design Pattern
public class GroupTotal implements Procedure{

	@Override
	public int accept(Visitor visitor) {
		// TODO Auto-generated method stub
		return visitor.visit(this);
	}

}
