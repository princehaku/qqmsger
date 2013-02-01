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
 *  Created on : 2010-11-17, 14:22:57
 *  Author     : princehaku
 */

package net._3haku.qqmsger.util;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author princehaku
 */
public class StringTools {
    /**将字节转换成字符串
     *
     * @param source
     * @param encode 编码
     * @return
     */
    public static String byteToString(byte[] source ,String encode){
        String returnString="";
        try {
            returnString=new String(source, encode);
        } catch (UnsupportedEncodingException ex) {
            Log4j.error(ex.getMessage());
        }
        return returnString;
    }

    /**
     * 用来截取两个字符串包含中间的字符串
     * @param source
     * @param st
     * @param ed
     * @return
     */
    public static String cutString(String source, String st, String ed) {

        int apos = source.indexOf(st);
        // 从第一个位置开始找下一个/>
        int bpos = source.indexOf(ed, apos + st.length());

        if (apos == -1 || bpos == -1) {
            return "";
        } else {
            return source.substring(apos + st.length(), bpos);
        }


    }

}
