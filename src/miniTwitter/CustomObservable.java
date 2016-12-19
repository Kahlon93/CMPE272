package miniTwitter;

//This interface represents the Observable of the Observer Design Interface
public interface CustomObservable {
	
	public void registerObserver(CustomObserver observer);

    public void removeObserver(CustomObserver observer);

    public void notifyObservers();


}

