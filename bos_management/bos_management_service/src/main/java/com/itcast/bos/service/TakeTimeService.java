package com.itcast.bos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.domain.base.TakeTime;


/**  
 * ClassName:TakeTimeService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午3:15:44 <br/>       
 */

public interface TakeTimeService {

    List<TakeTime> listajax();
    
 
}
  
