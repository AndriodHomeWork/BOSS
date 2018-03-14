package com.itcast.bos.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctc.wstx.util.StringUtil;
import com.itcast.bos.dao.CourierRepository;
import com.itcast.bos.domain.base.Courier;
import com.itcast.bos.service.CourierService;

/**  
 * ClassName:courierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:35:25 <br/>       
 */

@Transactional
@Service
public class courierServiceImpl implements CourierService {
    
    @Autowired
    private CourierRepository courierRepository;
   
    @Override
    public void save(Courier courier) {
          
        courierRepository.save(courier) ;
        
    }

    @Override
    public Page<Courier> findAll(Pageable pageable) {
          
        
        return courierRepository.findAll(pageable);
    }

    @Override
    public void batchDel(String ids) {
        // 真实开发中只有逻辑删除
        // null " "
        // 判断数据是否为空
       if(StringUtils.isNotEmpty(ids)) {
        // 切割数据
           String[] split = ids.split(",");
           for (String id : split) {
               courierRepository.updateDelTagById(Long.parseLong(id));
        }
       }
        
    }

}
  
