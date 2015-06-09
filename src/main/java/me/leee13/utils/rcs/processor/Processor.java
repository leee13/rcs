package me.leee13.utils.rcs.processor;

public interface Processor {

	String doProcess(String param) throws Exception;
	
	String getProcessorPattern();

}
