package me.leee13.utils.rcs.chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.leee13.utils.rcs.processor.Processor;

public class ProcessorChain {
	
	private ProcessorChain nextProcessorChain;
	
	private Processor processor;
	
	private String sequencePattern;
	private List<Map<String, String>> patternMaps = new ArrayList<>();
	private String result;
	
	public ProcessorChain(Processor processor){
		this.processor = processor;
	}
	
	public ProcessorChain(Processor processor, ProcessorChain nextProcessorChain){
		this.processor = processor;
		this.nextProcessorChain = nextProcessorChain;
	}

	public ProcessorChain interpret(String stringPattern) {
		
		this.sequencePattern = stringPattern;
		
		if (null != nextProcessorChain) {
			nextProcessorChain.interpret(stringPattern);
		}
		
		doInterpret(this.sequencePattern, patternMaps);
		
		return this;
	}
	
	public void doInterpret(String sequencePattern, List<Map<String, String>> patterns) {
		Pattern p = Pattern.compile(processor.getProcessorPattern());
		Matcher m = p.matcher(sequencePattern);
		while (m.find()) {
			Map<String, String> patternMap = new HashMap<>();
			patternMap.put("pattern", m.group());
			patternMap.put("param", m.group(1));
			patterns.add(patternMap);
		}
	}
	
	public ProcessorChain process() {
		
		if (null != nextProcessorChain) {
			nextProcessorChain.process();
		}
		
		doProcess(patternMaps);
		
		return this;
	}
	
	public void doProcess(List<Map<String, String>> patterns) {
		for (Map<String, String> patternMap : patterns) {
			
			String param = patternMap.get("param");
			
			String result;
			
			try {
				result = processor.doProcess(param);
			} catch (Exception e) {
				result = e.getMessage();
			}
			
			patternMap.put("result", result);
			
		}
	}
	
	public ProcessorChain compile() {
		
		if (null != nextProcessorChain) {
			nextProcessorChain.compile();
			result = nextProcessorChain.getResult();
		}else {
			result = sequencePattern;
		}
		result = doCompile(patternMaps, result);
		return this;
	}
	
	public String doCompile(List<Map<String, String>> processedResult, String sequencePattern) {
		StringBuffer resultBuffer = new StringBuffer();
		
		Pattern p = Pattern.compile(processor.getProcessorPattern());
		Matcher m = p.matcher(sequencePattern);
		
		while (m.find()) {
			String replacement = "";
			for (Map<String, String> processedResultMap : processedResult) {
				String pattern = processedResultMap.get("pattern");
				if (null != pattern && pattern.equals(m.group())) {
					replacement = processedResultMap.get("result");
					processedResult.remove(processedResultMap);
					break;
				}
			}
			m.appendReplacement(resultBuffer, replacement);
		}
		m.appendTail(resultBuffer);
		return resultBuffer.toString();
	}
	
	public String getResult() {
		return this.result;
	}

	public void setNextProcessorChain(ProcessorChain nextProcessorChain) {
		this.nextProcessorChain = nextProcessorChain;
	}

}
