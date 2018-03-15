package com.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.itcast.bos.domain.base.Area;
import com.itcast.bos.domain.base.Courier;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:22:51 <br/>       
 */
@Repository
public interface AreaRepository extends JpaRepository<Area, Long>,JpaSpecificationExecutor<Courier>{

}
  
