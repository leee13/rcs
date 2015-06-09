package me.leee13.utils.rcs.processor;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDProcessor implements Processor{
	
	private final String pattern = "\\$\\{U\\(([0-5a-z_]*)\\)\\}";
	private final String time_low = "1";
	private final String time_mid = "2";
	private final String time_high_and_version = "3";
	private final String variant_and_sequence = "4";
	private final String node = "5";

	@Override
	public String getProcessorPattern() {
		return this.pattern;
	}

	@Override
	public String doProcess(String param) {
		return createUUIDByParam(param);
	}
	
	/**
	 * UUID = <time_low> "-" <time_mid> "-" <time_high_and_version> "-" <variant_and_sequence> "-" <node> 
	 */
	private String createUUIDByParam(String index){
		
		String uuid = UUID.randomUUID().toString();
		
		Matcher uuidMatcher = Pattern.compile("(.*)-(.*)-(.*)-(.*)-(.*)").matcher(uuid);
		
		String result;
		
		String groupIndex = getGroupIndexByIndex(index);
		
		if (uuidMatcher.matches()) {
			if (null == groupIndex || "".equals(groupIndex) || "0".equals(groupIndex)) {
				/*when param eq 0 or empty, return entire uuid-string*/
				result = uuid;
			}else {
				result = uuidMatcher.group(Integer.valueOf(groupIndex));
			}
		}else {
			result = "";
		}
		
		return result;
	}

	private String getGroupIndexByIndex(String index) {
		String groupIndex;
		switch (index) {
		case "time_low":
			groupIndex = time_low;
			break;
		case "time_mid":
			groupIndex = time_mid;
			break;
		case "time_high_and_version":
			groupIndex = time_high_and_version;
			break;
		case "variant_and_sequence":
			groupIndex = variant_and_sequence;
			break;
		case "node":
			groupIndex = node;
			break;
		default:
			groupIndex = index;
			break;
		}
		return groupIndex;
	}

}
