package com.meitu.ablum.service;

import com.meitu.ablum.entity.Missingdata;
import com.meitu.ablum.mapper.MissingdataMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface MissingdataService {

     List<Missingdata> selectAll();
}
