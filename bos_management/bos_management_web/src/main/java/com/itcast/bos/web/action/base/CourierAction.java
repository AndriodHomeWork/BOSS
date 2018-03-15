package com.itcast.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
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
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{
    
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
    public String pageQuery() throws IOException {
        /*Pageable pageable= new PageRequest(page-1, rows);
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
        return NONE;*/
        Specification<Courier> specification = new Specification<Courier>() {
                @Override
                public Predicate toPredicate(Root<Courier> root,
                        CriteriaQuery<?> query, CriteriaBuilder cb) {
                    String courierNum = model.getCourierNum();
                    String company = model.getCompany();
                    String type = model.getType();
                    Standard standard = model.getStandard();
                    // 存储条件的集合
                    List<Predicate> list= new ArrayList();
                    if(StringUtils.isNotEmpty(courierNum)) {
                        Predicate p1 =cb.equal(root.get("courierNum").as(String.class), courierNum);
                        list.add(p1);
                    }
                    
                    if(StringUtils.isNotEmpty(company)) {
                        Predicate p2 =cb.like(root.get("company").as(String.class), "%"+company+"%");
                        list.add(p2);
                    }
                    
                    if(StringUtils.isNotEmpty(type)) {
                        Predicate p3 =  cb.equal(root.get("type").as(String.class), type);
                        list.add(p3);
                    }
                    if(standard != null) {
                    String name = standard.getName();
                    if (StringUtils.isNotEmpty(name)) {
                        // 连表查询,查询标准的名字//TODO
                        Join<Object, Object> join = root.join("standard");
                        Predicate p4 = cb.equal(join.get("name").as(String.class), name);
                        list.add(p4);
                        }
                    }
                    
                    // 用户没有输入查询条件
                    if(list.size()==0) {
                        return null;
                    }
                 // 用户输入了查询条件
                 // 将多个条件进行组合
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                // 用户输入了多少个条件,就让多少个条件同时都满足
                Predicate predicate = cb.and(arr);

                return predicate;                
                    
                }
        };

        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Courier> page = courierService.findAll(specification, pageable);

        long total = page.getTotalElements();
        List<Courier> content = page.getContent();

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", content);

        // 灵活控制输出的内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});

        String json = JSONObject.fromObject(map, jsonConfig).toString();

        // 在实际开发的时候,为了提高服务器的性能,把前台页面不需要的数据都应该忽略掉
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
  
