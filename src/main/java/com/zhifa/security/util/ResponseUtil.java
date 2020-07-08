package com.zhifa.security.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Slf4j
public class ResponseUtil {
    /**
     *  使用response输出JSON
     * @param response
     * @param resultMap
     */
    public static void out(HttpServletResponse response, Map<String, Object> resultMap){

        ServletOutputStream out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            out = response.getOutputStream();
            out.write(new Gson().toJson(resultMap).getBytes());
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        } finally{
            if(out!=null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Map<String, Object> resultMap(boolean success, int code, String message) {
        Map<String, Object> map = new Hashtable<>();
        map.put("timestamp", new Date().getTime());
        map.put("success", success);
        map.put("code", code);
        map.put("message", message);

        return map;
    }
    public static Map<String, Object> resultMap(boolean success, int code, String message, String token) {
        Map<String, Object> map =resultMap(success, code, message);
        if (!ObjectUtils.isEmpty(token)) {
            map.put("token", token);
        }
        return map;
    }
}
