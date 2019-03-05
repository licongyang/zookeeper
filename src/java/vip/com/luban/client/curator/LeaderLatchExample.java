package com.luban.client.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;
import java.util.concurrent.TimeUnit;

/*
    利用curator框架的leaderlatch实现领导者选举
    在节点下面建立一堆临时节点，取最小的节点，这里最小并不是指id最小
 */
public class LeaderLatchExample {
    public static void main(String[] args) throws Exception {

        List<CuratorFramework> clients = Lists.newArrayList();

        List<LeaderLatch> leaderLatches = Lists.newArrayList();
        //模拟十个连接，利用该框架进行领导者选举
        for (int i = 0; i < 10; i++) {
            CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(1, 1000));
            clients.add(client);
            client.start();

            LeaderLatch leaderLatch = new LeaderLatch(client, "/LeaderLatch", "client#" + i);
            leaderLatches.add(leaderLatch);
            leaderLatch.start();

        }

        //等待5秒钟
        TimeUnit.SECONDS.sleep(5);

        for (LeaderLatch leaderLatch : leaderLatches) {
            if (leaderLatch.hasLeadership()) {
                System.out.println("当前Leader 是" + leaderLatch.getId());
                break;
            }
        }
        System.in.read();
        for (CuratorFramework client : clients) {
            client.close();
        }
        for (LeaderLatch leaderLatch : leaderLatches) {
            leaderLatch.close();
        }
    }

}
