package com.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.system.MenuRepository;
import com.itcast.bos.domain.system.Menu;
import com.itcast.bos.domain.system.User;
import com.itcast.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:04:27 <br/>       
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Override
    public List<Menu> findLevelOne() {
          
        return menuRepository.findByparentMenuIsNull();
    }
    @Override
    
    public void save(Menu menu) {
     System.out.println(menu);
     // 导致异常的原因是parentMenu字段是一个瞬时态的对象
     // 判断用户是否是要添加一个一级菜单
     if(menu.getParentMenu()!=null && menu.getParentMenu().getId()==null) {
         menu.setParentMenu(null);//这样加载的不是ParentMenu对象了，直接把ParentMenu属性置为null
     }
        menuRepository.save(menu);
        
    }
    @Override
    public Page<Menu> findAll(Pageable pageable) {
          
        
        return menuRepository.findAll(pageable);
    }
    
    
    
    @Override
    public List<Menu> findbyUser(User user) {
          
        //如果当前用户是admin
        if("admin".equals(user.getUsername())) {
            return menuRepository.findAll();
        }
        
        return menuRepository.findbyUser(user.getId());
    }

}
  
