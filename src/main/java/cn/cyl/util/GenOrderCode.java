package cn.cyl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenOrderCode {

	public static String genOrderCode() {
		String format = "yyyyMMddHHmmss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(new Date());
		str = str + (int)((Math.random()*9 + 1)*1000);
		return str;
	}
}
