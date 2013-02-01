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
 *  Created on : 2011-1-9, 12:33:25
 *  Author     : princehaku
 */

package net._3haku.qqmsger.client;

import net._3haku.qqmsger.net.SsidNotFoundException;
import net._3haku.qqmsger.util.Log4j;
import net._3haku.qqmsger.util.StringTools;

/**正常方式通过form登录
 *
 * @author princehaku
 */
public class ClientFormLogin extends ClientLogin {
    
    ClientFormLogin(QQClient ql){
        super(ql);
    }
    /**如果登录成功
     * @exception SsidNotFoundException
     */
    @Override
    public void getSSid() throws SsidNotFoundException{
       
        //初始化令牌
        try {
            qqclient.ssid.getSsid();
        } catch (Exception ex) {
            Log4j.error(ex.getMessage());
            throw new SsidNotFoundException();
        }
    }

    @Override
    public void connect() throws ClientLoginException{

        byte[] h = null;
        
        //尝试连接服务器
        try {
            h = qqclient.hc.post("http://pt.3g.qq.com/handleLogin", "sid=" + qqclient.ssid.toString()
                    + "&qq=" + qqclient.getQQnumer() + "&pwd="
                    + qqclient.getPassword() + "&modifySKey=0&toQQchat=true&loginType=1&aid=nLoginHandle");
            this.context = StringTools.byteToString(h, "utf8");
        } catch (Exception ex) {
            Log4j.error(this.getClass().getName(), "LOGIN:CONNECT SERVER FAILED :" + ex.getMessage());
            throw new ClientLoginException("连接服务器失败");
        }
    }

    @Override
    public void handleResult() throws ClientLoginException{

        if (this.context.indexOf("错误，请输入正确的QQ号码") != -1) {
            Log4j.error(this.getClass().getName(), "YOUR QQNUM ERROR :" + qqclient.getQQnumer());
            throw new ClientLoginException("QQ号码错误");
        }
        //检测是否需要填写验证码
        if (context.indexOf("输入验证码") != -1 && context.indexOf("请输入上图字") != -1) {
            //如果需要就立即下载然后生成一个verifycoder类
            qqclient.verifycoder = new VerifyCoder(context, qqclient.ssid, qqclient.hc);
            Log4j.info(this.getClass().getName(), "LOGIN:VERFY CODE NEEDED");
            throw new ClientLoginException("需要验证码");
        }
        //检测是否是密码错误
        if (context.indexOf("登录密码错误") == -1 && context.indexOf("字母大小写") != -1) {
            Log4j.error(this.getClass().getName(), "LOGIN:PASSWORD ERROR");
            throw new ClientLoginException("密码错误");
        }
        String serverno = StringTools.cutString(context, "ontimer=\"http://", ".3g.qq.com");
        if (serverno.equals("")) {
            throw new ClientLoginException("没有找到服务器");
        }
        qqclient.setServerNo(serverno);
        qqclient.ssid.setSsid(StringTools.cutString(context, "?aid=nqqchatMain&amp;sid=", "&amp;"));
        Log4j.info(this.getClass().getName(), "LOGIN:SUCCESS");
        //将登陆成功的SSID写入文件
        qqclient.ssid.store();
    }
}
