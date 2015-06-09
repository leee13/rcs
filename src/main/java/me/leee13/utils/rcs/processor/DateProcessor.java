package me.leee13.utils.rcs.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProcessor implements Processor {
	
	private final String pattern = "\\$\\{D\\(([ymdsMH]*)\\)\\}";
	private final String default_formatPattern = "yyyyMMddHHmmss";

	@Override
	public String getProcessorPattern() {
		return this.pattern;
	}

	@Override
	public String doProcess(String param) {
		return createDateByParam(param);
	}
	
	private String createDateByParam(String datePattern){
		String formatPattern = (null == datePattern || "".equals(datePattern)) ? default_formatPattern : datePattern ;
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(new Date());
	}

}
