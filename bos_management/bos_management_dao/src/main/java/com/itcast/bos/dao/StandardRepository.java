package com.itcast.bos.dao;  
/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午7:51:44 <br/>       
 */

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.domain.base.Standard;

@Repository
public interface StandardRepository extends JpaRepository<Standard, Long> {
   
  
    
    
    
    
    
    
    public List<Standard> findByName(String name);
    
 // 根据用户名和最大长度进行查询
    @Query("from Standard where name=? and maxLength = ?")//JPQL === HQL
    public List<Standard> findByNameAndMaxLength(String name,Integer maxLength);
    
 // 根据用户名和最大长度进行查询
    @Query("from Standard where name=?2 and maxLength = ?1")//JPQL === HQL
    public List<Standard> findByNameAndMaxLength(Integer maxLength,String name);
    
   
    
   
    @Transactional //自定义增删改 操作都要开事务
    @Modifying // 所有的更新数据的操作,都要添加这个注解
    @Query("delete from Standard   where name = ?")
   void deleteByName(String name);
    
    @Transactional
    @Modifying
    @Query("update from Standard set maxLength = ? where name = ?")
    void  updateMaxLengthByName(Integer maxLength,String name);

   
    
    
}
  
