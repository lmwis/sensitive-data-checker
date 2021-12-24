package com.lmwis.datachecker.init;

import org.junit.Test;
import org.pcap4j.core.*;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.util.NifSelector;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/24 4:59 下午
 * @Version: 1.0
 */
public class NetPackStarterTest {
    private static final String COUNT_KEY = NetPackStarterTest.class.getName() + ".count";
    private static final int COUNT = Integer.getInteger(COUNT_KEY, 5);
    private static final String READ_TIMEOUT_KEY = NetPackStarterTest.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]
    private static final String SNAPLEN_KEY = NetPackStarterTest.class.getName() + ".snaplen";
    private static final int SNAPLEN = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]


    @Test
    public void loopTest() throws PcapNativeException, NotOpenException {
        String filter = "ip and tcp and (dst host 127.0.0.1 and dst port 80)"; // 设置过滤的字符串

        // 设置要抓包的网卡
        PcapNetworkInterface nif;
        try {
            nif = new NifSelector().selectNetworkInterface(); // 这个方法提供用户输入网卡的序号
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (nif == null) {
            return;
        }

        // 实例化一个捕获报文的对象，设置抓包参数：长度，混杂模式，超时时间等
        final PcapHandle handle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);

        // 设置过滤器
        if (filter.length() != 0) {
            handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
        }

        // 观察者模式，抓到报文回调gotPacket方法处理报文内容
        PacketListener listener =
                new PacketListener() {
                    @Override
                    public void gotPacket(Packet packet) {
                        // 开始处理报文

                        /* 您捕获的数据包包括某些协议的标头和有效负载，如以太网，IPv4和TCP。
                         * Pcap4J的Packet API使您可以从协议标头中获取信息。
                         */

                        IpV4Packet ipV4Packet = packet.get(IpV4Packet.class); // 直接获取IpV4报文
                        Inet4Address srcAddr = ipV4Packet.getHeader().getSrcAddr();
                        System.out.println(srcAddr); // 输出源IP地址

                        // 可以直接get你想要的报文类型，只要Pcap4J库原生支持
                        EthernetPacket ethernetPacket = packet.get(EthernetPacket.class); // 以太网报文
                        TcpPacket tcpPacket = packet.get(TcpPacket.class); // TCP报文

                        // 也可以通过getPayload()的方式一层一层读取
                        EthernetPacket.EthernetHeader ethernetHeader = ethernetPacket.getHeader(); // 读取以太网帧头部
                        IpV4Packet ipV4Packet2 = (IpV4Packet)tcpPacket.getPayload(); // 注意get出来的类型，强转可能抛异常

                        // 若需要解析的协议Pcap没有支持，那就需要自己实现这个报文的Java类，然后写反序列化方法了
                        byte[] rawData = ethernetPacket.getRawData(); // 获取以太网的原始二进制数据

                        // 然后调你自己对应的反序列化方法解析这个二进制
                        // TO-DO ...
                    }
                };

        // 直接使用loop无限循环处理包
        try {
            handle.loop(COUNT, listener); // COUNT设置为抓包个数，当为-1时无限抓包
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handle.close();
    }
    @Test
    public void initTest(){
        InetAddress addr;
        try {
            addr = InetAddress.getByName("10.111.34.251");//本机ip 127.0.0.1
            PcapNetworkInterface nif = Pcaps.getDevByAddress(addr);

            System.out.println(nif.getName());


            int snapLen = 64 * 1024;
//            PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
            int timeout = 50;
//            PcapHandle handle = nif.openLive(snapLen, mode, timeout);
//            Packet packet = handle.getNextPacketEx();
//            handle.close();
//            IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
//            Inet4Address srcAddr = ipV4Packet.getHeader().getSrcAddr();
//            System.out.println(srcAddr);
            PcapHandle.Builder phb = new PcapHandle.Builder(nif.getName()).snaplen(snapLen)
                    .promiscuousMode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS).timeoutMillis(timeout)
                    .bufferSize(1 * 1024 * 1024);
            PcapHandle handle = phb.build();
            String filter = "src host 10.111.34.251 && dst host 10.111.34.251";//过滤条件
            handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
            PacketListener listener = new PacketListener() {
                @Override
                public void gotPacket(Packet packet) {
                    System.out.println(packet);
                    System.out.println("-----------------------------------------");
                }
            };
            handle.loop(-1, listener);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
