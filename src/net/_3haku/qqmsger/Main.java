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
 *  Created on : 2010-11-16, 23:58:34
 *  Author     : princehaku
 */
package net._3haku.qqmsger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import net._3haku.qqmsger.client.ClientLoginException;

import net._3haku.qqmsger.client.QQClient;
import net._3haku.qqmsger.client.OnlineStatu;


/**
 *
 * @author princehaku
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        final QQClient c1=new QQClient(389663316,"xxxxxxxxx");

        try {
            c1.changeStatu(OnlineStatu.ONLINE);
        } catch (ClientLoginException ex) {
            //需要输入验证码
            System.out.println("请输入验证码 位置:cache/"+c1.getVerifyImageFileName());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();
            c1.verifyInput(input);
            //c1.sendMessage(qa,"asdasd");
        }
        
        c1.sendMessage(349674806, "啊啊啊");
        
        Timer t=new Timer();
        
        t.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
                if(!c1.mainTain()){
                     this.cancel();
                }else{
                    
                }
            }
        },0, 1000);

    }

}
