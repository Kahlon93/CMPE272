package miniTwitter;

//This is the "element" interface of the Visitor Design Pattern
public interface Procedure {
	
	public int accept (Visitor visitor);

}
