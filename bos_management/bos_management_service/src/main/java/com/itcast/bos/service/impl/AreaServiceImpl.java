package com.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.AreaRepository;
import com.itcast.bos.domain.base.Area;
import com.itcast.bos.service.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:20:37 <br/>       
 */
@Transactional
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;
    
   
    @Override
    public void save(List<Area> list) {
          
        areaRepository.save(list);
        
    }


    @Override
    public Page<Area> findAll(Pageable pageable) {
        
       
        return areaRepository.findAll(pageable);
    }


    @Override
    public List<Area> findQ(String q) {
          //模糊查询
        q = "%" + q.toUpperCase() + "%";
        return   areaRepository.findQ(q);
    }

}
  
