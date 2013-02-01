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
 *  Created on : 2011-1-9, 23:53:36
 *  Author     : princehaku
 */

package net._3haku.qqmsger.client;

import net._3haku.qqmsger.net.SsidNotFoundException;
import net._3haku.qqmsger.util.Log4j;
import net._3haku.qqmsger.util.StringTools;

/**通过ssid进行登录
 *
 * @author princehaku
 */
public class ClientSsidLogin extends ClientLogin{
    String ssid;

    ClientSsidLogin(QQClient ql){
        super(ql);
    }

    @Override
    public void getSSid() throws SsidNotFoundException {
        ssid=qqclient.ssid.toString();
        if(ssid.equals("")){
            throw new SsidNotFoundException();
        }
    }

    @Override
    public void connect() throws ClientLoginException {

        byte[] h = null;

        //尝试连接服务器

        try {
            h = this.qqclient.hc.get("http://pt.3g.qq.com/s?aid=nLogin3gqqbysid&3gqqsid=" + ssid);
            context = StringTools.byteToString(h, "utf8");
        } catch (Exception ex) {
            Log4j.error(this.getClass().getName(), "CONNECT SERVER FAILED :" + ex.getMessage());
            this.qqclient.setStatu(OnlineStatu.OFFLINE);
            throw new ClientLoginException("连接服务器失败");
        }
        context = StringTools.byteToString(h, "utf8");

    }

    @Override
    public void handleResult() throws ClientLoginException {
        if (context.indexOf("成功") == -1) {
            this.qqclient.setStatu(OnlineStatu.OFFLINE);
            throw new ClientLoginException("SSID过期");
        }
        String serverno = StringTools.cutString(context, "ontimer=\"http://", ".3g.qq.com");

        if (serverno.equals("")) {
            this.qqclient.setStatu(OnlineStatu.OFFLINE);
            throw new ClientLoginException("SSID过期");
        }
        
        this.qqclient.setServerNo(serverno);

        Log4j.info("Login at :" + serverno);

    }
}
