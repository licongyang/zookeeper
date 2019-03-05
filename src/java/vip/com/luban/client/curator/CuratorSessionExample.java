package com.luban.client.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
/*
 *   curator 框架可以帮助客户端跟服务端断开后，服务端session超时，会注销临时节点和监听器的问题
 *
 * */
public class CuratorSessionExample {
    public static void main(String[] args) {
        final CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",1000,1000,new RetryNTimes(3,1000));
        client.start();

        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                    //如果发现最新的连接是lost状态
                if(connectionState == ConnectionState.LOST){
                    //重新获取连接，通过阻塞方式建立连接
                    try {
                        if (client.getZookeeperClient().blockUntilConnectedOrTimedOut()){
                            //建立连接后做同样的事情，如创建节点，绑定监听器
                            doTask();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private static void doTask() {
    }
}
