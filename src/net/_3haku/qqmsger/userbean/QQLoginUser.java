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
 *  Created on : 2010-11-17, 13:51:46
 *  Author     : princehaku
 */

package net._3haku.qqmsger.userbean;

import net._3haku.qqmsger.util.Log4j;

/**qq登录用户
 * @author princehaku
 * @deprecated
 */
public class QQLoginUser{
    /**qq号码
     *
     */
    protected long qqnumer;
    /**客户密码
     *
     */
    private String password;
    /**必须提供qq号名和密码
     *
     */
    public QQLoginUser(long qqNumber,String qqPassword) {
        Log4j.info(this.getClass().getName(),"您的qq号码 :"+qqNumber);
        this.setQQnumer(qqNumber);
        Log4j.info(this.getClass().getName(),"您的密码 :"+qqPassword);
        this.setPassword(qqPassword);
    }

    public long getQQnumer() {
        return qqnumer;
    }

    protected void setQQnumer(long qqnumer) {
        this.qqnumer = qqnumer;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
}
