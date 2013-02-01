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
 *  Created on : 2010-11-17, 14:19:30
 *  Author     : princehaku
 */
package net._3haku.qqmsger.client;

import java.net.URLEncoder;
import net._3haku.qqmsger.message.ChatMessage;
import net._3haku.qqmsger.net.Ssid;

import net._3haku.qqmsger.message.Message;
import net._3haku.qqmsger.message.MessageQueue;
import net._3haku.qqmsger.net.HTTPconnecter;
import net._3haku.qqmsger.userbean.QQUser;
import net._3haku.qqmsger.util.Log4j;
import net._3haku.qqmsger.util.StringTools;

/**客户的实例 必须实现客户连接和客户沟通接口
 *
 * @author princehaku
 */
public class QQClient extends QQUser implements ClientConnectInterface, ClientCommunicateInterface {

    /**客户密码
     *
     */
    private String password;
    /**服务器编号
     * 
     */
    private String serverNo;
    /**消息队列
     *
     */
    protected MessageQueue messageQueue = null;
    /**会话令牌
     * 
     */
    protected Ssid ssid;
    /**连接器
     *
     */
    HTTPconnecter hc;
    /**验证码
     *
     */
    protected VerifyCoder verifycoder;

    /**默认的构造函数
     *
     * @param qqNumber
     * @param qqPassword
     */
    public QQClient(long qqNumber, String qqPassword) {
        ssid = new Ssid(qqNumber);
        hc = new HTTPconnecter();
        this.setQQnumer(qqNumber);
        this.setPassword(qqPassword);
        this.setStatu(OnlineStatu.OFFLINE);
    }

    public void changeStatu(OnlineStatu os) throws ClientLoginException {
        //如果旧状态是离线 需要非离线状态 则登陆
        if (this.getStatu().toInt() == 4 && os.toInt() != 4) {
            System.out.println(this.getQQnumer() + "正在登陆...");
            try {
                //this.mainTain();
                this.login();
                System.out.println(this.getQQnumer() + "登陆成功...");
                this.setStatu(os);
            } catch (ClientLoginException ex) {
                System.out.println(this.getQQnumer() + "登陆失败...原因: " + ex.getMessage());
                throw ex;
            }
        }
        switch (os) {
            case AWAY:
                break;
            case HIDE:
                break;

        }
    }

    /**用户登录
     * @see changeStatu
     * @return
     */
    public void login() throws ClientLoginException {

        Log4j.info(this.getClass().getName(), "qq号码 :" + this.getQQnumer() + "尝试登陆");

        ClientLogin cl = null;
        
        try {
            ssid.getSsidFromFile();
            cl = new ClientSsidLogin(this);
            cl.login();
        } catch (Exception ex) {
            Log4j.info(ex.getMessage());
            cl = new ClientFormLogin(this);
            cl.login();
        }

    }

    public String getServerNo() {
        return serverNo;
    }

    public void setServerNo(String serverNo) {
        this.serverNo = serverNo;
    }

    /**验证码 验证图片上的字符
     *
     * @param verifyCode 图片上的验证码
     * @return
     */
    public boolean verifyInput(String verifyCode) {
        if (this.verifycoder == null) {
            Log4j.info(this.getClass().getName(), "VERIFY:NO NEED");
            return false;
        }
        try {
            this.verifycoder.verifyInput(this, verifyCode);
        } catch (Exception ex) {
            System.out.println(this.getQQnumer() + "验证失败,请重新登陆");
            return false;
        }
        System.out.println(this.getQQnumer() + "验证成功,成功登陆");
        
        this.setStatu(OnlineStatu.ONLINE);
        return true;
    }

    public boolean logout() {
        //TODO:logout
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Message reviceMessage() throws ClientReceviceMessageException{

        return null;
    }
    public void sendMessage(int toqqNumber, String message) throws ClientSendMessageException{
        
        if (this.getStatu().equals(OnlineStatu.OFFLINE)) {
            Log4j.info("SendMessage:NEED LOGIN FIRST");
            throw new ClientSendMessageException("SendMessage:NEED LOGIN FIRST");
        }
        if (toqqNumber==this.getQQnumer()) {
            throw new ClientSendMessageException("can't send msg to yourself");
        }
        byte[] h;
        String context="";
        try {
            h = this.getHc().post("http://" + getServerNo() + ".3g.qq.com/g/s?sid=" + ssid.getSsid()+ "&aid=sendmsg&tfor=qq", "msg=" + URLEncoder.encode(message, "UTF8") + "&u=" + toqqNumber + "&saveURL=0&do=send&on=1");
            context = StringTools.byteToString(h, "utf8");
        } catch (Exception ex) {
            Log4j.info("SendMessage:NetWork Error");
            throw new ClientSendMessageException("Message Send Error");
        }
        if (context.indexOf("发送成功") != -1) {
            System.out.println(this.getQQnumer()+"成功发送消息至"+toqqNumber);
        } else {
            System.out.println(this.getQQnumer()+"失败发送消息至"+toqqNumber);
            Log4j.info("SendMessage:Deliver Error");
            throw new ClientSendMessageException("Message Send Error");
        }
    }

    public void receiveMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean mainTain() {

        if (this.getStatu().equals(OnlineStatu.OFFLINE)) {
            Log4j.info("MainTain:NEED LOGIN FIRST");
            return false;
        }

        String context = "";

        byte[] h = null;

        //如果服务器号发现PT...跟随url跳转
        if (this.getServerNo().indexOf("pt") != -1) {
            try {
                h = hc.get("http://pt.3g.qq.com/s?aid=nLogin3gqqbysid&3gqqsid=" + ssid);
                context = StringTools.byteToString(h, "utf8");
            } catch (Exception ex) {
                Log4j.error(this.getClass().getName(), "CONNECT SERVER FAILED :" + ex.getMessage());
                this.setStatu(OnlineStatu.OFFLINE);
                return false;
            }
            context = StringTools.byteToString(h, "utf8");
            if (context.indexOf("成功") == -1) {
                this.setStatu(OnlineStatu.OFFLINE);
            }
            if (this.getStatu().equals(OnlineStatu.OFFLINE)) {
                System.out.println(this.getQQnumer() + "连接断开");
                return false;
            }
            String serverno = StringTools.cutString(context, "ontimer=\"http://", ".3g.qq.com");
            if (serverno.equals("")) {
                this.setStatu(OnlineStatu.OFFLINE);
            }
            this.setServerNo(serverno);

            Log4j.info("establishing :" + serverno);

            return true;
        } else {
            try {
                h = hc.get("http://" + this.getServerNo() + ".3g.qq.com/g/s?aid=nqqchatMain&sid=" + ssid.toString() + "&myqq=" + this.getQQnumer());
                context = StringTools.byteToString(h, "utf8");
            } catch (Exception ex) {
                Log4j.error(this.getClass().getName(), "CONNECT SERVER FAILED :" + ex.getMessage());
                return false;
            }

            String SerNo = StringTools.cutString(context, "=\"http://", ".3g");

            Log4j.info("establishing :" + SerNo);

            if (!(SerNo.equals(""))) {
                setServerNo(SerNo);
            }

            return true;
        }
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    protected HTTPconnecter getHc() {
        return hc;
    }

    public String getVerifyImageFileName() {
        if (this.verifycoder == null) {
            Log4j.info(this.getClass().getName(), "VERIFY:NO NEED");
            return "";
        }
        return this.verifycoder.getFileName();
    }


}
