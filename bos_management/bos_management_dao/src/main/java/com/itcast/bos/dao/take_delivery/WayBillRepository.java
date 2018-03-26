package com.itcast.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itcast.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:WayBillRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午3:20:40 <br/>       
 */
@Repository
public interface WayBillRepository extends JpaRepository<WayBill, Long>{

}
  
