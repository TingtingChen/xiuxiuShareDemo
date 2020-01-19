package com.meitu.ablum.service.serviceImpl;

import com.meitu.ablum.entity.Missingdata;
import com.meitu.ablum.mapper.MissingdataMapper;
import com.meitu.ablum.service.MissingdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissingdataServiceImpl implements MissingdataService {
    @Autowired
    private MissingdataMapper missingdataMapper;

    @Override
    public List<Missingdata> selectAll() {
        return missingdataMapper.selectAll();
    }
}
