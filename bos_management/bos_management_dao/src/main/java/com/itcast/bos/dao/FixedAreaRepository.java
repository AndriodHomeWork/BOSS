package com.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itcast.bos.domain.base.FixedArea;

/**  
 * ClassName:fixedAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午9:02:20 <br/>       
 */
@Repository
public interface FixedAreaRepository extends JpaRepository<FixedArea, Long> {

}
  
