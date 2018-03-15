package com.itcast.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itcast.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:33:39 <br/>       
 */
public interface CourierService {

    void save(Courier model);

    Page<Courier> findAll( Pageable pageable);
    Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);
    
    void batchDel(String ids);

   

   

}
  
