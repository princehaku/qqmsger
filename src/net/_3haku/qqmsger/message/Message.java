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
 *  Created on : 2010-11-16, 23:12:28
 *  Author     : princehaku
 */

package net._3haku.qqmsger.message;

import java.util.Date;

/**消息
 *
 * @author princehaku
 */
public class Message {
    /**消息正文
     *
     */
    private String context;
    /**消息时间
     *
     */
    private Date time;
    /**消息的状态
     *
     */
    private MessageStatu messageStatu;

    public MessageStatu getMessageStatu() {
        return messageStatu;
    }

    public void setMessageStatu(MessageStatu messageStatu) {
        this.messageStatu = messageStatu;
    }
    
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
