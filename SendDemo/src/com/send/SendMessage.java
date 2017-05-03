package com.send;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @Description:���Ź���
 * @author hansq
 * @create 2017-5-3 ����1:42:32
 */
public class SendMessage {
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn"); 
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//��ͷ�ļ�������ת��
		NameValuePair[] data ={ 
				new NameValuePair("Uid", "hansq"),
				new NameValuePair("Key", "9a23953bd9431b508fac"),
				new NameValuePair("smsMob","18603884805"),
				new NameValuePair("smsText","������֤�룺123456789")};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers)
		{
		System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
		System.out.println(result); //��ӡ������Ϣ״̬


		post.releaseConnection();

	}
}
