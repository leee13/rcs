package me.leee13.utils.rcs.pub;

import me.leee13.utils.rcs.chain.ProcessorChain;
import me.leee13.utils.rcs.processor.DateProcessor;
import me.leee13.utils.rcs.processor.MD5Processor;
import me.leee13.utils.rcs.processor.SequenceProcessor;
import me.leee13.utils.rcs.processor.UUIDProcessor;

public class RCSUtil {
	
	private RCSUtil(){};
	
	private final static class RCSUtilHolder {
		private static RCSUtil instance = new RCSUtil();
	}
	
	public static RCSUtil getInstance(){
		return RCSUtilHolder.instance;
	} 
	
	public String createRandomCharSequence(String stringPattern){
		
		ProcessorChain processorChain = initProcessorChain();
		
		String result = processorChain.interpret(stringPattern).process().compile().getResult();
		
		return result;
	}
	
	private ProcessorChain initProcessorChain(){
		ProcessorChain processorChain = new ProcessorChain(new UUIDProcessor()); 
		ProcessorChain dateProcessorChain = new ProcessorChain(new DateProcessor());
		ProcessorChain md5ProcessorChain = new ProcessorChain(new MD5Processor());
		ProcessorChain sequenceProcessorChain = new ProcessorChain(new SequenceProcessor());
		
		processorChain.setNextProcessorChain(dateProcessorChain);
		dateProcessorChain.setNextProcessorChain(md5ProcessorChain);
		md5ProcessorChain.setNextProcessorChain(sequenceProcessorChain);
		return processorChain;
	}

}
