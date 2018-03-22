package com.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.CourierRepository;
import com.itcast.bos.dao.FixedAreaRepository;
import com.itcast.bos.dao.TakeTimeRepository;
import com.itcast.bos.domain.base.Courier;
import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.domain.base.TakeTime;
import com.itcast.bos.service.FixedAreaService;

/**  
 * ClassName:fixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午9:04:25 <br/>       
 */
@Transactional
@Service
public class fixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public void save(FixedArea fixedArea) {
          
        fixedAreaRepository.save(fixedArea);
        
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
          
       
        return fixedAreaRepository.findAll(pageable);
    }

    
    //定区关联快递员
    @Override
    public void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId) {
          
     // 代码执行成功以后,快递员表发生update操作,快递员和定区中间表会发生insert操作

     // 持久态对象
        FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        
        // 建立快递员和时间的关联
        courier.setTakeTime(takeTime);
        
     // 建立快递员和定区的关联
       fixedArea.getCouriers().add(courier);//多多对的时候，一方放弃关系维护，放弃维护的一方不能建立关联
       
    // 建立快递员和定区的关联
       // 下面的写法是错的,因为在Courier实体类中fixedAreas字段的上方添加了mappedBy属性
       // 就代表快递员放弃了对关系的维护
       // courier.getFixedAreas().add(fixedArea);
        
    
        
    }
}
  
