package com.gg.server.utils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;

/**
 * 加密解密工具
 */
public class CryptUtil {

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
	private static final String PASSWORD_CRYPT_KEY = "ihis-key";
	private final static String DES = "DES";

	/**
	 * MD5 摘要计算(byte[]).
	 *
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] md5(byte[] src) throws Exception {
		MessageDigest alg = MessageDigest.getInstance("MD5");
		return alg.digest(src);
	}

	/**
	 * SHA256 摘要计算(byte[]).
	 *
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] sha256(byte[] src) throws Exception {
		MessageDigest alg = MessageDigest.getInstance("SHA-256");
		return alg.digest(src);
	}

	/**
	 * MD5 摘要计算(String).
	 *
	 * @param src
	 *            String
	 * @throws Exception
	 * @return String
	 */
	public static String sha256(String src) throws Exception {
		return byte2hex(sha256(src.getBytes(ENCODING)));
	}

    /**
     * HmacSHA1 摘要计算(byte[])
     * @param src
     * @param key
     * @return
     * @throws Exception
     */
	public static byte[] hmacSha1(String src, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(ENCODING), MAC_NAME);
        Mac mac = Mac.getInstance(MAC_NAME);
        mac.init(secretKey);

        byte[] srcBytes = src.getBytes(ENCODING);
        return mac.doFinal(srcBytes);
    }

	/**
	 * MD5 摘要计算(String).
	 *
	 * @param src
	 *            String
	 * @throws Exception
	 * @return String
	 */
	public static String md5(String src) throws Exception {
		return byte2hex(md5(src.getBytes("UTF-8")));
	}

	/**
	 * 加密
	 *
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 解密
	 *
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);
	}

    /**
     * 生成key，作为加密和解密密钥且只有密钥相同解密加密才会成功
     * @return
     * @throws Exception
     */
    public static Key createKey(String aesKey) throws Exception {
        //获得对称密钥的字节数组
        byte[] keyBytes = generateKey(aesKey);
        // key转换,根据字节数组生成AES密钥
        Key key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }

    /**
     * 生成key
     * @param aesKey
     * @return
     * @throws Exception
     */
    private static byte[] generateKey(String aesKey) throws Exception{
        if(StringUtils.isBlank(aesKey)){
            // 生成key
            KeyGenerator keyGenerator;
            //构造密钥生成器，指定为AES算法,不区分大小写
            keyGenerator = KeyGenerator.getInstance("AES");
            //生成一个128位的随机源,根据传入的字节数组
            keyGenerator.init(128);
            //产生原始对称密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得原始对称密钥的字节数组
            byte[] keyBytes = secretKey.getEncoded();
            return keyBytes;
        }else {
            return aesKey.getBytes();
        }
    }

    /**
     * AES加密 ECB
     * @param data
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptAES(byte[] data, Key key) throws Exception {

        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //获取数据并加密
        return cipher.doFinal(data);
    }

    /**
     * AES解密 ECB方式
     * @param data
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptAES(byte[] data, Key key) throws Exception {

        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key);
        //获取数据并解密
        return cipher.doFinal(data);
    }

	/**
	 * 密码解密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public final static String decryptPassword(String data) {
		if (data != null)
			try {
				return new String(decrypt(hex2byte(data.getBytes()),
						PASSWORD_CRYPT_KEY.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 密码加密
	 *
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public final static String encryptPassword(String password) {
		if (password != null)
			try {
				return byte2hex(encrypt(password.getBytes(), PASSWORD_CRYPT_KEY
						.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 二行制转字符串
	 *
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuilder sb = new StringBuilder();
		String stmp = "";
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				sb.append("0").append(stmp);
			else
				sb.append(stmp);
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 二行制转页面字符串
	 *
	 * @param b
	 * @return
	 */
	public static String byte2webhex(byte[] b) {
		return byte2hex(b, "%");
	}

	/**
	 * 二行制转字符串
	 *
	 * @param b
	 * @param elide
	 *            分隔符
	 * @return
	 */
	public static String byte2hex(byte[] b, String elide) {
		StringBuilder sb = new StringBuilder();
		String stmp = "";
		elide = elide == null ? "" : elide;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				sb.append(elide).append("0").append(stmp);
			else
				sb.append(elide).append(stmp);
		}
		return sb.toString().toUpperCase();
	}

	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}


    /**
     * 对Base64字符串做URL安全的处理
     * url-safe variation emits - and _ instead of + and / characters
     * @param base64Str
     * @return
     * @throws Exception
     */
    public static String safeUrlBase64Encode(String base64Str) throws Exception {
        String safeBase64Str = base64Str.replace('+', '-');
        safeBase64Str = safeBase64Str.replace('/', '_');
        return safeBase64Str;
    }

	/**
	 * sha256_mac
	 * @param message
	 * @param key
	 * @return
	 */
	public static String sha256_mac(String message,String key) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(),"HmacSHA256");
		sha256_HMAC.init(secret_key);
		byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
		return byteArrayToHexString(bytes);
	}

	/**
	 * byteArrayToHexString
	 * @param b
	 * @return
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				sb.append('0');
			sb.append(stmp);
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 随机数据来源
	 */
	@SuppressWarnings("unused")
	private final static String[] UPPERCASE = { "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "0", "Q", "W", "E", "R", "T", "Y", "U", "I", "O",
			"P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C",
			"V", "B", "N", "M" };

	/**
	 * 随机数据来源
	 */
	@SuppressWarnings("unused")
	private final static String[] LOWERCASE = { "1", "2", "3", "4", "5", "6",
		"7", "8", "9", "0", "q", "w", "e", "r", "t", "y", "u", "i", "o",
		"p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c",
		"v", "b", "n", "m" };

	public static void main(String[] args) {
		String id = "441622199507152594" ;
		String encryptPassword = encryptPassword(id);
		System.out.println("加密：" + encryptPassword);
		System.out.println("解密：" + decryptPassword(encryptPassword));

//		String encryptPassword = "11F29C515631260A";
//		System.out.println(decryptPassword(encryptPassword));

		//微信跳转导诊地址 wx_url
//		String wx_url = "https://miying.qq.com/guide-h5/home?appid=wx2bb7bfdfb4ca346b";
//		String appId = "wx2bb7bfdfb4ca346b";//appid
//		String openId = "zsfyapp";//app对接传唯一设备号
//		String partnerId = "20000691";//腾讯分配合作方id
//		String timestamp = System.currentTimeMillis()+"";
//		//签名字符串key全部变小写
//		String message = "appid="+appId+"&openid="+openId+"&partnerid="+partnerId+"&timestamp="+timestamp;
//		String key = "2be24d85b11676ddabebf290ee9751e8";//腾讯分配合作方密钥
//		String signature = sha256_mac(message, key);
//		System.out.println("sign:"+signature);
//
//		//app跳转导诊地址app_url  loginType=h5 是表示此时是第三方app
//		String app_url = "https://miying.qq.com/guide-h5?appid="+appId+"&openId="+openId+"&partnerId="+partnerId+"&timestamp="+timestamp+"&signature="+signature+"&loginType=h5";
//		System.out.println("app_url:" + app_url);
	}
}

