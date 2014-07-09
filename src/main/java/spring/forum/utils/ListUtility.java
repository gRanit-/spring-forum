package spring.forum.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtility {
	public static List<String> searchForList(List<List<String>> list,
			String value) {
		
		if (list.size() == 0)
			return null;
		long longValue = Long.parseLong(value);

		int hi = list.size() - 1;
		int lo = 0;
		System.out.println("List size is: "+list.size());
		while (hi >= lo) {
			int temp = lo + ((hi - lo) / 2);
			long listElem = Long.parseLong(list.get(temp).get(0));
			System.out.println("listElem: "+listElem+"   ?   "+value);
			if (listElem > longValue) {
				hi = temp - 1;
			} else if (listElem < longValue) {
				lo = temp + 1;
			} else {
				System.out.println("GotList");
				return list.get(temp);
			}
		}
		return null;

	}

	public static int searchForElement(List<List<String>> list, String listKey,
			String value) {
		List<String> elementsList = searchForList(list, listKey);
		return searchForElement(elementsList, value);
	}

	public static int searchForElement(List<String> elementsList, String value) {
		long longValue = Long.parseLong(value);
		int hi = elementsList.size() - 1;
		int lo = 1;
		while (hi >= lo) {
			int temp = lo + ((hi - lo) / 2);
			long listElem = Long.parseLong(elementsList.get(temp));
			if (listElem > longValue) {
				hi = temp - 1;
			} else if (listElem < longValue) {
				lo = temp + 1;
			} else
				return temp;
		}
		return -1;

	}

	public static void deleteElement(List<List<String>> list, String listKey,
			String value) {
		List<String> elementsList = searchForList(list, listKey);
		int index = searchForElement(list, listKey, value);
		if (index == -1)
			return;
		elementsList.remove(index);
	}

	public static void addElement(List<List<String>> list, String listKey,
			String value) {
		List<String> elementsList = searchForList(list, listKey);
		if (elementsList == null) {
			addList(list, listKey);
			System.out.println("Cant find list adding new");
			elementsList = searchForList(list, listKey);
			try {
				Thread.sleep(1000*6);
			} catch (InterruptedException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//topic_topicID_userID
		long longValue = Long.parseLong(value.split("_")[1]);
		int hi = elementsList.size() - 1;
		
		// list(0) to HEAD. Zawiera id topicu/usera.
		int lo = 1;
		if(elementsList.size()==1) {
			elementsList.add(1,value);
			return;
		}
		while (hi >= lo) {
			int temp = lo + ((hi - lo) / 2);
			long listElem = Long.parseLong(elementsList.get(temp));
			if (listElem > longValue) {
				if (temp - 1 >= 0)
					if (Long.parseLong(elementsList.get(temp - 1)) < longValue)
						elementsList.add(temp - 1, value);
				hi = temp - 1;
			} else if (listElem < longValue) {
				if (temp + 1 < elementsList.size())
					if (Long.parseLong(elementsList.get(temp + 1)) > longValue)
						elementsList.add(temp + 1, value);
				lo = temp + 1;
			} else
				elementsList.set(temp, value);
			//System.out.println("added Element");
		}
		
	}

	public static void addList(List<List<String>> list, String listKey) {
		long longValue = Long.parseLong(listKey);
		List<String> newList = new ArrayList<String>();
		newList.add(listKey);
		int hi = list.size() - 1;
		int lo = 0;
		while (hi >= lo) {
			int temp = lo + ((hi - lo) / 2);
			long listElem = Long.parseLong(list.get(temp).get(0));
			if (listElem > longValue) {
				if (temp - 1 >= 0)
					if (Long.parseLong(list.get(temp - 1).get(0)) < longValue)
						list.add(temp - 1, newList);

				hi = temp - 1;
			} else if (listElem < longValue) {
				if (temp + 1 < list.size())
					if (Long.parseLong(list.get(temp + 1).get(0)) > longValue)
						list.add(temp + 1, newList);
				lo = temp + 1;
			} else {
				list.add(temp, newList);
				System.out.println("Added list "+listKey);
			}
		}
		if(list.size()==0) {
			list.add(newList);
			System.out.println("Added list "+listKey);
			}
	}

}
