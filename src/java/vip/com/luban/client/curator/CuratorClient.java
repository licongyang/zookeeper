package com.luban.client.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class CuratorClient {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",10000,10000,new RetryNTimes(3,1000));
        client.start();

//        client.create().withMode(CreateMode.PERSISTENT).forPath("/data","3".getBytes());

        String path = "/data";
        NodeCache nodeCache = new NodeCache(client,path);
        //nodeCache是对节点的缓存，参数默认为false。当为true时，表示缓存启动时，请求连接获取节点内容到缓存中。
//        nodeCache.start(true);
        nodeCache.start();

        nodeCache.getListenable().addListener(new NodeCacheListener() {
            //请求节点内容与缓存内容比较，发生变化触发监听器方法
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("123123");
            }
        });
        //这里相当于调用原生zookeeper ，只会调用一次watcher
        client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("监听变化："+ event);
            }
        }).forPath(path);

        System.in.read();
    }
}
