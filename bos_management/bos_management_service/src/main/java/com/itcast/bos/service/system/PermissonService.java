package com.itcast.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itcast.bos.domain.system.Permission;

/**  
 * ClassName:PermissonService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午10:33:06 <br/>       
 */
public interface PermissonService {

    Page<Permission> findAll(Pageable pageable);

    void save(Permission model);

   

}
  
