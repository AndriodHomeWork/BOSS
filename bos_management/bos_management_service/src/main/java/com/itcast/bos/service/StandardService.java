package com.itcast.bos.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itcast.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:33:23 <br/>       
 */
public interface StandardService {

    void save(Standard standard);

    Page<Standard> findAll(Pageable pageable);

}
  
