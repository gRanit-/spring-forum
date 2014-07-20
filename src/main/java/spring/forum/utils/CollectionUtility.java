package spring.forum.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollectionUtility {

	public static class Element {
		public Long userID ;
		public Long postID ;
		public Long topicID;
		
		public Element(Long userID, Long postID, Long topicID) {
			this.userID =userID;
			this.postID = postID;
			this.topicID = topicID;
		}
		public String getString() {
			if(postID==-1)
				return "t_"+userID+"_"+topicID;
			else return "p_"+topicID+"_"+postID;
		}
	}

	public static class ComparatorUtil implements Comparator<Element> {

		public int compare(Element o1, Element o2) {
			if (o1.postID == -1) {
				if (o1.userID - o2.userID == 0) {
					return  (o1.topicID.compareTo(o2.topicID));
				} else
					return o1.userID.compareTo(o2.userID);
			} else if (o1.topicID - o2.topicID == 0) {
				return o1.postID.compareTo(o2.postID);
			} else
				return o1.topicID.compareTo(o2.topicID);

		}
	}
	
	public static void addElement(Element e) {
		
		
	}
	

}
