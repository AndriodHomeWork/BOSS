package com.itcast.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.system.PermissonRepository;
import com.itcast.bos.domain.system.Permission;
import com.itcast.bos.service.system.PermissonService;

/**  
 * ClassName:PermissonServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午10:34:49 <br/>       
 */
@Service
@Transactional
public class PermissonServiceImpl implements PermissonService {

    @Autowired
    private PermissonRepository permissonRepository;
    @Override
    public Page<Permission> findAll(Pageable pageable) {
          
        return permissonRepository.findAll(pageable);
    }
    
    @Override
    public void save(Permission permisson) {
          
        permissonRepository.save(permisson);
        
    }

}
  
