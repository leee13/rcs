package me.leee13.utils.rcs.pub;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class RedisUtil {
	
	private final static Map<String, Integer> sequenceMap = new HashMap<>();
	private final static ConcurrentHashMap<String, Semaphore> sequenceToken = new ConcurrentHashMap<>();
	
	public static Integer put(String key, Integer value) throws Exception{
		
		Integer result = value;
		
		try {
			sequenceToken.putIfAbsent(key, new Semaphore(1));
			sequenceToken.get(key).acquire();
			sequenceMap.put(key, result);
			sequenceToken.get(key).release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Integer get(String key) throws Exception{
		
		Integer value = new Integer(0);
		
		try {
			Semaphore syncToken = sequenceToken.putIfAbsent(key, new Semaphore(1));
			
			if (null == syncToken) {
				sequenceToken.get(key).acquire();
				sequenceMap.put(key, value);
				sequenceToken.get(key).release();
			}else {
				syncToken.acquire();
				value = sequenceMap.get(key);
				syncToken.release();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}
	
}
