package miniTwitter;

//This interface represents one the "element" interface of the Visitor Design Pattern
public class UserTotal implements Procedure{

	@Override
	public int accept(Visitor visitor) {
		// TODO Auto-generated method stub
		return visitor.visit(this);
	}

}
