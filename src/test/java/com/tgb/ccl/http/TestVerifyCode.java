package com.tgb.ccl.http;

import org.apache.http.Header;

import com.tgb.ccl.http.common.HttpConfig;
import com.tgb.ccl.http.common.HttpCookies;
import com.tgb.ccl.http.common.HttpHeader;
import com.tgb.ccl.http.common.util.OCR;
import com.tgb.ccl.http.exception.HttpProcessException;
import com.tgb.ccl.http.httpclient.HttpClientUtil;

/** 
 * 识别验证码demo
 * 
 * @author arron
 * @date 2016年6月7日 上午10:51:51 
 * @version 1.0 
 */
public class TestVerifyCode {
	
	public static void main(String[] args) throws InterruptedException, HttpProcessException {
		String qq = "123456789";//qq号
		String imgUrl = "http://qqxoo.com/include/vdimgvt.php?t="+Math.random(); //获取验证码图片地址
		String verifyUrl = "http://qqxoo.com/include/vdcheck.php";
		String saveCodePath = "C:/1.png";//保存验证码图片路径
		
		Header[] headers = HttpHeader.custom().referer("http://qqxoo.com/main.html?qqid="+qq).build();//设置referer，是为了获取对应qq号的验证码，否则报错
		HttpConfig config = HttpConfig.custom().headers(headers).context(HttpCookies.custom().getContext());//必须设置context，是为了携带cookie进行操作
		
		String result =null;//识别结果
		
		do {
			if(result!=null){
				System.err.println("校验失败！稍等片刻后继续识别");
				Thread.sleep((int)(Math.random()*10)*100);
			}
			
			//获取验证码
			//OCR.debug(); //开始Fiddler4抓包（127.0.0.1:8888）
			String code = OCR.ocrCode4Net(config.url(imgUrl), saveCodePath);
			
			while(code.length()!=5){//如果识别的验证码位数不等于5，则重新识别
				if(code.contains("亲,apiKey已经过期或错误,请重新获取")){
					System.err.println(code);
					return;
				}
				code = OCR.ocrCode4Net(config.url(imgUrl), saveCodePath);
			}
			
			System.out.println("本地识别的验证码为："+code);
			System.out.println("验证码已保存到："+saveCodePath);
			
			System.out.println("开始校验验证码是否正确");
			//开始验证识别的验证码是否正确
			result = HttpClientUtil.get(config.url(verifyUrl+"?vc="+code+"&qqid="+qq));
			
		} while (!result.contains("succeed"));
		
		System.out.println("识别验证码成功！反馈信息如下：\n" + result);
	}
}
