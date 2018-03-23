package com.itcast.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaReposity <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午5:22:12 <br/>       
 */
public interface SubareaRepository extends JpaRepository<SubArea, Long> {

    List<SubArea> findByFixedAreaIsNull();

    //SubArea里面没有fixedAreaId这个字段，只有fixedArea对象
    // 使用SpringDataJPA的命名规范进行查询的时候，
       // 如果字段是对象，必须是单一对象，不能是集合
       // 传入的参数必须指定id属性
    List<SubArea> findByFixedArea(FixedArea fixedArea);

}
  
