package com.yinghua.translation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.enterprise.context.ApplicationScoped;

import com.alibaba.fastjson.JSONObject;


/**
 * 
 * HTTP请求对象
 * 
 * 
 * 
 * @author
 */
@ApplicationScoped
public class HttpRequester
{

	private String defaultContentEncoding;
	
	public HttpRequester() {

        this.defaultContentEncoding = "UTF-8";

    }


	/**
	 * 
	 * 发送GET请求
	 * 
	 * 
	 * 
	 * @param urlString
	 * 
	 *            URL地址
	 * 
	 * @return 响应对象
	 * 
	 * @throws IOException
	 */

	public HttpRespons sendGet(String urlString) throws IOException
	{

		return this.send(urlString, "GET", null, null);

	}

	/**
	 * 
	 * 发送GET请求
	 * 
	 * 
	 * 
	 * @param urlString
	 * 
	 *            URL地址
	 * 
	 * @param params
	 * 
	 *            参数集合
	 * 
	 * @return 响应对象
	 * 
	 * @throws IOException
	 */

	public HttpRespons sendGet(String urlString, Map<String, String> params)

	throws IOException
	{

		return this.send(urlString, "GET", params, null);

	}

	/**
	 * 
	 * 发送GET请求
	 * 
	 * 
	 * 
	 * @param urlString
	 * 
	 *            URL地址
	 * 
	 * @param params
	 * 
	 *            参数集合
	 * 
	 * @param propertys
	 * 
	 *            请求属性
	 * 
	 * @return 响应对象
	 * 
	 * @throws IOException
	 */

	public HttpRespons sendGet(String urlString, Map<String, String> params,

	Map<String, String> propertys) throws IOException
	{

		return this.send(urlString, "GET", params, propertys);

	}

	/**
	 * 
	 * 发送POST请求
	 * 
	 * 
	 * 
	 * @param urlString
	 * 
	 *            URL地址
	 * 
	 * @return 响应对象
	 * 
	 * @throws IOException
	 */

	public HttpRespons sendPost(String urlString) throws IOException
	{

		return this.send(urlString, "POST", null, null);

	}

	/**
	 * 
	 * 发送POST请求
	 * 
	 * 
	 * 
	 * @param urlString
	 * 
	 *            URL地址
	 * 
	 * @param params
	 * 
	 *            参数集合
	 * 
	 * @return 响应对象
	 * 
	 * @throws IOException
	 */

	public HttpRespons sendPost(String urlString, Map<String, String> params)

	throws IOException
	{

		return this.send(urlString, "POST", params, null);

	}

	/**
	 * 
	 * 发送POST请求
	 * 
	 * 
	 * 
	 * @param urlString
	 * 
	 *            URL地址
	 * 
	 * @param params
	 * 
	 *            参数集合
	 * 
	 * @param propertys
	 * 
	 *            请求属性
	 * 
	 * @return 响应对象
	 * 
	 * @throws IOException
	 */

	public HttpRespons sendPost(String urlString, Map<String, String> params,

	Map<String, String> propertys) throws IOException
	{

		return this.send(urlString, "POST", params, propertys);

	}

	/**
	 * 
	 * 发送HTTP请求
	 * 
	 * 
	 * 
	 * @param urlString
	 * 
	 * @return 响映对象
	 * 
	 * @throws IOException
	 */

	private HttpRespons send(String urlString, String method,

	Map<String, String> parameters, Map<String, String> propertys)

