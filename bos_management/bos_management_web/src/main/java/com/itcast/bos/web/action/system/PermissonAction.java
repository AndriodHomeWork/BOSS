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

import com.itcast.bos.domain.system.Menu;
import com.itcast.bos.domain.system.Permission;
import com.itcast.bos.service.system.PermissonService;
import com.itcast.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:PermissonAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午10:23:21 <br/>       
 */
@Controller
@Scope("prototype") 
@Namespace("/") 
@ParentPackage("struts-default")
public class PermissonAction extends CommonAction<Permission> {

    public PermissonAction() {
        super(Permission.class);  
    }
    @Autowired
    private PermissonService  permissonService;
    
    //分页
   @Action(value="permissonAction_pageQuery")
    public String pageQuery() throws IOException {
       Pageable pageable = new PageRequest(page-1, rows);
       Page<Permission> page = permissonService.findAll(pageable);
       JsonConfig jsonConfig = new JsonConfig();
       jsonConfig.setExcludes(new String[] {"roles"});
       
       page2json(page, jsonConfig);
        return NONE;
    }
   
  
   //保存
   @Action(value = "permissonAction_save",
           results = {@Result(name = "success",  location = "/pages/system/permission.html", type = "redirect"),})
   public String save() {
       
       permissonService.save(getModel());
       
       return SUCCESS;
   }
   
 //动态获取所有权限
   @Action(value = "permissionAction_findAll")
   public String findAll() throws IOException {
      
       Page<Permission> page = permissonService.findAll(null);
       List<Permission> list = page.getContent();
       
       //返回json数据
       JsonConfig jsonConfig = new JsonConfig();
       
       jsonConfig.setExcludes(new String[] {"roles"});
       list2json(list, jsonConfig);
       return NONE;
       
   }

}
  
