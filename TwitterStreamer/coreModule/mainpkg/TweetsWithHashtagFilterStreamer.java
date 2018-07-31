package mainpkg;

import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

import twitter4j.TwitterStreamFactory;
import twitter4j.TwitterStream;

public class TweetsWithHashtagFilterStreamer {
	private TwitterStream twitterstream;
	public TweetsWithHashtagFilterStreamer(TwitterStream paramTwitter){
		twitterstream =paramTwitter;
	}
	
	public void streamTweetsWithHashtags(String[] paramStr4Query,String strFilenameTweetsOut,String strFilenameUsersOut)
	{
		if(strFilenameUsersOut==null){
			strFilenameUsersOut="streamUsersOutput.dat";
		}
		if(strFilenameTweetsOut==null){
			strFilenameTweetsOut="streamTweetsOutput.txt";
		}
		
		String[] track=paramStr4Query;
		
		User tUser;
		
		File tweetsSavedFile=new File(strFilenameTweetsOut);
		File userSavedFile=new File(strFilenameUsersOut);
		File userCountSavedFile=new File("count"+strFilenameUsersOut);
		try {
			BufferedWriter bwTweets=new BufferedWriter(new FileWriter(tweetsSavedFile));
			FileOutputStream fosUsers=new FileOutputStream(userSavedFile);
			ObjectOutputStream oosUsers=new ObjectOutputStream(fosUsers);	
			Integer userCount=0;
			StatusListener listener=new HashtagStatusListener(bwTweets,oosUsers,fosUsers,userCount); 
			twitterstream.addListener(listener);
			twitterstream.filter(new FilterQuery(0, null,track));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to spideTweetsWithHashtags " + e.getMessage());
			System.exit(-1);
		}
		return;
	}
	
	public void streamTweetsWithHashtags(String[] paramStr4Query){
		streamTweetsWithHashtags(paramStr4Query,null,null);
	}
	
}
