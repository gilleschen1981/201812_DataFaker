package com.microfocus.ucmdb.server.fakedata.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressUtil {
    /**
     * change ipaddress to long type for calculation purpose
     * @param ip : 192.168.0.1
     * @return : 3232235521
     */
    static public long convertIPFromString2Int(String ip){
        String[] split = ip.split("\\.");
        if(split.length != 4){
            return -1;
        }
        long sum = 0;
        for(String v : split){
            sum *= 256;
            sum += Integer.valueOf(v);
        }

        return sum;
    }

    /**
     * change ip to String for readability
     * @param ip : 3232235521
     * @return : 192.168.0.1
     */
    static public String convertIPFromInt2tring(long ip){
        StringBuilder rlt = new StringBuilder();
        while(ip > 0){
            long number = ip%256;
            rlt = rlt.insert(0, new String(String.valueOf(number) + "."));
            ip = (ip - number)/256;
        }
        rlt.deleteCharAt(rlt.length() - 1);
        return rlt.toString();
    }

    static public boolean isIPAddress(String ip){
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

}
