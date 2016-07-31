package com.nearor.mylibrary.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {
	
	public static boolean isEmpty(Collection<?> list) {
		return list == null || list.isEmpty();
	}
	
	
	public static boolean isNotEmpty(Collection<?> list) {
		return !isEmpty(list);
	}
	
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
	
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}
}