package com.itcast.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.take_delivery.WayBillRepository;
import com.itcast.bos.domain.take_delivery.WayBill;
import com.itcast.bos.service.take_delivery.WayBillService;

/**  
 * ClassName:WayBillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午3:19:46 <br/>       
 */
@Transactional
@Service
public class WayBillServiceImpl implements WayBillService {
    
    @Autowired
    private WayBillRepository wayBillRepository;
    
    @Override
    public void save(WayBill wayBill) {

        wayBillRepository.save(wayBill);

    }

}
  
