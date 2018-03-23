package com.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itcast.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午3:14:37 <br/>       
 */
@Repository
public interface TakeTimeRepository extends JpaRepository<TakeTime, Long> {

}
  
