/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net._3haku.qqmsger.client;

/**用户在线状态
 *
 * @author princehaku
 */
public enum OnlineStatu {
    /**在线
     *
     */
    ONLINE{
        @Override
    public int toInt(){
            return 1;
        };
    },
    /**离线
     *
     */
    OFFLINE{
        @Override
    public int toInt(){
            return 4;
        };
    },
    /**离开
     *
     */
    AWAY{
        @Override
    public int toInt(){
            return 3;
        };
    },
    /**隐身
     *
     */
    HIDE{
        @Override
    public int toInt(){
            return 2;
        };
    };
    public int toInt(){
        return -1;
    }
}
