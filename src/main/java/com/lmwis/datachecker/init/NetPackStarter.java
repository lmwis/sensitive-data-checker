package com.lmwis.datachecker.init;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/24 4:39 下午
 * @Version: 1.0
 */
@Component
public class NetPackStarter {
    
    public NetPackStarter(){
        init();
    }

    private void init() {

        InetAddress addr;
        try {
            addr = InetAddress.getByName("10.111.34.251");//本机ip 127.0.0.1
            PcapNetworkInterface nif = Pcaps.getDevByAddress(addr);

            System.out.println(nif.getName());


            int snapLen = 64 * 1024;
            PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
            int timeout = 50;
            PcapHandle handle = nif.openLive(snapLen, mode, timeout);
            Packet packet = handle.getNextPacketEx();
            handle.close();
            IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
            Inet4Address srcAddr = ipV4Packet.getHeader().getSrcAddr();
            System.out.println(srcAddr);
//            PcapHandle.Builder phb = new PcapHandle.Builder(nif.getName()).snaplen(snaplen)
//                    .promiscuousMode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS).timeoutMillis(timeout)
//                    .bufferSize(1 * 1024 * 1024);
//            PcapHandle handle = phb.build();
//            String filter = "src host 192.168.11.219 && dst host 192.168.11.191";//过滤条件
//            handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);
//            PacketListener listener = new PacketListener() {
//                @Override
//                public void gotPacket(Packet packet) {
//                    System.out.println(packet);
//                    System.out.println("-----------------------------------------");
//                }
//            };
//            handle.loop(-1, listener);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
