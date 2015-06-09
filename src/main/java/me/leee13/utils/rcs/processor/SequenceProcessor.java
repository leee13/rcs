package me.leee13.utils.rcs.processor;

import java.util.concurrent.ConcurrentHashMap;

import me.leee13.utils.rcs.pub.SequenceQueue;


public class SequenceProcessor implements Processor {
	
	private final String pattern = "\\$\\{S\\(([0-9]*)\\)\\}";
	private final int max_bits = 10;
	
	private final static ConcurrentHashMap<String, SequenceQueue> sequenceQueueCache = new ConcurrentHashMap<>();
	
	@Override
	public String getProcessorPattern() {
		return this.pattern;
	}
	
	@Override
	public String doProcess(String param) throws Exception{
		return createSequenceByParam(param);
	}
	
	/**
	 * 
	 * @param bits
	 * @return
	 */
	private String createSequenceByParam(String param) throws Exception{
		
		String result = "";
		
		String bits = ( null == param || "".equals(param) ) ? "0" : param;
		
		if (Integer.valueOf(bits) >= max_bits) {
			/*TODO throw new exception*/
		}else {
			
			SequenceQueue queue = sequenceQueueCache.putIfAbsent(bits, new SequenceQueue(bits));
			
			if (null == queue) {
				sequenceQueueCache.get(bits).fleshQueue();;
			}
			
			queue = sequenceQueueCache.get(bits);
			
			result = queue.nextVal();
		}
		
		return result;
	}

}
