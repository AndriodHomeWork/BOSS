package com.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.StandardRepository;
import com.itcast.bos.domain.base.Standard;
import com.itcast.bos.service.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:35:03 <br/>       
 */

@Service
@Transactional
public class StandardServiceImpl implements StandardService {

    
    @Autowired
    private StandardRepository standardRepository;
    
    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    
    //分页查询
    @Override
    public Page<Standard> findAll(Pageable pageable) {
        
        return  standardRepository.findAll(pageable);
    }


   

}
  
