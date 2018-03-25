package com.itcast.bos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itcast.bos.domain.base.Area;
import com.itcast.bos.domain.base.Courier;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:22:51 <br/>       
 */
@Repository
public interface AreaRepository extends JpaRepository<Area, Long>,JpaSpecificationExecutor<Courier>{

    @Query("from Area where province like ?1 or  city like ?1  or  district like ?1  or  postcode like ?1  or  citycode like ?1  or  shortcode like ?1")
    List<Area> findQ(String q);

    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}
  
