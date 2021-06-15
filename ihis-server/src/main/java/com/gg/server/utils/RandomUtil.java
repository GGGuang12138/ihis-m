package com.gg.server.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * (随机数工具类)
 *
 * @author xiaojiang0229 2016年1月18日
 * @version 1.0
 */
public class RandomUtil {
	public static char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static SecureRandom sr = new SecureRandom();
	private static String RANDOM_STRING = "abcdefjhijkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";

	/**
	 * (此处写功能描述)
	 * @param dash 是否去破折号
	 * @param lower 是否转小写
	 * @param upper 是否转大写
	 *
	 * @author xiaojiang0229 2016年1月18日
	 * @version 1.0
	 */
	public static String newUUID(boolean dash, boolean lower, boolean upper) {
		String uuid = UUID.randomUUID().toString();
		if(dash){
			uuid = uuid.replaceAll("-", "");
		}
		if(lower){
			uuid = uuid.toLowerCase();
		}
		if(upper){
			uuid = uuid.toUpperCase();
		}
		return uuid;
	}

	/**
	 * (获取区间范围的整数)
	 *
	 * @author xiaojiang0229 2016年1月18日
	 * @version 1.0
	 */
	public static int rndInt(int min, int max) {
		return (int) (sr.nextDouble() * (max + 1 - min) + min);
	}

	/**
	 * 产生随机字符串
	 *
	 * @param len 长度
	 * @param digit 是否包含数字
	 * @param lower 是否包含小写字母
	 * @param upper 是否包含大写字母
	 * @return
	 */
	public static String randomString(int len, boolean digit, boolean lower, boolean upper) {
		StringBuilder source = new StringBuilder();
		if (digit) {
			source.append(Arrays.copyOfRange(HEX_DIGITS, 0, 10));
		}
		if (lower) {
			source.append(RANDOM_STRING.substring(0, 25));
		}
		if (upper) {
			source.append(RANDOM_STRING.substring(25));
		}
		return randomString(source.toString(), len);
	}

	public static String randomString(String source, int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int r = rndInt(0, source.length() - 1);
			sb.append(source.substring(r, r + 1));
		}
		return sb.toString();
	}

	/**
	 * 产生一个包含大小写字母的随机字符串
	 *
	 * @param len 长度
	 * @return
	 */
	public static String randomString(int len) {
		return randomString(RANDOM_STRING, len);
	}

	public static void main(String[] args) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < 2000 ; i++) {
			String randomString = RandomUtil.randomString(8, true, false, true);
			if(!result.contains(randomString)){
				result.add(randomString);
			}
		}
		System.out.println(result.toString());
	}
}
