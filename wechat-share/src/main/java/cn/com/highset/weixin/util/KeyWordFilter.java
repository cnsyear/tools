package cn.com.highset.weixin.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class KeyWordFilter {
	private static Pattern pattern = null;

    // 从words.properties初始化正则表达式字符串
    @SuppressWarnings("rawtypes")
	private static void initPattern() {
        StringBuffer patternBuffer = new StringBuffer();
        try {
            InputStream in = KeyWordFilter.class.getClassLoader().getResourceAsStream("keywords.properties");
            Properties property = new Properties();
            property.load(in);
            Enumeration enu = property.propertyNames();
            patternBuffer.append("(");
            while (enu.hasMoreElements()) {
                String scontent = (String) enu.nextElement();
                patternBuffer.append(scontent + "|");
//                System.out.println(scontent);
            }
            patternBuffer.deleteCharAt(patternBuffer.length() - 1);
            patternBuffer.append(")");

            // 装换编码
            pattern = Pattern.compile(patternBuffer.toString());
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public static String doFilter(String str) {
        initPattern();
        Matcher m = pattern.matcher(str);
        // 选择替换方式，这里以* 号代替
        str = m.replaceAll("*");
        return str;
    }
    
}
