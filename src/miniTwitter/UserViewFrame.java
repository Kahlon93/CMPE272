package miniTwitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JList;

//This class is the Frame of the User View part of the assignment. It also represents
// an observable subject of the Observer Design Pattern.

public class UserViewFrame extends JFrame implements CustomObservable{
	
	private JPanel contentPane;
	private JTextArea userIdArea;
	private JTextArea messageArea;
	private JList followingsList;
	private JList newsFeedList;
	private DefaultListModel newsFeedListModel = new DefaultListModel();
	private DefaultListModel followingsListModel = new DefaultListModel();
	
	private int currentCount = 0;
	private int indexOfUser = 0;
	private User user;
	private List<User> usersList = AdminControlPanel.getUsersList();
	private List<String> followings = new ArrayList<String>();
	private ArrayList<String> userIdsList = AdminControlPanel.getUserIdsList();

	//Create the frame
    public UserViewFrame(User u) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 416, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		for (int i = 0; i < usersList.size(); i++) {
			if (usersList.get(i).getId().equals(u.getId())) {
				user = usersList.get(i);
				indexOfUser = i;
				break;
			}
		}
		
		currentCount = user.getFollowingsMessages().size();
		setTitle(user.getId());
		followings = u.getFollowings();
		if (!followings.isEmpty()) {
			for (int i = 0; i < followings.size(); i++) {
				followingsListModel.addElement(followings.get(i));
			}
		}
		if (currentCount > 0) {
			for (int i = 0; i < currentCount; i++) {
				newsFeedListModel.addElement(user.getFollowingsMessages().get(i));
			}
		}
		userIdArea = new JTextArea();
		userIdArea.setText("User Id");
		userIdArea.setBounds(10, 11, 232, 33);
		contentPane.add(userIdArea);
		
		JButton followButton = new JButton("Follow User");
		followButton.setBounds(252, 12, 134, 32);
		followButton.addActionListener(new followButtonListener());
		contentPane.add(followButton);
		
		followingsList = new JList(followingsListModel);
		followingsList.setBounds(10, 55, 376, 101);
		contentPane.add(followingsList);
		
		messageArea = new JTextArea();
		messageArea.setBounds(10, 167, 260, 33);
		messageArea.setText("Message tweet");
		contentPane.add(messageArea);
		
		JButton postTweetButton = new JButton("Post Tweet");
		postTweetButton.setBounds(280, 167, 106, 33);
		postTweetButton.addActionListener(new postTweetButtonListener());
		contentPane.add(postTweetButton);
		
		newsFeedList = new JList(newsFeedListModel);
		newsFeedList.setBounds(10, 211, 376, 105);
		contentPane.add(newsFeedList);
		
		JButton updateFeedButton = new JButton("Update Feed");
		updateFeedButton.setBounds(135, 327, 101, 33);
		updateFeedButton.addActionListener(new updateFeedButtonListener());
		contentPane.add(updateFeedButton);
	}
    
    //This method updates the feed of a user so as to include tweets of the other
    // users that the current user is following
    private class updateFeedButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			User u = usersList.get(indexOfUser);
		    System.out.println(u.getFollowingsMessages());
			
			if (currentCount < u.getFollowingsMessages().size()) {
				System.out.println("one");
				for (int i = currentCount; i < u.getFollowingsMessages().size(); i++) {
					newsFeedListModel.addElement(u.getFollowingsMessages().get(currentCount));
					//System.out.println("two");
					currentCount++;
				}
			}
		}
    	
    }
    

    //This method allows the user to follow another user, and creates a link between followers
    // and followings.
	private class followButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) { 
		
			String following = userIdArea.getText();
            int index = userIdsList.indexOf(following); //get index of user
            User newUser = new User(following);
            if (index == -1) { // If user doesnt exist..
    			userIdsList.add(following);
            	AdminControlPanel.addToUsersList(newUser);
            	newUser.addToFollowers(user.getId());
            	System.out.println("newUSer followers count: " + newUser.getFollowers().size() + newUser.getFollowers());
            	usersList.get(indexOfUser).addToFollowings(following);
            	usersList.add(newUser);
                followingsListModel.addElement(following);
            }
            else {
            	if (followingsListModel.indexOf(following) == -1) {
            		usersList.get(indexOfUser).addToFollowings(following);
            		usersList.get(index).addToFollowers(user.getId());
                    followingsListModel.addElement(following);
            	}
            }
		}

	}
	
	//This method posts tweet of the user to the newsFeed. It also notifies all the users
	// followers, hence using the Observer part of the Observable Design Pattern
	private class postTweetButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) { 
		
			String tweet = messageArea.getText();
			//tweets.add(tweet);
			user.addToMessages(tweet);
			newsFeedListModel.addElement(user.getId() + "- " + tweet);
			notifyObservers();
			
			
		}

	}

	@Override
	public void registerObserver(CustomObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(CustomObserver observer) {
		// TODO Auto-generated method stub
		
	}

	//This method notifies all the observers of the posted tweet.
	public void notifyObservers() {
		
		User followerUser = null;
		String followerName = "";
		for (int i = 0; i < user.getFollowers().size(); i++) {
			followerName = usersList.get(indexOfUser).getFollowers().get(i);
			for(int j = 0; j< usersList.size(); j++) {
				if (followerName.equals(usersList.get(j).getId())) {
					followerUser = usersList.get(j);
					break;
				}
			}
			followerUser.update(user.getId() + "- " + user.getMessages().get(user.getMessages().size()-1));
		}
		
	}
}
