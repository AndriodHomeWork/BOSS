package com.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.SubareaRepository;
import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.domain.base.SubArea;
import com.itcast.bos.service.SubareaService;

/**  
 * ClassName:SubareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午5:21:44 <br/>       
 */
@Transactional
@Service
public class SubareaServiceImpl implements SubareaService {

    @Autowired
    private SubareaRepository  subareaRepository;
    
    @Override
    public void save(SubArea subArea) {
        subareaRepository.save(subArea);
    }

    @Override
    public Page<SubArea> findAll(Pageable pageable) {
          
      
        return  subareaRepository.findAll(pageable);
    }

 //查询未关联的分区
    @Override
    public List<SubArea> findUnAssociatedSubAreas() {
       return  subareaRepository.findByFixedAreaIsNull();
    }
//查询已经关联的分区
    @Override
    public List<SubArea> findAssociatedSubAreas(Long fixedAreaId) {
          //SubArea里面没有fixedAreaId这个字段，只有fixedArea对象
     // 使用SpringDataJPA的命名规范进行查询的时候，
        // 如果字段是对象，必须是单一对象，不能是集合
        // 传入的参数必须指定id属性
        FixedArea fixedArea = new FixedArea();
        fixedArea.setId(fixedAreaId);
         return subareaRepository.findByFixedArea(fixedArea);
        
    }

   
}
  
