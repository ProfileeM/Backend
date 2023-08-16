package com.example.profileem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        String testStr = "hi";
        System.out.println(testStr);
        return testStr;
    }
}
