package com.luban.client.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;
import java.util.concurrent.TimeUnit;

/*
*   这里多个客户端争取一把锁，获取leader权
*   leader来管控各个节点信息
* */

public class LeaderSelectorExample {

    public static void main(String[] args) throws Exception {

        List<CuratorFramework> clients = Lists.newArrayList();

        List<LeaderSelector> leaderSelectors = Lists.newArrayList();
        //模拟十个连接，利用该框架进行领导者选举
        for (int i = 0; i < 10; i++) {
            CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(1, 1000));
            clients.add(client);
            client.start();

            LeaderSelector leaderSelector = new LeaderSelector(client, "/LeaderLatch", new LeaderSelectorListener() {
                @Override
                public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                    //当上leader就会进入这个方法
                    System.out.println("当前leader是：" + client);
                    //这里表示获取leader权后执行以下操作，否则就会释放leader权
                    //表示5秒后，选举另外一个leader
                    TimeUnit.SECONDS.sleep(5);
                }

                @Override
                public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

                }
            });
            leaderSelectors.add(leaderSelector);
            leaderSelector.start();

        }
        System.in.read();
        for (CuratorFramework client : clients) {
            client.close();
        }
        for (LeaderSelector leaderSelector : leaderSelectors) {
            leaderSelector.close();
        }
    }
}
