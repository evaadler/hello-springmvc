package com.fifi.controller;

import com.fifi.bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fifi
 */
@Controller
public class HelloWorld {

    // 1. 返回值是string

    @RequestMapping(value = "/hello")
    public String helloWorld(){
        return "helloWorld";
    }

    @RequestMapping("/login")
    public String login(Person person){
        System.out.println(person.getUsername());
        System.out.println(person.getPassword());
        return "helloWorld";
    }
}
