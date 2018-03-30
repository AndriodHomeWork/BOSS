package com.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("select m from Menu m inner join m.roles r inner join r.users u where u.id=?")
    List<Menu> findbyUser(Long id);

}
  
