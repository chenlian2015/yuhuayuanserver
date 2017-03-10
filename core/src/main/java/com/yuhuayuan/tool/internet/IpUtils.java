package com.yuhuayuan.tool.internet;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cl on 2017/3/8.
 */

public final class IpUtils {
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(ip == null || ip.trim().equals("") || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(ip == null || ip.trim().equals("") || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(ip == null || ip.trim().equals("") || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if(ip == null || ip.trim().equals("") || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if(ip == null || ip.trim().equals("") || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if(ip != null && !ip.trim().equals("")) {
            if(ip.indexOf(44) > 0) {
                ip = ip.substring(0, ip.indexOf(44));
            }

            return ip;
        } else {
            return "0.0.0.0";
        }
    }

    public static long ip2long(String strIp) {
        long[] ip = new long[4];
        int position1 = strIp.indexOf(46);
        int position2 = strIp.indexOf(46, position1 + 1);
        int position3 = strIp.indexOf(46, position2 + 1);
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String long2ip(long longIp) {
        StringBuilder sb = new StringBuilder();
        sb.append(longIp >>> 24).append('.').append((longIp & 16777215L) >>> 16).append('.').append((longIp & 65535L) >>> 8).append('.').append(longIp & 255L);
        return sb.toString();
    }

    private IpUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
