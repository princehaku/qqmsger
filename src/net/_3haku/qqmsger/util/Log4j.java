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
 *  Created on : 2010-11-17, 8:21:36
 *  Author     : princehaku
 */

package net._3haku.qqmsger.util;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
/**日志类
 *
 * @author princehaku
 */
public class Log4j {

    private static boolean isDebug=true;

    private static Logger logger;

    private static Logger getLogger(String obj){
        if(logger==null){

            BasicConfigurator.configure();
        }

        logger = Logger.getLogger(obj);

        return logger;
    }

    public static void info(String className,String msg){
        if(!isDebug)  {
            return;
        }
         getLogger(className).info(msg);
    }

    public static void info(String msg){
        if(!isDebug)  {
            return;
        }
         getLogger("Sys").info(msg);
    }

    public static void error(String className,String msg){
        if(!isDebug)  {
            return;
        }
         getLogger(className).info(msg);
    }
    public static void error(String msg){
        if(!isDebug)  {
            return;
        }
         getLogger("Sys").info(msg);
    }
}
