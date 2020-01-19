package com.meitu.ablum.service;

import com.meitu.ablum.entity.Tester;

import java.util.List;

/**
 * @author yanrangnan
 * @date 2019/12/23
 */

public interface TesterService {
    List<Tester> getAllTester();

    Boolean addTester(Tester tester);

    Boolean deleteTester(Integer id);
}
