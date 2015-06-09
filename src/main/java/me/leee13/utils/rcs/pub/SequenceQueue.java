package me.leee13.utils.rcs.pub;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class SequenceQueue {
	
	private final boolean isBeginFrom1 = false;
	private final String bits;
	private final Double max_sequence;
	private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	private final int load_sequences_per_time = 100;
	private final Thread t_fleshQueue;
	
	public SequenceQueue(String bits) throws Exception{
		this.bits = bits;
		this.max_sequence = Math.pow(10, Integer.valueOf(bits));
		
		this.t_fleshQueue = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if ( 0 == queue.size() ) {
						try {
							loadVal();
						} catch (Exception e) {
							//TODO
						}
						
					}
				}
			}
		});
		t_fleshQueue.setDaemon(true);
	}
	
	public void fleshQueue(){
		t_fleshQueue.start();
	}
	
	public String nextVal() throws Exception{
		
		String value = queue.take();
		return value;
	}

	private void loadVal() throws Exception{
		
		Integer value = RedisUtil.get(bits);
		
		Integer beginSequence = ( null == value ) ? (isBeginFrom1 ? 1 : 0) : value ;
		
		Integer initSequence = max_sequence.intValue() - beginSequence;
		
		if (initSequence > load_sequences_per_time) {
			
			IntStream.iterate(0, i -> i + 1).parallel().limit(load_sequences_per_time).forEach(new IntConsumer() {
				
				@Override
				public void accept(int value) {
					try {
						queue.put(String.format("%0" + bits + "d", value + beginSequence));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			
//			for (int i = 0 ; i < load_sequences_per_time; i++) {
//				queue.put(String.format("%0" + bits + "d", i + beginSequence));
//			}
			RedisUtil.put(bits, beginSequence + load_sequences_per_time);
		}else if (initSequence <= load_sequences_per_time && 0 < initSequence) {
			
			IntStream.iterate(0, i -> i + 1).parallel().limit(initSequence).forEach(new IntConsumer() {
				
				@Override
				public void accept(int value) {
					try {
						queue.put(String.format("%0" + bits + "d", value + beginSequence));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			
//			for (int i = 0 ; i < initSequence; i++) {
//				queue.put(String.format("%0" + bits + "d", i + beginSequence));
//			}
			RedisUtil.put(bits, max_sequence.intValue());
		}else {
			StringBuffer exSb = new StringBuffer("not enough sequences!!");
			exSb.append(beginSequence);
			throw new InterruptedException(exSb.toString());
		}
	}

}
