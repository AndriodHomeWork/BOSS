package com.itcast.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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

import com.itcast.bos.domain.base.Courier;
import com.itcast.bos.domain.base.Standard;
import com.itcast.bos.service.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:courierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:23:59 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class courierAction extends ActionSupport implements ModelDriven<Courier>{
    
    private Courier model = new Courier();
   

    @Override
    public Courier getModel() {
          
        return model;
    }
    
    @Autowired
    private CourierService courierService;

    @Action(value = "courierAction_save", results = {@Result(name = "success",
            location = "/pages/base/courier.html", type = "redirect")})
    public String save() {
        courierService.save(model);
        return SUCCESS;
    }
    
  //使用属性驱动获得数据
    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    @Action(value="courierAction_pageQuery")
    public String courierAction_pageQuery() throws IOException {
        Pageable pageable= new PageRequest(page-1, rows);
        Page<Courier> page = courierService.findAll(pageable);
        
        
        // 总数据条数
        long total = page.getTotalElements();
        // 当前页要实现的内容
        List<Courier> list = page.getContent();
        
        // 封装数据
        Map<String, Object> map = new HashMap<>();
        
        map.put("total", total);
        map.put("rows", list);
        
     // 灵活控制输出的内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});

        String json = JSONObject.fromObject(map, jsonConfig).toString();

        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
    
    //属性驱动获取数据
    private String ids;
    
    public void setIds(String ids) {
        this.ids = ids;
    }
    //批量删除快递员
    @Action(value = "courierAction_batchDel",
            results = {@Result(name = "success",
            location = "/pages/base/courier.html", type = "redirect")})
    public String batchDel() {
        courierService.batchDel(ids);
        return SUCCESS;
    }
}
  
