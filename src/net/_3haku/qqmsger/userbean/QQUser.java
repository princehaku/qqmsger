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
 *  Created on : 2010-11-16, 23:13:01
 *  Author     : princehaku
 */

package net._3haku.qqmsger.userbean;

import net._3haku.qqmsger.client.OnlineStatu;

/**QQ用户
 *
 * @author princehaku
 */
public class QQUser extends User {
    /**qq号码
     *
     */
    protected long qqnumer;
    /**备注名称
     *
     */
    protected String memoName;
    /**qq等级
     * 
     */
    protected int level;
    /**状态
     *
     */
    protected OnlineStatu statu;

    public int getLevel() {
        return level;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    public String getMemoName() {
        return memoName;
    }

    protected void setMemoName(String memoName) {
        this.memoName = memoName;
    }

    public long getQQnumer() {
        return qqnumer;
    }

    protected void setQQnumer(long qqnumer) {
        this.qqnumer = qqnumer;
    }

    public OnlineStatu getStatu() {
        return statu;
    }

    public void setStatu(OnlineStatu statu) {
        this.statu = statu;
    }
}
