package com.itcast.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.itcast.bos.domain.system.Menu;
import com.itcast.bos.service.system.MenuService;
import com.itcast.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午7:56:44 <br/>       
 */
@Controller
@Scope("prototype") 
@Namespace("/") 
@ParentPackage("struts-default")
public class MenuAction extends CommonAction<Menu> {

    public MenuAction() {
        super(Menu.class);  
    }
    @Autowired
    private MenuService  menuService;
    
    
    //找到父类菜单
    @Action(value = "menuAction_findLevelOne")
    public String menuAction_findLevelOne() throws IOException {
       
        List<Menu> list = menuService.findLevelOne();
        
        //返回json数据
        JsonConfig jsonConfig = new JsonConfig();
        /*
         * 访问的时候会发生net.sf.json.JSONException: There is a cycle in the hierarchy! 就是死循环,子菜单查询父菜单,
         * 父菜单又去查询子菜单导致的死循环, 解决方法就是忽略parentMenu字段.
         */
        jsonConfig.setExcludes(new String[] {"roles","childrenMenus","parentMenu"});
        list2json(list, jsonConfig);
        return NONE;
        
    }
    
    
    //保存菜单数据
    @Action(value = "menuAction_save",
            results = {@Result(name = "success", location = "/pages/system/menu.html", type = "redirect"),})
    public String menuAction_save() {
        
        menuService.save(getModel());
        
        return SUCCESS;
    }
    
    @Action(value="menuAction_pageQuery")
    public String pageQuery() throws IOException {
       
        // struts框架在封装数据的时候优先封装给模型对象的,,会导致属性驱动中的page对象无法获取数据
        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage()) -1, rows);
        Page<Menu> page = menuService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles","childrenMenus","parentMenu"});
        page2json(page, jsonConfig);
        return NONE;
    }

}
  
