package com.itcast.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.itcast.bos.crm.domain.Customer;
import com.itcast.bos.domain.base.Courier;
import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.service.FixedAreaService;
import com.itcast.bos.web.action.CommonAction;

import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;

import groovy.transform.PackageScope;
import net.sf.json.JsonConfig;

/**  
 * ClassName:fixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午8:57:41 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction  extends CommonAction<FixedArea>{

    public FixedAreaAction() {
        super(FixedArea.class);  
    }
    
    @Autowired
    private FixedAreaService fixedAreaService;
    
    
    @Action(value = "fixedAreaAction_save", results = {@Result(name = "success",
            location = "/pages/base/fixed_area.html", type = "redirect")})
    public String save() {
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
    
    @Action(value="fixedAreaAction_pageQuery")
   public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<FixedArea> page = fixedAreaService.findAll(pageable);
        
        JsonConfig jsonconfig = new JsonConfig();
        jsonconfig.setExcludes(new String[] {"subareas","couriers"});
        page2json(page, jsonconfig);
        
       return NONE;
   }
    
    // 向CRM系统发起请求,查询未关联定区的客户
    @Action(value = "fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException {
       List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssociated")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .getCollection(Customer.class);
       
       list2json(list, null);
        return NONE;
    }
    
 // 向CRM系统发起请求,查询已关联指定定区的客户
    @Action(value = "fixedAreaAction_findAssociatedCustomers")
    public String findAssociatedCustomers() throws IOException {
        
        System.out.println(getModel().getId());
        
        List<Customer> list = (List<Customer>) WebClient.create( "http://localhost:8180/crm/webService/customerService/findCustomersAssociated2FixedArea")
        .query("fixedAreaId", getModel().getId())
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .getCollection(Customer.class);
        
        list2json(list, null);
        
        return NONE;
        
    }
    
    
    // 使用属性驱动获取被选中的客户的id
    private Long[] customerIds;
    
    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }
    
    // 向CRM系统发起请求,关联客户
    @Action(value = "fixedAreaAction_assignCustomers2FixedArea",
                    results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html",
                    type = "redirect")})
    public String assignCustomers2FixedArea(){
        //把定区所有的关联客户取消关联
        //定区重新关联选中的客户
        WebClient.create( "http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .query("fixedAreaId",getModel().getId())  //定区ID
        .query("customerIds",customerIds)          //选择客户ID
        .put(null);

        return SUCCESS;
    }
    
    // 使用属性驱动获取快递员和时间的ID
    private Long courierId;
    private Long takeTimeId; 
    
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    
    //定区关联快递员 
    @Action(value ="fixedAreaAction_associationCourierToFixedArea" ,results = {@Result(name = "success",
            location = "/pages/base/fixed_area.html",
            type = "redirect")})
    public String associationCourierToFixedArea(){
        fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
        return SUCCESS;
    }
}
  
