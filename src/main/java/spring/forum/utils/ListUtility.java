package spring.forum.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtility {
	public static List<String> searchForList(List<List<String>> list,
			String value) {
		// list(0) to HEAD. Zawiera id topicu/usera.
		if (list.size() == 1)
			return null;
		long longValue = Long.parseLong(value);

		int hi = list.size() - 1;
		int lo = 0;
		while (hi >= lo) {
			int temp = lo + ((hi - lo) / 2);
			long listElem = Long.parseLong(list.get(temp).get(0));
			if (listElem > longValue) {
				hi = temp - 1;
			} else if (listElem < longValue) {
				lo = temp + 1;
			} else {
				return list.get(temp);
			}
		}
		return null;

	}
	public static int searchForElement(List<List<String>> list,String listKey,String value) {
		List <String>elementsList=searchForList(list,listKey);
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
	
	public static void deleteElement(List<List<String>> list,String listKey,String value) {
		List <String>elementsList=searchForList(list,listKey);
		int index=searchForElement(list,listKey,value);
		if(index == -1)
			return;
		elementsList.remove(index);
	}
	

	public static void addElement(List<List<String>> list, String listKey,
			String value) {
		List<String> elementsList = searchForList(list, listKey);
		if(elementsList==null)
			addList(list,listKey);
		
		long longValue = Long.parseLong(value);
		int hi = elementsList.size() - 1;
		int lo = 1;
		while (hi >= lo) {
			int temp = lo + ((hi - lo) / 2);
			long listElem = Long.parseLong(elementsList.get(temp));
			if (listElem > longValue) {
				if (Long.parseLong(elementsList.get(temp - 1)) < longValue
						&& temp - 1 != 0)
					elementsList.add(temp - 1, value);
				hi = temp - 1;
			} else if (listElem < longValue) {
				if (Long.parseLong(elementsList.get(temp + 1)) > longValue
						&& temp + 1 < elementsList.size())
					elementsList.add(temp + 1, value);
				lo = temp + 1;
			} else
				elementsList.set(temp, value);
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
				if (Long.parseLong(list.get(temp - 1).get(0)) < longValue
						&& temp - 1 != 0)
					list.add(temp - 1, newList);

				hi = temp - 1;
			} else if (listElem < longValue) {
				if (Long.parseLong(list.get(temp + 1).get(0)) > longValue
						&& temp + 1 < list.size())
					list.add(temp + 1, newList);
				lo = temp + 1;
			} else {

			}
		}
	}
	
}
