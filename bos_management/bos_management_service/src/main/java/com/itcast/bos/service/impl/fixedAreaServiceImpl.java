package com.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.FixedAreaRepository;
import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.service.FixedAreaService;

/**  
 * ClassName:fixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午9:04:25 <br/>       
 */
@Transactional
@Service
public class fixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Override
    public void save(FixedArea fixedArea) {
          
        fixedAreaRepository.save(fixedArea);
        
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
          
       
        return fixedAreaRepository.findAll(pageable);
    }
}
  
