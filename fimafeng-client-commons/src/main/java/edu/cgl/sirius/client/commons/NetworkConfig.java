package edu.cgl.sirius.client.commons;

public class NetworkConfig {
    private String ipaddress;
    private int tcpport;

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public int getTcpport() {
        return tcpport;
    }

    public void setTcpport(int tcpport) {
        this.tcpport = tcpport;
    }

    @Override
    public String toString() {
        return "NetworkConfig{" +
                "ipAddress='" + ipaddress + '\'' +
                ", tcpPort=" + tcpport +
                '}';
    }
}
