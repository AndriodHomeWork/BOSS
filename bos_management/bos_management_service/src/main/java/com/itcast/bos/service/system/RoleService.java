package com.itcast.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itcast.bos.domain.system.Role;

/**  
 * ClassName:RoleAService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午10:58:14 <br/>       
 */
public interface RoleService {

    Page<Role> findAll(Pageable pageable);

    void save(Role model, String menuIds, Long[] permissionIds);

}
  
