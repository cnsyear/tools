package cn.com.highset.weixin.util;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;  
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.highset.common.constant.Const;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.alibaba.fastjson.JSONObject;

import org.jdom.Element;


public class JS_Sign {
	
    public static Map<String, String> getJSSignMapResult(String appid,String appSecret,String apiKey,String access_token,String url,HttpServletRequest request){
   	 	Map<String, String> ret = new HashMap<String, String>();
   	 	String jsapi_ticket=(String)request.getSession().getAttribute(appid+"jsapi_ticket_session");
   	 	if(jsapi_ticket==null || "".equals(jsapi_ticket)){
   	 		JSONObject json=MyWeixinUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi", "GET", "");
   	 		jsapi_ticket=(String)json.get("ticket");
   	 		request.getSession().setAttribute(appid+"jsapi_ticket_session", jsapi_ticket);
   	 		request.getSession().setMaxInactiveInterval(7200);
   	 	}
   	 	ret = sign(jsapi_ticket, url);
   	 	return ret;
    }
    
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        //  System.out.println(string1);
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("noncestr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", Const.APPID);
        return ret;
    }

    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    @SuppressWarnings("rawtypes")
	public static String getRequestXml(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }else {
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
    
    /**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			ce.printStackTrace();
			//System.out.println("连接超时");
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("请求异常");
		}
		return null;
	}
	
	public static String getIp2(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (!(ip==null||ip.trim().length()==0) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (!(ip==null||ip.trim().length()==0) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	 @SuppressWarnings("rawtypes")
	public static String createSign(String apiKey,String characterEncoding,SortedMap<Object,Object> parameters){
         StringBuffer sb = new StringBuffer();
         Set es = parameters.entrySet();
         Iterator it = es.iterator();
         while(it.hasNext()) {
             Map.Entry entry = (Map.Entry)it.next();
             String k = (String)entry.getKey();
             Object v = entry.getValue();
             if(null != v && !"".equals(v) 
                     && !"sign".equals(k) && !"key".equals(k)) {
                 sb.append(k + "=" + v + "&");
             }
         }
         sb.append("key=" + apiKey);
         String sign = MD5Encode(sb.toString(), characterEncoding).toUpperCase();
         return sign;
     }
	 
	 public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	/**
	 * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	 * @param code
	 * 正确时返回的JSON数据包如下：
		{
		   "access_token":"ACCESS_TOKEN",
		   "expires_in":7200,
		   "refresh_token":"REFRESH_TOKEN",
		   "openid":"OPENID",
		   "scope":"SCOPE"
		}
	 */
	@SuppressWarnings("unused")
	private Map<String, String> getAccess_tokenByCode(String appid,String appSecret,String code, HttpServletResponse response) {
		//System.out.println("method start getAccess_tokenByCode(String code, HttpServletResponse response)");
		Map<String, String> data = new HashMap<String, String>();
		String requestUrlMessageFormat = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
		String requestUrl = MessageFormat.format(requestUrlMessageFormat, appid, appSecret, code);    
		//String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxc857987be4f6d9b2&secret=6b1960835b43e0dd24d35ad0a49a25fb&code="+code+"&grant_type=authorization_code";
		//System.out.println("requestUrl----------"+requestUrl);
		String requestMethod = "GET";
		String outputStr = "";
		JSONObject json = MyWeixinUtil.httpRequest(requestUrl, requestMethod, outputStr);
		String access_token = (String) json.get("access_token");
		String openid = (String) json.get("openid");
		data.put("access_token", access_token);
		data.put("openid", openid);
		
		//System.out.println("access_token------------"+access_token);
		//System.out.println("openid------------"+openid);
		
		//System.out.println("method end getAccess_tokenByCode(String code, HttpServletResponse response)");
		return data;
	}
	
	 /**
     * 解析xml
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map doXMLParse(String strxml) throws Exception {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if(null == strxml || "".equals(strxml)) {
            return null;
        }
        
        Map m = new HashMap();
        
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            
            m.put(k, v);
        } 
        //关闭流
        in.close(); 
        return m;
    }
    
    /**
	 * 获取子结点的xml
	 */
	@SuppressWarnings("rawtypes")
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}


}
