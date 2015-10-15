package com.zohaltech.app.sigma.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WifiStats
{
    public static int TxBytes = 1;
    public static int RxBytes = 2;

    public static long getTotalBytes(int type)
    {
        String filePath = (type == 1 ? "/sys/class/net/wlan0/statistics/tx_bytes" : "/sys/class/net/wlan0/statistics/rx_bytes");
        long bytes = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            bytes = Long.valueOf(br.readLine());
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return bytes;
    }
}