	throws IOException
	{

		HttpURLConnection urlConnection = null;
		if (method.equalsIgnoreCase("GET") && parameters != null)
		{

			StringBuffer param = new StringBuffer();
			int i = 0;

			for (String key : parameters.keySet())
			{
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}

			urlString += param;
			System.out.println(urlString);
		}

		URL url = new URL(urlString);
		
		urlConnection = (HttpURLConnection) url.openConnection();
		
		urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		
		urlConnection.setRequestMethod(method);

		urlConnection.setDoOutput(true);

		urlConnection.setDoInput(true);

		urlConnection.setUseCaches(true);

		if (propertys != null)

			for (String key : propertys.keySet())
			{

				urlConnection.addRequestProperty(key, propertys.get(key));

			}

		if (method.equalsIgnoreCase("POST") && parameters != null)
		{

			StringBuffer param = new StringBuffer();

			for (String key : parameters.keySet())
			{
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			urlConnection.getOutputStream().write(
					param.toString().getBytes("UTF-8"));
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}

		return this.makeContent(urlString, urlConnection);

	}

	/**
	 * 
	 * 得到响应对象
	 * 
	 * 
	 * 
	 * @param urlConnection
	 * 
	 * @return 响应对象
	 * 
	 * @throws IOException
	 */

	private HttpRespons makeContent(String urlString,

	HttpURLConnection urlConnection) throws IOException
	{

		HttpRespons httpResponser = new HttpRespons();

		try
		{
			InputStream in = urlConnection.getInputStream();

			BufferedReader bufferedReader = new BufferedReader(

			new InputStreamReader(in,"UTF-8"));

			httpResponser.contentCollection = new Vector<String>();

			StringBuffer temp = new StringBuffer();

			String line = bufferedReader.readLine();
			while (line != null)
			{
				httpResponser.contentCollection.add(line.trim());
				temp.append(line.trim()).append("\r\n");
				line = bufferedReader.readLine();
			}

			bufferedReader.close();
			
			//String ecod = CodingConstant.CHARSET;
			String ecod = urlConnection.getContentEncoding();

			if (ecod == null)
				ecod = this.defaultContentEncoding;

			httpResponser.urlString = urlString;

			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();

			httpResponser.file = urlConnection.getURL().getFile();

			httpResponser.host = urlConnection.getURL().getHost();

			httpResponser.path = urlConnection.getURL().getPath();

			httpResponser.port = urlConnection.getURL().getPort();

			httpResponser.protocol = urlConnection.getURL().getProtocol();

			httpResponser.query = urlConnection.getURL().getQuery();

			httpResponser.ref = urlConnection.getURL().getRef();

			httpResponser.userInfo = urlConnection.getURL().getUserInfo();

			httpResponser.content = new String(temp.toString().trim().getBytes(), ecod);

			httpResponser.contentEncoding = ecod;

			httpResponser.code = urlConnection.getResponseCode();

			httpResponser.message = urlConnection.getResponseMessage();

			httpResponser.contentType = urlConnection.getContentType();

			httpResponser.method = urlConnection.getRequestMethod();

			httpResponser.connectTimeout = urlConnection.getConnectTimeout();

			httpResponser.readTimeout = urlConnection.getReadTimeout();

			return httpResponser;

		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			if (urlConnection != null)
				urlConnection.disconnect();
		}

	}
	
	public static void main(String[] args) {
		HttpRequester h = new HttpRequester();
		HttpRespons hr;
		try {
			Map<String, String> param = new HashMap<String, String>();
			Map<String, String> pp = new HashMap<String, String>();
			param.put("account", "novobabel");
			param.put("password", "novobabel1");
			param.put("mobile", "15901321233");
			param.put("content", URLEncoder.encode("您的验证码是：3467。如需帮助请联系客服。","UTF-8"));
			String ss = JSONObject.toJSONString(param);
			pp.put("params", ss);
			
			hr = h.sendPost("http://sms.106jiekou.com/utf8/sms.aspx", param);
			//hr = h.sendGet("http://sms.106jiekou.com/utf8/sms.aspx?account=novobabel&password=novobabel1&mobile=15901321233&"+URLEncoder.encode("content=您的验证码是:6578。如需帮助请联系客。", "UTF-8"));
			System.out.println(hr.getContent());
			System.out.println(hr.getContentType());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
