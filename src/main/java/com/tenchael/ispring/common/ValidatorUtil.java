package com.tenchael.ispring.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ValidatorUtil {

	/**
	 * ����ַ������Ƿ�������������ַ��������������ַ�������ʹ��ͨ���'*'��
	 * 
	 * @param str
	 * @param search
	 * @return
	 */
	public static boolean contains(String str, String search) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(search)) {
			return false;
		}
		String reg = StringUtils.replace(search, "*", ".*");
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}

	/**
	 * �ı�תhtml
	 * 
	 * @param txt
	 * @return
	 */
	public static String txt2htm(String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
		char c;
		boolean doub = false;
		for (int i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			if (c == ' ') {
				if (doub) {
					sb.append(' ');
					doub = false;
				} else {
					sb.append("&nbsp;");
					doub = true;
				}
			} else {
				doub = false;
				switch (c) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				default:
					sb.append(c);
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * �����ı�����������˼��У������ı������"..."
	 * 
	 * @param s
	 *            ���ж���
	 * @param len
	 *            ����С��256����Ϊһ���ַ�������256����Ϊ�����ַ���
	 * @return
	 */
	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}
		// �����������ȫ��Ӣ�ģ�
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; count < maxCount && i < slen; i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (!StringUtils.isBlank(append)) {
				if (s.codePointAt(i - 1) < 256) {
					i -= 2;
				} else {
					i--;
				}
				return s.substring(0, i) + append;
			} else {
				return s.substring(0, i);
			}
		} else {
			return s;
		}
	}

	/**
	 * ��ȡIP��ַ
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// ��η���������ж��IPֵ����һ��Ϊ��ʵIP��
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * �ж�һ���ַ����Ƿ�Ϊ null �� ���ַ�
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		return StringUtils.isEmpty(input);
	}

	/**
	 * �ж�һ���ַ����Ƿ�Ϊ null �� ���ַ� �� �ո�
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isBlank(String input) {
		return StringUtils.isBlank(input);
	}

	public static String regexFilter(String regex, String content)
			throws PatternSyntaxException {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		return matcher.replaceAll("");

	}

	public static String filterSpecialChar(String content) {

		// ��������������ַ�
		String regex = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~��@#��%����&*��������+|{}������������������������]";
		return regexFilter(regex, content);

	}

	public static String filterUnSafeChar(String content) {

		content = filterHtml(content);
		String regex = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~��@#��%����&*����+|{}������������������������]";
		content = regexFilter(regex, content);
		return content;

	}

	public static String filterHtml(String content) {

		String regex = "<.+?>";
		return regexFilter(regex, content);

	}

	public static String filterHtmlA(String content) {

		String regex = "<.?a(.|\n)*?>";
		return regexFilter(regex, content);

	}

	public static String filterHtmlScript(String content) {

		// ��������������ַ�
		String regex = "<script((?:.|\\n)*?)</script>";
		return regexFilter(regex, content);

	}

	public static String filterHtmlImg(String content) {

		String regex = "<img(.|\\n)*?>";
		return regexFilter(regex, content);

	}

	public static String filterUrl(String content) {

		String regex = "\\w+://";
		return regexFilter(regex, content);

	}

	public static String filterHtmlDiv(String content) {

		String regex = "<.?div(.|\\n)*?>";
		return regexFilter(regex, content);

	}

	public static boolean isNumber(String input) {

		return input.matches("^\\d+$");

	}

	public static boolean isOnlyLetter(String input) {

		return input.matches("[^a-zA-Z]");

	}

	public static boolean isLetterAndNum(String input) {

		return input.matches("[^a-zA-Z0-9]");

	}

	public static boolean isIP(String ip) {
		String regex = "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$";
		return Pattern.matches(regex, ip);

	}

	public static boolean isEMail(String input) {

		return input
				.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

	}

}
