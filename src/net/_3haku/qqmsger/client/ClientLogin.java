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
 *  Created on : 2011-1-9, 12:48:08
 *  Author     : princehaku
 */

package net._3haku.qqmsger.client;

import net._3haku.qqmsger.net.SsidNotFoundException;

/**客户端登录类
 *
 * @author princehaku
 */
public class ClientLogin implements ClientLoginInterFace{

    static protected QQClient qqclient=null;

    protected String context;

    ClientLogin(QQClient ql){
        qqclient=ql;
    }
    /**登陆
     * getSSid()=>connect()=>handleResult()
     * @throws ClientLoginException
     */
    public void login() throws ClientLoginException{
        try {
            this.getSSid();
            this.connect();
            this.handleResult();
        } catch (Exception ex) {
            throw new ClientLoginException(ex.getMessage());
        }
    }
    /**获取凭据
     *
     * @throws SsidNotFoundException
     */
    public void getSSid() throws SsidNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**连接服务器验证
     *
     * @throws ClientLoginException
     */
    public void connect() throws ClientLoginException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**处理服务器返回的数据
     *
     * @throws ClientLoginException
     */
    public void handleResult() throws ClientLoginException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
