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
 *  Created on : 2010-11-17, 16:52:55
 *  Author     : princehaku
 */

package net._3haku.qqmsger.client;

import net._3haku.qqmsger.message.Message;

/**用户之间的交流
 *
 * @author princehaku
 */
interface ClientCommunicateInterface {
    
    void sendMessage(int toqqNumber, String message) throws ClientSendMessageException;
    
    void receiveMessage(Message msg);
}
