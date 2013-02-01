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
 *  Created on : 2010-11-17, 23:20:26
 *  Author     : princehaku
 */
package net._3haku.qqmsger.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import net._3haku.qqmsger.client.ClientConfig;
import net._3haku.qqmsger.util.Log4j;
import net._3haku.qqmsger.util.StringTools;

/**认证令牌
 *
 * @author princehaku
 */
public class Ssid {

    private String ssid = "";
    
    private long qqNumber;

    public Ssid(long qqNumber) {
        this.qqNumber = qqNumber;
    }

    public long getQQNumber() {
        return qqNumber;
    }

    /**从服务器获取ssid
     *
     * @throws HttpConnectException,SsidNotFoundException
     */
    private void fetchSsid() throws HttpConnectException, SsidNotFoundException {
        HTTPconnecter hc = new HTTPconnecter();
        String context = "";
        byte[] h = null;
        //登录主页面寻找令牌
        try {
            h = hc.get(ClientConfig.loginUrl);
        } catch (Exception ex) {
            //Log4j.getLogger(this.getClass()).error("CONNECT SERVER FAILED :"+ex.getMessage());
            throw new HttpConnectException(ex);
        }
        context = StringTools.byteToString(h, "utf8");
        //提取ssid
        if (StringTools.cutString(context, "sid=", "&amp;").equals("")) {
            //Log4j.info(this.getClass().getName(), "SSID " + context);
            throw new SsidNotFoundException();
        }
        setSsid(StringTools.cutString(context, "sid=", "&amp;") + "");
        Log4j.info(this.getClass().getName(), "SSID set :" + ssid);
        //写入ssidpool文件尾
    }

    /**得到一个唯一令牌
     * 从服务器获取
     * @return
     * @throws HttpConnectException, SsidNotFoundException
     */
    public String getSsid() throws HttpConnectException, SsidNotFoundException {

        if (this.ssid.equals("")) {
            //如果没有找到 从服务器上重新获取
            try {
                fetchSsid();
            } catch (HttpConnectException ex) {
                Log4j.error("GET SSID ERROR:服务器连接失败" + ex.getMessage());
                throw ex;
            } catch (SsidNotFoundException ex) {
                Log4j.error("GET SSID ERROR:服务器没有返回ssid");
                throw ex;
            }
        }
        return ssid;
    }

    @Override
    public String toString() {
        return ssid;
    }

    public void clear() {
        ssid = "";
    }
    /**改变ssid值
     *
     * @param ssid
     */
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    /**将当前的ssid存入文件
     * 如果没有则新增记录
     * 
     */
    public void store() {
        try {
            FileReader fr = new FileReader("./cache/ssidpool");
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String content = "";
            boolean hasOld=false;
            while (br.ready()) {
                line = br.readLine();
                StringTokenizer st = new StringTokenizer(line, "[]");
                if (st.countTokens() != 2) {
                    Log4j.info("ERROR READ SSID FILE AT " + br);
                    continue;
                }
                String number=st.nextToken();
                if ((this.getQQNumber() + "").equals(number)) {
                    String ss = st.nextToken();
                    Log4j.info("SSID SET To FILE :" + this.ssid);
                    //如果ssid没有变化 不写入文件
                    if(ss.equals(this.ssid)){
                        return;
                    }else{
                        line=number+"[]"+this.ssid;
                        hasOld=true;
                    }
                }
                content = content + line + "\r\n";
            }
            fr.close();
            if(!hasOld){
                content = content +this.getQQNumber()+ "[]" +this.ssid+ "\r\n";
            }
            //重新将缓存写入文件
            FileWriter fw=new FileWriter("./cache/ssidpool");
            fw.write(content);
            fw.close();
        } catch (IOException ex) {
            Log4j.error("PUT SSID ERROR:本地文件写入错误");
        }
    }

    /**得到一个唯一令牌
     * 从缓存文件读取
     * @return
     * @throws SsidNotFoundException, IOException
     */
    public String getSsidFromFile() throws  SsidNotFoundException, IOException{

            //先从缓冲区找ssid
            try {
                FileReader fr = new FileReader("./cache/ssidpool");
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                while (br.ready()) {
                    line = br.readLine();
                    StringTokenizer st = new StringTokenizer(line, "[]");
                    if (st.countTokens() != 2) {
                        Log4j.info("ERROR READ SSID FILE AT " + br);
                        continue;
                    }
                    if ((this.getQQNumber() + "").equals(st.nextToken())) {
                        String ss = st.nextToken();
                        Log4j.info("SSID GET FROM FILE :" + ss);
                        this.setSsid(ss);
                        return ss;
                    }
                }
            } catch (IOException ex) {
                Log4j.error("GET SSID ERROR:本地文件读取错误");
                throw ex;
            }
            return "";
    }
}
