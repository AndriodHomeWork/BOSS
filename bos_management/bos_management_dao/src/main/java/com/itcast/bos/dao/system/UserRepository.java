package com.itcast.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itcast.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午8:20:56 <br/>       
 */
//继承的JpaRepository实现类上面打了@Repository注解，所以接口就不用打了。用的是接口的而实现类的方法
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
  
