package com.itcast.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itcast.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午5:21:24 <br/>       
 */
public interface SubareaService {

    void save(SubArea subArea);

    Page<SubArea> findAll(Pageable pageable);

    List<SubArea> findUnAssociatedSubAreas();

    List<SubArea> findAssociatedSubAreas(Long fixedAreaId);




}
  
