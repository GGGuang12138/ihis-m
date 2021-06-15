package com.gg.server.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtils {

	private static Pattern format_pattern = Pattern.compile("\\{.*?\\}");
	/**
	 * 格式化
	 * format("I {} you", "love") -> I love you
	 * @param pattern
	 * @param params
	 * @return
	 */
	public static String format(String pattern, Object...params){
		if(pattern != null && params != null && params.length > 0){
			Matcher mather = format_pattern.matcher(pattern);
			int i = 0;
			while(mather.find()){
				pattern = mather.replaceFirst(String.valueOf(params[i]));
				if(i >= params.length - 1){
					break;
				}
				i++;
				mather.reset(pattern);
			}
		}
		return pattern;
	}

}

