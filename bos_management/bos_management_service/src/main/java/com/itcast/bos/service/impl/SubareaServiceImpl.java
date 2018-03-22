package com.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.SubareaRepository;
import com.itcast.bos.domain.base.SubArea;
import com.itcast.bos.service.SubareaService;

/**  
 * ClassName:SubareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午5:21:44 <br/>       
 */
@Transactional
@Service
public class SubareaServiceImpl implements SubareaService {

    @Autowired
    private SubareaRepository  subareaRepository;
    
    @Override
    public void save(SubArea subArea) {
        subareaRepository.save(subArea);
    }

    @Override
    public Page<SubArea> findAll(Pageable pageable) {
          
      
        return  subareaRepository.findAll(pageable);
    }

}
  
