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
 *  Created on : 2010-11-16, 23:16:05
 *  Author     : princehaku
 */
package net._3haku.qqmsger.client;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net._3haku.qqmsger.net.HTTPconnecter;
import net._3haku.qqmsger.net.Ssid;
import net._3haku.qqmsger.util.Log4j;
import net._3haku.qqmsger.util.MD5;
import net._3haku.qqmsger.util.StringTools;

/**验证码
 *
 * @author princehaku
 */
public final class VerifyCoder {

    static Ssid ssid;
    static HTTPconnecter hc;
    private String context;

    public VerifyCoder(String context, Ssid ssid, HTTPconnecter hc) {
        VerifyCoder.ssid = ssid;
        VerifyCoder.hc = hc;
        this.context = context;

        imgurl = StringTools.cutString(context, "<img src=\"", "\"");

        try {
            this.fetchImage(imgurl);
        } catch (Exception ex) {
            Log4j.error(this.getClass().getName(), "VERFY IMAGE DOWNLOAD ERROR :" + ex.getMessage());
        }
    }
    /**文件名
     *
     */
    private String fileName = "";
    /**验证码获取时间
     *
     */
    private Date lastFetchTime = null;
    /**图片的url
     *
     */
    private String imgurl = "";

    /**默认构造函数
     *
     * @param imgurl
     * @throws Exception
     */
    void fetchImage(String imgurl) throws Exception {
        //如果超时则重新获取
        if (lastFetchTime == null || new Date().getTime() - lastFetchTime.getTime() > ClientConfig.expiraTime) {
            try {
                byte[] h = null;
                h = hc.getBin(imgurl);
                fileName = MD5.getMD5(ssid.toString().replaceAll("\\\\", "").getBytes()) + ".gif";
                File storeFile = new File(ClientConfig.imageCachePath + fileName);
                FileOutputStream output = new FileOutputStream(storeFile);
                //得到验证图片并写入文件
                output.write(h);
                output.close();
                //设置上次时间为这次
                lastFetchTime = new Date();
                this.imgurl = imgurl;
                Log4j.info(this.getClass().getName(), "VERFY IMAGE DOWNLOADED :" + ClientConfig.imageCachePath +  fileName + " " + lastFetchTime);
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    /**得到图片的真实url
     *
     * @return
     */
    public String getImgurl() {
        return imgurl;
    }

    /**效验输入的效验码
     * 
     * @param verifyCode
     */
    void verifyInput(QQClient aThis, String verifyCode) throws Exception {
        if (new Date().getTime() - lastFetchTime.getTime() > ClientConfig.expiraTime) {
            Log4j.error(this.getClass().getName(), "VERFY TIMEOUT");
            throw new Exception("TIME OUT");
        }
        //解析源html得到表单里面的值
        Pattern pn = Pattern.compile("<go href=\"(.*?)\"");
        Matcher mc = pn.matcher(this.context);
        String purl = "";
        if (mc.find()) {
            purl = mc.group(1);
            if (purl.indexOf("&amp;") != -1) {
                purl = purl.substring(0, purl.indexOf("&amp;"));
            }
        }
        Log4j.info(this.getClass().getName(), "POST URL GET :" + purl);

        pn = Pattern.compile("<postfield name=\"hexpwd\" value=\"(.*?)\"/>");
        mc = pn.matcher(this.context);
        String hexpwd = "";
        if (mc.find()) {
            hexpwd = mc.group(1);
        }
        Log4j.info(this.getClass().getName(), "Param hexpwd GET :" + hexpwd);

        pn = Pattern.compile("<postfield name=\"extend\" value=\"(.*?)\"/>");
        mc = pn.matcher(this.context);
        String extend = "";
        if (mc.find()) {
            extend = mc.group(1);
        }
        Log4j.info(this.getClass().getName(), "Param extend GET :" + extend);

        pn = Pattern.compile("<postfield name=\"r_sid\" value=\"(.*?)\"/>");
        mc = pn.matcher(this.context);
        String r_sid = "";
        if (mc.find()) {
            r_sid = mc.group(1);
        }
        Log4j.info(this.getClass().getName(), "Param r_sid GET :" + r_sid);

        pn = Pattern.compile("<postfield name=\"rip\" value=\"(.*?)\"/>");
        mc = pn.matcher(this.context);
        String rip = "";
        if (mc.find()) {
            rip = mc.group(1);
        }
        Log4j.info(this.getClass().getName(), "Param rip GET :" + r_sid);

        pn = Pattern.compile("<postfield name=\"go_url\" value=\"(.*?)\"/>");
        mc = pn.matcher(this.context);
        String go_url = "";
        if (mc.find()) {
            go_url = mc.group(1);
        }
        //传递
        byte[] h = hc.post("http://pt.3g.qq.com/handleLogin", "qq=" + aThis.getQQnumer()
                + "&hexpwd=" + hexpwd + "&go_url=" + URLEncoder.encode(go_url, "utf8")
                + "&loginType=1&hexp=true&imgType=gif&loginTitle=手机腾讯网&loginType=1&modifySKey=0&"
                + "q_status=10&r_sid=" + r_sid + "&rip=" + rip + "&sid=" + ssid.getSsid() + "&"
                + "verify=" + verifyCode + "&toQQchat=true");

        context = StringTools.byteToString(h, "utf8");

        if (context.indexOf("登录成功") == -1 && context.indexOf("登录3GQQ成功") == -1) {
            Log4j.info(this.getClass().getName(), "VERIFY:FAILD");
            throw new Exception("FAILD");
        }

        Log4j.info(this.getClass().getName(), "VERIFY:SUCCESS");
    }

    /**得到下载后的文件名
     *
     * @return
     */
    public String getFileName() {
        return this.fileName;
    }
}
