package com.rine.versionupdate.utils;

import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wu
 * @version 1.0(2018/4/24)
 * String 工具类
 */
public class StringUtil {
	private static final String PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
	/**
	 * 返回字符串, 以utf——8格式的字符串
	 *
	 * @param xml value
	 * @return
	 * @author g10000000 2015-12-8 下午8:19:13
	 * @since v1.0
	 */
	public static String getUTF8XMLString(String xml)
	{
		// A StringBuffer Object
		StringBuffer sb = new StringBuffer();
		sb.append(xml);
		String xmString = "";
		String xmlUTF8="";
		try {
			xmString = new String(sb.toString().getBytes("UTF-8"));
			xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return to String Formed
		return xmlUTF8;
	}

	/**
	 * 返回字符串, 如果value为null则, 返回为""
	 *
	 * @param value
	 * @return
	 * @author g10000000 2015-12-8 下午8:19:13
	 * @since v1.0
	 */
	public static String get(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	/**
	 * int转byte
	 * @param x a
	 * @return
	 */
	//byte 与 int 的相互转换
	public static byte intToByte(int x) {
		return (byte) x;
	}





	/**
	 * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
	 * @param value
	 *            要转换的int值
	 * @return byte数组
	 */
	public static byte[] intToBytes( int value )
	{
		byte[] src = new byte[4];
		src[3] =  (byte) ((value>>24) & 0xFF);
		src[2] =  (byte) ((value>>16) & 0xFF);
		src[1] =  (byte) ((value>>8) & 0xFF);
		src[0] =  (byte) (value & 0xFF);
		return src;
	}

	/**
	 *基于位移的 byte[]转化成int
	 * @param   bytes
	 * @return int  number
	 */

	public static int bytesToInt(byte[] bytes) {
		int number = bytes[0] & 0xFF;
		// "|="按位或赋值。
		number |= ((bytes[1] << 8) & 0xFF00);
		number |= ((bytes[2] << 16) & 0xFF0000);
		number |= ((bytes[3] << 24) & 0xFF000000);
		return number;
	}

	/**
	 * 返回字符串, 如果value为null则, 返回为""
	 *
	 * @param value
	 * @return
	 * @author g10000000 2015-12-8 下午8:19:13
	 * @since v1.0
	 */
	public static String get(String value, String defult) {
		if (value == null) {
			return defult;
		}
		return value;
	}

	/**
	 * 返回字符串, 如果value为null则, 返回为""
	 *
	 * @param value
	 * @return
	 * @author g10000000 2015-12-8 下午8:19:13
	 * @since v1.0
	 */
	public static String get(Integer value, String defult) {
		if (value == null) {
			return defult;
		}
		return String.valueOf(value);
	}

	/***
	 * 检查是否为手机格式
	 * @param phone
	 * @return
	 */
	public static Boolean isPhone(String phone) {
//		return Pattern.compile("^((13[0-9])|(15[0-3, 5-9])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$").matcher(phone).matches();
		return Pattern.compile( "^1[\\d]{10}$").matcher(phone).matches();
	}

	/**
	 * 检查是否为8-16位的手机号并且数字和英文结合
	 * @param checkStr
	 * @return
	 */
	public static Boolean checkRegex(CharSequence checkStr) {
		Pattern pattern = Pattern.compile(PASSWORD);
		Matcher matcher = pattern.matcher(checkStr);
		return matcher.matches();
	}

	/**
	 * 判断email格式是否正确
	 */
	public static boolean isEmail(String email) {

		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

		Pattern p = Pattern.compile(str);

		Matcher m = p.matcher(email);

		return m.matches();

	}

	/**
	 * 判断账号格式是否正确
	 */
	public static boolean isAccounNumber(String accounNumber) {

		String str = "^[a-zA-Z]\\w{3,9}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(accounNumber);
		return m.matches();
	}


	/**
	 * 判断账号格式是否正确
	 */
	public static boolean isPassword(String password) {

		String str = "^\\S{3,9}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(password);
		return m.matches();
	}

	/**
	 * uuid
	 * @return a random uuid
	 * @author dengdan 2015-4-10 下午5:10:15
	 * @since v1.0
	 */
	public final static String getUuid() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}

	/**
	 * 生成一个随机整数
	 *
	 * @return 随机整数
	 * @author dengdan 2015-4-10 下午5:48:18
	 * @since v1.0
	 */
	public final static int getRandomInt() {
		Random rand = new Random();
		return rand.nextInt();
	}

	/**
	 *
	 * @param length
	 *            数字字符串长度
	 * @return 随机数字字符串
	 * @author dengdan 2015-6-8 下午6:16:48
	 * @since v1.0
	 */
	public final static String getRandomIntString(int length) {
		Random r = new Random();
		String str = "";
		for (int i = 0; i < length; i++) {
			str += r.nextInt(10);
		}
		return str;
	}

	public final static String removeSpace(String str)
	{
		String str2 = str.replaceAll(" ", "");
		return str2;
	}

	/**
	 * 获得当前时间
	 * @return 时间
	 */
	public static String getTime(){
		long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1=new Date(time);
		String t1=format.format(d1);
		return t1;
	}

	/**
	 * 获得当前时间
	 * @return 时间
	 */
	public static String getCusYear(){
		long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
		SimpleDateFormat format=new SimpleDateFormat("yyyy");
		Date d1=new Date(time);
		String t1=format.format(d1);
		return t1;
	}

	/**
	 * 去除所有空格
	 * @param str
	 * @return
	 */
	public static String clearSpace(String str ){
		str = str.replaceAll(" ","");
		return str;
	}
	/**
	 *
	 * 判断字符串是否为空(若只有空格, 也判定为空)
	 *
	 * @param str
	 * @return 字符串是否为空
	 * @author dengdan 2015-4-10 下午5:59:52
	 * @since v1.0
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.trim().equals("")){
			return true;
		}
		return false;
	}

	/**
	 * 如果为空则填""
	 * @param value
	 * @return
	 */
	public static String isNullFill(String value){
		if (StringUtil.isNullOrEmpty(value)){
			return "";
		}
		return value;
	}


	/**
	 * 如果为空则填nullVaule传递的值
	 * @param value
	 * @param nullVaule
	 * @return
	 */
	public static String isNullFill(String value,String nullVaule){
		if (StringUtil.isNullOrEmpty(value)){
			return nullVaule;
		}
		return value;
	}


	/**
	 * 经纬度如果为0，则为空
	 * @return
	 */
	public static String isLatLngIsNull(double latlng){
		if ((int)latlng == 0){
			return "";
		}
		return String.valueOf(latlng);
	}

	public static void copyToClipboard(Context context,String text){
		ClipboardManager clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
		//clip.getText(); // 粘贴
		clip.setText(text); // 复制
	}

	/**
	 * 如果为空则填暂无
	 * @param value
	 * @return
	 */
	public static String isNullToNo(String value){
		return isNullFill(value,"暂无");
	}

	/**
	 *
	 * 检验字符串的格式是否匹配正则表达式
	 *
	 * @param regex
	 *            正则
	 * @param str
	 *            要检验的字符串
	 * @return 是否匹配
	 * @author dengdan 2015-4-14 下午12:19:14
	 * @since v1.0
	 */
	public static boolean match(String regex, String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.lookingAt();
	}

	/**
	 * 只含有汉字、数字、字母、下划线，下划线位置不限
	 *
	 * @param str
	 *            字符串
	 * @return
	 * @author c99999999 2015-8-5 下午3:59:06
	 * @since v1.0
	 */
	public static boolean legitimacyJudgment(String str) {
		String regEx = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
		return match(regEx, str);
	}

	/**
	 * 数字 判断
	 *
	 * @param str
	 * @return
	 * @author c99999999 2015-8-5 下午4:00:17
	 * @since v1.0
	 */
	public static boolean numberJudgment(String str) {
		String regEx = "^(0|[1-9][0-9]*)$";
		return match(regEx, str);
	}

	/**
	 * 将字符串解析成Boolean值
	 *
	 * @param str
	 * @param dft
	 *            default value to be returned when str is not a valid bool
	 *            string
	 * @return boolean
	 * @author dengdan 2015-4-14 下午1:32:45
	 * @since v1.0
	 */
	public static boolean getBoolean(String str, boolean dft) {
		boolean v = true;
		try {
			v = Boolean.parseBoolean(str);
		} catch (Exception e) {
			return dft;
		}
		return v;
	}

	/**
	 * 将字符串解析成long
	 *
	 * @param str
	 *            string
	 * @param dft
	 *            解析异常时返回的默认值
	 * @return long
	 * @author dengdan 2015-4-21 下午4:20:50
	 * @since v1.0
	 */
	public static long getLong(String str, long dft) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return dft;
		}
	}

	/**
	 * 将字符串解析成int
	 *
	 * @param str
	 * @param dft
	 * @return int
	 * @author dengdan 2015-6-2 下午7:51:53
	 * @since v1.0
	 */
	public static int getInt(String str, int dft) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return dft;
		}
	}



	/**
	 * 判断字符器是否不为空
	 *
	 * @param str
	 * @return 是否不为空
	 * @author dengdan 2015-5-12 下午3:29:48
	 * @since v1.0
	 */
	public static boolean notNullOrEmpty(String str) {
		return !isNullOrEmpty(str);
	}

	/**
	 * 截取字符串
	 * @param str 字符串
	 * @param start 开始
	 * @param end 结束
	 * @return 返回截取的字符串
	 */
	public static String getintercept(String str,int start,int end)
	{
		str = str.substring(start, end);
		return str;
	}

	/**
	 * 截取字符串
	 * @param str 字符串
	 * @param sub 截至字符
	 * @return 返回截取的字符串
	 */
	public static String getString(String str, String sub)
	{
		String N = str;
		String L="";
		String R="";
		int k= N.length();
		for (int i = 0; i < N.length(); i++)
		{
			String value = N.substring(i, i + 1);
			if (value.equals(sub))
			{
				L = N.substring(0,i).trim();
				R = N.substring(i+1,k).trim();
				break;
			}
			else
			{
				L = str;
			}
		}
		return L;
	}


	/**
	 * 截取数字
	 * @param num 字符串
	 * @param start 开始
	 * @param end 结束
	 * @return 返回截取的字符串
	 */
	public static String getintercept(int num,int start,int end)
	{
		String str;
		str = String.valueOf(num);
		str = str.substring(start, end);
		return str;
	}

	/**
	 * sha加密
	 *
	 * @param toBeEncrypted
	 * @param shaType
	 * @return encrypted String
	 * @throws NoSuchAlgorithmException
	 *             当shaType不存在时抛出
	 * @author c99999999 2015-6-24 上午11:19:48
	 * @since v1.0
	 */
	private static String eccrypt(String toBeEncrypted, String shaType)
			throws NoSuchAlgorithmException {
		MessageDigest sha = MessageDigest.getInstance(shaType);
		byte[] srcBytes = toBeEncrypted.getBytes();

		// 使用srcBytes更新摘要
		sha.update(srcBytes);

		// 完成哈希计算，得到result
		byte[] resultBytes = sha.digest();
		String encrypted = hexString(resultBytes);
		return encrypted;
	}

	private static String eccryptSHA1(String info) {
		try {
			return eccrypt(info, "SHA1");
		} catch (NoSuchAlgorithmException e) {
			/*
			 * 可以肯定不会抛出这个异常
			 */
			e.printStackTrace();
		}
		return null;
	}

	private static String eccryptSHA256(String info) {
		try {
			return eccrypt(info, "SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String eccryptSHA384(String info) {
		try {
			return eccrypt(info, "SHA-384");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	// private static String eccryptSHA512(String info){
	// try {
	// return eccrypt(info, "SHA-512");
	// } catch (NoSuchAlgorithmException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	private static String hexString(byte[] bytes) {
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int val = (bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 加密密码
	 *
	 * @param pwd
	 * @return
	 * @author c99999999 2015-6-24 上午11:28:19
	 * @since v1.0
	 */
	public static String encryptPwd(String pwd) {
		String pwdEncryption = null;
		String sha1 = eccryptSHA1(pwd);
		String sha384 = eccryptSHA384(sha1);
		String sha256 = eccryptSHA256(sha384);
		pwdEncryption = md5(sha256);
		return pwdEncryption;
	}

	/**
	 * 将byte数组转换成16进制的字符串
	 *
	 * @param bytes
	 * @return
	 * @author c99999999 2015-6-24 上午11:22:35
	 * @since v1.0
	 */
	public static String byteArrayToHexString(byte[] bytes) {
		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			int val = (bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 对字符串进行md5加密
	 *
	 * @param str
	 *            要加密的字符串
	 * @return 加密后的字符串
	 * @param str
	 * @return
	 * @author c99999999 2015-6-24 上午11:25:51
	 * @since v1.0
	 */
	public static String md5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] srcBytes = str.getBytes();
		md5.update(srcBytes);
		byte[] resultBytes = md5.digest();
		String md5Str = byteArrayToHexString(resultBytes);
		return md5Str;
	}

	/**
	 * 将数组连接成字符串
	 *
	 * @param array
	 * @param joiner
	 *            连接字符
	 * @return
	 * @author c99999999 2015-6-27 下午3:08:00
	 * @since v1.0
	 */
	public static String join(Object[] array, String joiner) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i].toString());
			if (i < array.length - 1) {
				sb.append(joiner);
			}
		}
		return sb.toString();
	}

	/**
	 * 将数组连接成字符串
	 *
	 * @param array
	 * @param joiner
	 *            连接字符
	 * @return
	 * @author c99999999 2015-6-27 下午3:08:00
	 * @since v1.0
	 */
	public static String join(byte[] array, String joiner) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
			if (i < array.length - 1) {
				sb.append(joiner);
			}
		}
		return sb.toString();
	}

	/**
	 * 将list连接成字符串
	 *
	 * @param list
	 * @param joiner
	 *            连接字符
	 * @return
	 * @author c99999999 2015-6-27 下午3:08:00
	 * @param <T>
	 * @since v1.0
	 */
	public static <T> String join(List<T> list, String joiner) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				sb.append(list.get(i).toString());
				if (i < list.size() - 1) {
					sb.append(joiner);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符串转换为字符串组
	 * @param  s 字符串
	 * @param i 循环变量
	 * @return 字符串组[循环变量]
	 */
	public static String getSubString(String s,int i,String join)
	{
		String subS[] = s.split(join);
		return subS[i];
	}


	/**
	 * 2017-5-19
	 * 将字符串转换为字符串组
	 * @param s 字符串
	 * @param i 循环变量
	 * @return 字符串组[循环变量]
	 */
	public static String getSubString0(String s,int i)
	{
		try {
			String subS[] = s.split("&");
			if (subS[i].equals("")) {
				return "未知";
			}else if(subS[i] == null){
				return "未知";
			}else{
				return subS[i];
			}
		} catch (Exception e) {
			return "未知";
		}

	}

	public static String getSubString2(String s,int i)
	{
		String subS[] = s.split(",.:");
		return subS[i];
	}

	/**
	 * 将字符串转换为字符串组
	 * @param s 字符串
	 * @return 字符串组[循环变量]
	 */
	public static byte[] getSubString(String s)
	{
		String subS[] = s.split(",");
		int len = subS.length;
		byte[] sub = new byte[len];
		for (int i = 0; i < len; i++) {
			int iSub = Integer.parseInt(subS[i]);
			sub[i] = (byte) iSub;
		}

		return sub;
	}

	public static boolean isMatch(String name) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		return match(regEx, name);
	}

	/**
	 * 判断是否为手机号(+86)
	 *
	 * @param mobiles
	 * @return
	 * @author c99999999 2015-7-22 上午10:07:27
	 * @since v1.0
	 */
	public static boolean isMobileNO(String mobiles) {
		String regEx = "^[1][34578][0-9]{9}$";
		return match(regEx, mobiles);
	}


	/**
	 * 校验身份证
	 *
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard)
	{
		String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 检测是否有emoji表情
	 *
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是Emoji
	 *
	 * @param codePoint
	 *            比较的单个字符
	 * @return
	 */
	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 拆分集合 List<List<String>> ret=split(resList,10);
	 *
	 * @param <T>
	 * @param resList
	 *            要拆分的集合
	 * @param count
	 *            每个集合的元素个数
	 * @return 返回拆分后的各个集合
	 */
	public static <T> List<List<T>> split(List<T> resList, int count) {

		if (resList == null || count < 1) {
			return null;
		}
		List<List<T>> ret = new ArrayList<List<T>>();
		int size = resList.size();
		if (size <= count) { // 数据量不足count指定的大小
			ret.add(resList);
		} else {
			int pre = size / count;
			int last = size % count;
			// 前面pre个集合，每个大小都是count个元素
			for (int i = 0; i < pre; i++) {
				List<T> itemList = new ArrayList<T>();
				for (int j = 0; j < count; j++) {
					itemList.add(resList.get(i * count + j));
				}
				ret.add(itemList);
			}
			// last的进行处理
			if (last > 0) {
				List<T> itemList = new ArrayList<T>();
				for (int i = 0; i < last; i++) {
					itemList.add(resList.get(pre * count + i));
				}
				ret.add(itemList);
			}
		}
		return ret;
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是long
	 */
	public static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isLong(value);
	}

	/**
	 * 字符串比较大小
	 * @param a
	 * @param b
	 * @return 大于0 表示 a>b, 小于0 表示 a<b, 等于0 表示a=b, 均以ascii码值大小作为比较
	 * @author g10000000 2016年1月6日 下午5:41:42
	 * @since v1.0
	 */
	public static int compare(String a, String b) {
		/*
		 * 如果a或者b为null, 视为空字符串
		 */
		a =  a == null ? "" : a;
		b =  a == null ? "" : b;

		/*
		 * 获取字符串长度并开始比较
		 */
		int aLength = a.length();
		int bLength = b.length();
		int length = aLength > bLength ? bLength : aLength; //获取长度最小的字符串, 相等时取aLength
		for(int i = 0; i < length; i++) {
			int result = a.charAt(i)-b.charAt(i);
			if(result != 0) {
				return result; //如果在第i位不相等, 直接返回相减结果
			}
		}
		return aLength - bLength; //如果以最小字符串长度内每一位都相等, 返回长度的相减结果
	}

	/**
	 * textview显示html
	 * @param html
	 * @return
	 */
	public static Spanned displayhtml(String html)
	{
		return Html.fromHtml(html);
	}

	/**
	 * html自适应 加载html最后面
	 * @return
	 */
	public static String htmlStyle()
	{
		String style =  "<script type=\"text/javascript\">"
				+  "var tables = document.getElementsByTagName('table');"  // 找到table标签
				+ "for(var i = 0; i<tables.length; i++){"     // 逐个改变
				+ "tables[i].style.width = '100%';"   // 宽度改为100%
				+ "tables[i].style.height = 'auto';"
				+ "}" + "</script>" ;
		return style;
	}


	/**
	 * 获得字符串中某个值的出现次数
	 * @param str
	 * @param key
	 * @return
	 */
	public static int getstringSize(String str,char key)
	{
		int sum=1;
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(i) == key)
			{
				sum++;
			}

		}
		return sum;
	}

	/**
	 * 分割字符串
	 * @param str
	 * @param cutStr
	 * @return
	 */
	public static List<String> getStringCut(String str,String cutStr){
		List<String> vaule = new ArrayList<>();
		String[] strarray=str.split(cutStr);
		for (int i = 0; i < strarray.length; i++){
			vaule.add(strarray[i]);
		}
		return vaule;
	}
	/**
	 * 截取字符串
	 * @param  str 字符串
	 * @param sub 截至字符
	 * @return 返回截取的字符串
	 */
	public static String getStringR(String str, String sub)
	{
		String N = str;
		String L="";
		String R="";
		int k= N.length();
		for (int i = 0; i < N.length(); i++)
		{
			String value = N.substring(i, i + 1);
			if (value.equals(sub))
			{
				L = N.substring(0,i).trim();
				R = N.substring(i+1,k).trim();
				break;
			}
			else
			{
				L = str;
			}
		}
		return R;
	}


	/**
	 * 截取图片名(从后面开始取)
	 */
	public static String getImgName(String url,char sub)
	{
		int len = url.length();
		String s = "" ;
		String q = "" ;
		for(int i = len - 1; i > 0; i--)
		{
			char c = url.charAt(i);
			if(c != sub)
			{
				url.substring(0, i + 1);
				s = s + c;
			}
			else
			{
				break;
			}
		}
		int len1 = s.length();
		for(int i = len1 - 1; i >= 0; i--)
		{
			char c1 = s.charAt(i);
			q = q + c1;
		}
		return q;
	}

	/**
	 * 删除重复字符
	 * @param str 字符串
	 * @param deleteStr 删除的字符串
	 * @param keepNum 保留字符，从1开始
	 * @return
	 */
	public static String deleteStr(String str,String deleteStr,int keepNum){
		keepNum = keepNum - 1;
		String deleteStrs = "";
		if (deleteStr.equals(".") || deleteStr.equals("|")) {
			deleteStrs = "//"+deleteStr ;
		}
		String res = "";
		String[]  strs=str.split(deleteStrs);
		if (keepNum>=strs.length -1) {
			keepNum = -1;
		}
		for(int i=0,len=strs.length;i<len;i++){
//			System.out.println(strs[i]);
			if (i==keepNum) {
				res = res + strs[i].toString()+deleteStr;
			}else{
				res = res + strs[i].toString();
			}
		}
		return res;
	}

	/**
	 * 时时间比较
	 * @param DATE1
	 * @param DATE2
	 * @return 1为 date1在前，-1为date2前，0为出错
	 */
	public static int compareDate(String DATE1, String DATE2)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
