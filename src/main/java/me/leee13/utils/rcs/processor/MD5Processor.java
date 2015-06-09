package me.leee13.utils.rcs.processor;

import java.util.UUID;

import me.leee13.utils.rcs.pub.MD5Util;

public class MD5Processor implements Processor {
	
	private final String pattern = "\\$\\{M\\(([^\\{\\}]*)\\)\\}";

	@Override
	public String getProcessorPattern() {
		return this.pattern;
	}
	
	@Override
	public String doProcess(String param) {
		return createMd5ByParam(param);
	}
	
	private String createMd5ByParam(String param){
		String string = ( null == param || "".equals(param) ) ? UUID.randomUUID().toString() : param;
		return MD5Util.md5(string);
	}

}
