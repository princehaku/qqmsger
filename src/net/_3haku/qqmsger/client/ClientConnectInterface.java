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
 *  Created on : 2010-11-17, 13:27:40
 *  Author     : princehaku
 */

package net._3haku.qqmsger.client;

/**用户与服务器连接接口
 *
 * @author princehaku
 */
interface ClientConnectInterface {
    /**登录
     *
     */
    public void login() throws ClientLoginException;
    /**填写验证码
     *
     */
    boolean verifyInput(String verifyCode);
    /**退出
     *
     */
    boolean logout();
    /**维持在线状态
     *
     */
    boolean mainTain();
}