//				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
//				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 时时间比较
	 * @param DATE1
	 * @param DATE2
	 * @return 1为 date1在前，-1为date2前，0为出错
	 */
	public static int compareDate4(String DATE1, String DATE2)
	{
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() < dt2.getTime()) {
//				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() > dt2.getTime()) {
//				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 时时间比较
	 * @param DATE1
	 * @param DATE2
	 * @return 1为 date1在大，-1为date2大，0为出错
	 */
	public static int compareDate2(String DATE1, String DATE2)
	{
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
//				LogUtil.logTag("d1大");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
//				LogUtil.logTag("d2大");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 时时间比较
	 * @param DATE1
	 * @param DATE2
	 * @return 1为 date1在大，-1为date2大，0为出错
	 */
	public static int compareDate3(String DATE1, String DATE2)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
//				LogUtil.logTag("d1大");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
//				LogUtil.logTag("d2大");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}


	/**
	 *  将字符串转为时间戳
	 * @param user_time
	 * @return
	 */
	public static String getTime(String user_time)
	{
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		Date d;
		try {


			d = sdf.parse(user_time);
			long l = d.getTime();
			re_time = String.valueOf(l/1000);
//		String str = String.valueOf(l);
//		re_time = str.substring(0, 10);


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}

	/**
	 *  将字符串转为时间戳
	 * @param user_time
	 * @return
	 */
	public static String getTime2(String user_time)
	{
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		Date d;
		try {

			d = sdf.parse(user_time);
			long l = d.getTime();
			re_time = String.valueOf(l/1000);
//		String str = String.valueOf(l);
//		re_time = str.substring(0, 10);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}




	/**
	 * 获取时间戳
	 * @return
	 */
	public static String getCurrentimeMillis(){
		long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
		String str=String.valueOf(time);
		return str;
	}


	/**
	 *  将时间戳转为字符串  ,date and time
	 * @param cc_time
	 * @return
	 */
	public static String getStrTime2(String cc_time)
	{
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));


		return re_StrTime;
	}

//	/**
//	 *  将一个时间格式转成另外一个时间格式
//	 * @param cc_time
//	 * @return
//	 */
//	public static String getStrTime3(String cc_time)
//	{
//		String date1="";
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.ENGLISH);
//			String Sdate = cc_time.toString();
//			Date date = sdf.parse(Sdate);
//
//			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss" );
//			date1=sdf2.format(date);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return date1;
//	}

	/**
	 *  将一个时间格式转成另外一个时间格式
	 * @param cc_time
	 * @return
	 */
	public static String getStrTime4(String cc_time)
	{
		String date1="";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.ENGLISH);
			Date date = sdf.parse(cc_time.toString());

			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
			date1=sdf2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1;
	}
	/**
	 * 将string转成date
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) {
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			// Fri Feb 24 00:00:00 CST 2012
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//        // 2012-02-24
//        date = java.sql.Date.valueOf(str);

		return date;
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param  pTime     设置的需要判断的时间  //格式如2012-09-08
	 *
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static String getWeek(String pTime) {
		String Week = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {

			c.setTime(format.parse(pTime));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "天";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "一";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "二";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "三";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "四";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "五";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "六";
		}
		return Week;
	}

	/**
	 * 将string转成date
	 * @param str
	 * @return
	 */
	public static Date stringToTime(String str) {
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		Date date = null;
		try {
			// Fri Feb 24 00:00:00 CST 2012
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//        // 2012-02-24
//        date = java.sql.Date.valueOf(str);

		return date;
	}

	/**
	 * 返回当前程序版本名
	 * 1为name
	 * 2为code
	 */
	public static String getAppVersionName(int version, Context context) {
		String versionName = "";
		String versioncode = "";
		String res = "";
		try {
			// ---get the package info---
			PackageManager pm =  context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			versioncode = String.valueOf(pi.versionCode);
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
//			LogUtil.logTag("Exception:"+e.toString());
		}
		if(version ==1){
			res=versionName;
		}else if(version ==2){
			res=versioncode;
		}
		return res;

	}

	/**
	 * 文字缩进2行
	 * @param context
	 * @return
	 */
	public static SpannableStringBuilder ToSuoJin(String context){
		String text = StringUtil.ToDBC(context);
		SpannableStringBuilder span = new SpannableStringBuilder("缩进"+text);
		span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
				Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return span;
	}
	/**
	 * 整理文字
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		String text = new String(c);
		String text2 = stringFilter(text);
		return text2;
	}

	/**
	 * 半角转全角
	 * @param input String.
	 * @return 全角字符串.
	 */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}
	/**
	 * 去除特殊字符或将所有中文标号替换为英文标号
	 *
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}


	/**
	 * 获取用户手机API
	 * @return
	 */
	public static int getApi(){
		return Build.VERSION.SDK_INT;
	}
	/**
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	/**
	 * 获取屏幕高度
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/*
	 * 检查手机上是否安装了指定的软件
	 *
	 * @param context
	 *
	 * @param packageName：应用包名
	 *
	 * @return
	 */
	public static boolean isAvilible(Context context, String packageName) {
		// 获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		// 用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<String>();
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (packageInfos != null) {
			for (int i = 0; i < packageInfos.size(); i++) {
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		// 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
		return packageNames.contains(packageName);
	}
	/**
	 * 获取当前应用程序的包名
	 * @param context 上下文对象
	 * @return 返回包名
	 */
	public static String getAppProcessName(Context context) {
		//当前应用pid
		int pid = android.os.Process.myPid();
		//任务管理类
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		//遍历所有应用
		List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo info : infos) {
			if (info.pid == pid)//得到当前应用
				return info.processName;//返回包名
		}
		return "";
	}

}
