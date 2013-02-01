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
 *  Created on : 2010-11-16, 23:45:10
 *  Author     : princehaku
 */

package net._3haku.qqmsger.message;

import net._3haku.qqmsger.userbean.QQUser;

/**对话消息
 *
 * @author princehaku
 */
public class ChatMessage extends Message{

    public ChatMessage()
    {

    }
    /**对话消息的构造函数
     *
     * @param fromUser
     * @param toUser
     * @param msg
     */
    public ChatMessage(QQUser fromUser, QQUser toUser,String msg) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.setContext(msg);
        this.setMessageStatu(MessageStatu.PENDDING);
    }
    /**来源用户
     *
     */
    private QQUser fromUser;
    /**目标
     *
     */
    private QQUser toUser;


    
}
