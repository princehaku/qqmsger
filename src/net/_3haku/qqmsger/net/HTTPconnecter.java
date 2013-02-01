/*  Copyright 2010 princehaku
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Created on : 2010-11-16, 23:19:19
 *  Author     : princehaku
 */
package net._3haku.qqmsger.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import net._3haku.qqmsger.client.ClientConfig;
import net._3haku.qqmsger.util.Log4j;

/**HTTP连接类
 * 带cookie 可以使用GET和POST
 * @author princehaku
 */
public class HTTPconnecter {

    /** 存放cookie
     */
    private String cookieString = "";
    /**清空cookie
     *
     */
    public void clearCookie(){
        this.cookieString="";
    }
    /**
     * 得到get传递的文本
     *
     * @param url
     *            提交地址
     */
    @SuppressWarnings("finally")
    public byte[] get(String url) throws Exception {

        String line = "";

        String content = "";

        HttpURLConnection httpConn = null;

        try {
            URL turl = new URL(url);
            Log4j.info(this.getClass().getName(),"URL GET :"+url);
            httpConn = (HttpURLConnection) turl.openConnection();
            httpConn.setConnectTimeout(ClientConfig.HttpConnectTimeout);
            httpConn.setReadTimeout(ClientConfig.HttpConnectTimeout);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Host", turl.getHost());
            httpConn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
            httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpConn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
            httpConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
            httpConn.setRequestProperty("Accept-Charset", "utf-8,GB2312;q=0.7,*;q=0.7");
            if (!(getCookieString().equals(""))) {
                // 晕死..
                httpConn.setRequestProperty("Cookie", "" + getCookieString()+ ";");
                // System.out.print("发送cookie======="+Inc.cookieString);
            }
            httpConn.setRequestProperty("Keep-Alive", "300");
            httpConn.setRequestProperty("Connection", "keep-alive");
            httpConn.setRequestProperty("Cache-Control", "no-cache");
            Log4j.info(this.getClass().getName(),"Content Length:"+httpConn.getContentLength());
            InputStream uurl;
            uurl = httpConn.getInputStream();
            if (httpConn.getHeaderField("Set-Cookie") != null) {
                String set_Cookie = httpConn.getHeaderField("Set-Cookie");
                // System.out.println("得到cookie"+set_Cookie);
                setCookieString(set_Cookie.substring(0, set_Cookie.indexOf(";")));
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(uurl));

            while (line != null) {
                line = br.readLine();
                if (line != null) {
                    content = content.toString() + line.toString();
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            // 关闭连接
            httpConn.disconnect();
            return content.getBytes();
        }
    }

    /**
     * 得到二进制数据流
     *
     * @param url
     *
     */
    @SuppressWarnings("finally")
    public byte[] getBin(String url) throws Exception {

        byte[] bufferCache = null ;

        HttpURLConnection httpConn = null;

        try {
            URL turl = new URL(url);
            Log4j.info(this.getClass().getName(),"URL GET :"+url);
            httpConn = (HttpURLConnection) turl.openConnection();
            httpConn.setConnectTimeout(ClientConfig.HttpConnectTimeout);
            httpConn.setReadTimeout(ClientConfig.HttpConnectTimeout);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Host", turl.getHost());
            httpConn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
            httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpConn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
            httpConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
            httpConn.setRequestProperty("Accept-Charset", "utf-8,GB2312;q=0.7,*;q=0.7");
            if (!(getCookieString().equals(""))) {
                // 晕死..
                httpConn.setRequestProperty("Cookie", "" + getCookieString()+ ";");
                // System.out.print("发送cookie======="+Inc.cookieString);
            }
            httpConn.setRequestProperty("Keep-Alive", "300");
            httpConn.setRequestProperty("Connection", "keep-alive");
            httpConn.setRequestProperty("Cache-Control", "no-cache");
            Log4j.info(this.getClass().getName(),"Content Length:"+httpConn.getContentLength());
            bufferCache = new byte[httpConn.getContentLength()];
            InputStream uurl;
            uurl = httpConn.getInputStream();
            if (httpConn.getHeaderField("Set-Cookie") != null) {
                String set_Cookie = httpConn.getHeaderField("Set-Cookie");
                //System.out.println("得到cookie"+set_Cookie);
                setCookieString(set_Cookie.substring(0, set_Cookie.indexOf(";")));
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(uurl));
            uurl.read(bufferCache);

        } catch (Exception e) {
            throw e;
        } finally {
            // 关闭连接
            httpConn.disconnect();
            return bufferCache;
        }
    }
    /**
     * 传递POST表单
     *
     * @param url
     *            提交的url地址
     * @param parmString
     *            表单参数串
     */
    @SuppressWarnings("finally")
    public byte[] post(String url, String parmString) throws Exception {

        String line = "";

        String content = "";

        HttpURLConnection httpConn = null;

        try {
            URL turl = new URL(url);
            Log4j.info(this.getClass().getName(),"URL POST :"+url+"  Params :"+parmString);
            httpConn = (HttpURLConnection) turl.openConnection();
            httpConn.setConnectTimeout(ClientConfig.HttpConnectTimeout);
            httpConn.setReadTimeout(ClientConfig.HttpConnectTimeout);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Host", turl.getHost());
            httpConn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");httpConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpConn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
            httpConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
            httpConn.setRequestProperty("Accept-Charset",
                    "utf-8,GB2312;q=0.7,*;q=0.7");
            if (!(getCookieString().equals(""))) {
                // 晕死..
                httpConn.setRequestProperty("Cookie", "" + getCookieString()+ ";");
            }
            httpConn.setRequestProperty("Keep-Alive", "300");
            httpConn.setRequestProperty("Connection", "keep-alive");
            httpConn.setRequestProperty("Cache-Control", "no-cache");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(parmString.length()));
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStreamWriter out = new OutputStreamWriter(httpConn.getOutputStream());
            out.write(parmString);
            out.close();
            InputStream uurl;

            uurl = httpConn.getInputStream();

            if (httpConn.getHeaderField("Set-Cookie") != null) {
                String set_Cookie = httpConn.getHeaderField("Set-Cookie");
                // System.out.println("得到cookie"+set_Cookie);
                setCookieString(set_Cookie.substring(0, set_Cookie.indexOf(";")));
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(uurl));
            while (line != null) {
                line = br.readLine();
                if (line != null) {
                    content = content.toString() + line.toString();
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            // 关闭连接
            httpConn.disconnect();
            return content.getBytes();
        }
    }

    public void setCookieString(String cookieString) {
        this.cookieString = cookieString;
    }

    public String getCookieString() {
        return cookieString;
    }
}
