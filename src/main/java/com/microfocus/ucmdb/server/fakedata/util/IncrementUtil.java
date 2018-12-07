package com.microfocus.ucmdb.server.fakedata.util;

import com.microfocus.ucmdb.server.fakedata.util.IpAddressUtil;

public class IncrementUtil {
    static private Integer globalCount = 0;
    static public String  generateIncrementalValue(String value, Integer count){
        if(count == 0){
            count = ++globalCount;
        }
        String rlt = "";

        // num value

        // Ipaddress value
        if(IpAddressUtil.isIPAddress(value)){
            return IpAddressUtil.convertIPFromInt2tring(IpAddressUtil.convertIPFromString2Int(value) + count - 1);
        }

        // String value
        return value + "_" + count;

    }
}
