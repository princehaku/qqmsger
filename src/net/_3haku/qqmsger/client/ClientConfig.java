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
 *  Created on : 2010-11-17, 14:34:32
 *  Author     : princehaku
 */

package net._3haku.qqmsger.client;

/**
 *
 * @author princehaku
 */
public class ClientConfig {
    /**登录的url
     *
     */
    public final static String loginUrl="http://pt.3g.qq.com/s?aid=nLogin3gqq";
    /**验证码存放的路径
     *
     */
    public final static String imageCachePath="./cache/";
    /**验证码过期时间间隔 (ms)
     *
     */
    public final static int expiraTime=60000;
    /**HTTP连接超时时间 (ms)
     *
     */
    public final static int HttpConnectTimeout=30000;
}
