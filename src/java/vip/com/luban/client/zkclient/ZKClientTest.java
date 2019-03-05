package com.luban.client.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;

public class ZKClientTest {
    public static void main(String[] args) throws IOException {
        ZkClient zkClient = new ZkClient("localhost:2181",10000,10000,new SerializableSerializer());

//        zkClient.createPersistent("/data","1");

        zkClient.subscribeDataChanges("/data", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据被改了");
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });

        System.in.read();
    }
}
