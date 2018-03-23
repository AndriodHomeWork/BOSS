package com.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.TakeTimeRepository;
import com.itcast.bos.domain.base.TakeTime;
import com.itcast.bos.service.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午3:17:35 <br/>       
 */
@Transactional
@Service
public class TakeTimeServiceImpl implements TakeTimeService {
    
    @Autowired
    private  TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> listajax() {
       
        return takeTimeRepository.findAll();
    }
}
  
