package com.eason.k8sjava.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author rongrong
 * @version 1.0
 * @description:
 * @date 2019/12/28 21:32
 */
@RestController
public class HelloWorld {

    @RequestMapping("/hello")

    public String hello() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        return "hello world , ip :" + ip;
    }
}
