package com.luban.client.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class ZKClientWatchTest {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("localhost:2181",10000,10000,new SerializableSerializer());
        zkClient.writeData("/data","7");
    }
}
