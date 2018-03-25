package com.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itcast.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderReposity <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 上午10:42:13 <br/>       
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
  
