package com.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itcast.bos.domain.system.Menu;

/**  
 * ClassName:MenuRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:05:39 <br/>       
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByparentMenuIsNull();

}
  
