package com.itcast.bos.web.action.system;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itcast.bos.domain.system.User;
import com.itcast.bos.service.system.UserService;
import com.itcast.bos.web.action.CommonAction;

import freemarker.template.utility.SecurityUtilities;
import net.sf.json.JsonConfig;

/**  
 * ClassName:UserAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午6:04:34 <br/>       
 */
@Controller
@Scope("prototype") 
@Namespace("/") 
@ParentPackage("struts-default")

public class UserAction extends CommonAction<User> {

    public UserAction()  {
        super(User.class);  
    }
    
 // 用户输入的验证码
    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Action(value = "userAction_login",
            results = {
                    @Result(name = "success", location = "/index.html",
                            type = "redirect"),
                    @Result(name = "login", location = "/login.html",
                            type = "redirect")})
     public String login() {
        
        
       String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
       
       if(StringUtils.isNotEmpty(serverCode)&&StringUtils.isNotEmpty(checkcode)&&checkcode.equals(serverCode)) {
        // 主体,代表当前用户
          Subject subject = SecurityUtils.getSubject();
          // 使用代码校验权限---不推荐
          // subject.checkPermission("");
          
          
       // 创建令牌
          AuthenticationToken token =  new UsernamePasswordToken(getModel().getUsername(),getModel().getPassword());
                  
          try {
           // 执行登录
            subject.login(token);
            // 方法的返回值由Realm中doGetAuthenticationInfo方法定义SimpleAuthenticationInfo对象的时候,第一个参数决定的
            User user = (User) subject.getPrincipal();
            
            //将登陆的当前用户存到session中
            ServletActionContext.getRequest().getSession().setAttribute("user", user);
            
            return SUCCESS;
        }  catch (UnknownAccountException e) {
            // 用户名写错了
            e.printStackTrace();
            System.out.println("用户名写错了");
        } catch (IncorrectCredentialsException e) {
            // 用户名写错了
            e.printStackTrace();
            System.out.println("密码错误");
        } catch (Exception e) {
            // 用户名写错了
            e.printStackTrace();
            System.out.println("其他错误");
       }
    
        }
        return LOGIN;
    }
    
    //用户注销---把用户从session中删除
    @Action(value = "userAction_logout",
            results = {@Result(name = "success", location = "/login.html", type = "redirect"),})
    public String logout() {
        
        // 注销
        Subject subject = SecurityUtils.getSubject();
        subject.logout();


        // 上面底层原理实现其实就是清空Session
       // ServletActionContext.getRequest().getSession().removeAttribute("user");
        return SUCCESS;
    }
    
    
    
    
    @Autowired
    private UserService userService;
    
    
    // 使用属性驱动获取角色的ID
    private Long[] roleIds;

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    //保存用户
    @Action(value = "userAction_save", results = {
            @Result(name = "success", location = "/pages/system/userlist.html", type = "redirect") })

    public String save() {
        
        userService.save(getModel(),roleIds);
        
        return SUCCESS;
    }
    
    @Action(value="userAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable  pageable = new PageRequest(page-1, rows);
       Page<User> page =  userService.findAll(pageable);
       JsonConfig jsonconfig = new JsonConfig();
       jsonconfig.setExcludes(new String[] {"roles"});
          page2json(page, jsonconfig);
                
        return NONE;
    }
}
    
    
    
    


  
