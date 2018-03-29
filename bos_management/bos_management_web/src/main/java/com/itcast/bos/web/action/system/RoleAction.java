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
import com.itcast.bos.domain.system.Role;
import com.itcast.bos.service.system.RoleService;
import com.itcast.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午10:56:20 <br/>       
 */
@Controller
@Scope("prototype") 
@Namespace("/") 
@ParentPackage("struts-default")
public class RoleAction extends CommonAction<Role> {

    public RoleAction() {
        super(Role.class);  
    }
    
    @Autowired
    private RoleService roleService;
    
    //分页
    @Action(value = "roleAction_pageQuery")
     public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Role> page = roleService.findAll(pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] { "users", "permissions", "menus"});
        
        page2json(page, jsonConfig);
        
         return NONE;
     }
    
    //属性驱动获得 权限 和 菜单
    private String menuIds;
    private Long[] permissionIds;

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    
    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }
    //保存角色
    @Action(value = "roleAction_save",
            results = {@Result(name = "success", location = "/pages/system/role.html", type = "redirect"),})
    public String menuAction_save() {
        
       roleService.save(getModel(),menuIds,permissionIds);
        
        return SUCCESS;
    }
    
    @Action(value="roleAction_findAll")
    public String findAll() throws IOException {
        
        // struts框架在封装数据的时候优先封装给模型对象的,,会导致属性驱动中的page对象无法获取数据
        Page<Role> page = roleService.findAll(null);
        
        List<Role> list = page.getContent();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] { "users", "permissions", "menus" });
        list2json(list, jsonConfig);
        return NONE;
    }
    

}
  
