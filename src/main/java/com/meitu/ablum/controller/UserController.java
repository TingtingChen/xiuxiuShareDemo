package com.meitu.ablum.controller;

import com.meitu.ablum.entity.Tester;
import com.meitu.ablum.service.TesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author guoshuang
 * @date 2020/1/13
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    TesterService testerService;

    @PostMapping("addUser")
    @CrossOrigin(maxAge = 3600)
    public Boolean addTester(Tester tester){
        return testerService.addTester(tester);
    }

    @CrossOrigin(maxAge = 3600)
    @PostMapping("delUser")
    public Boolean deleteTester(String id){
        return testerService.deleteTester(Integer.parseInt(id));
    }

    @GetMapping("testAdd")
    public void test(){
        Tester tester = new Tester();
        tester.setUsername("郭霜");
        tester.setNickname("郭霜");
        tester.setEmail("gs2@meitu.com");
        addTester(tester);
    }

}
