package com.luban.client.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ZookeeperClientTest {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperClientTest.class);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //默认的连接watch,它不是一次性的，只对client的连接状态变化做出反应。
        ZooKeeper client = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("连接的时候" + event);
            }
        });

        /*
            可以注册watcher的方法：getData 、exists、getChildren
            可以触发watcher的方法：create、delete、setData.连接断开的情况下触发的watcher会丢失。
            一个watcher实例是一个回调函数，被回调一次后就被移除了。如果还需要关注数据的变化，需要再次注册watcher

         */
        //创建节点
//        client.create("/data","1".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //改变节点内容

//        client.setData("/data","2".getBytes(),0);
//        Stat stat = new Stat();
//        String s = new String(client.getData("/data", new Watcher() {
//            @Override
//            public void process(WatchedEvent event) {
//                if (Event.EventType.NodeDataChanged.equals(event.getType())) {
//                    System.out.println("数据发生了变化1");
//                }
//            }
//        }, stat));
//        logger.info("getData,new Watcher :{},stat:{}", s,stat);
//        String ss = new String(client.getData("/data", false, stat));
//        logger.info("getData,false :{}", ss);
//        client.getData("/data", false, new AsyncCallback.DataCallback() {
//            @Override
//            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
//                logger.info("getData,DataCallback ,ctx:{},data:{}", ctx, data);
//            }
//        }, null);
        System.in.read();
    }
}
