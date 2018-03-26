package com.itcast.bos.web.action.take_delivery;

import javax.ws.rs.Produces;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcast.bos.domain.take_delivery.WayBill;
import com.itcast.bos.service.take_delivery.WayBillService;
import com.itcast.bos.web.action.CommonAction;

/**  
 * ClassName:WaybillAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午2:49:25 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WaybillAction extends CommonAction<WayBill>{

    public WaybillAction() {
        super(WayBill.class);  
    }
    
    @Autowired
    private WayBillService wayBillService ;
  
    //属性驱动接受参数
    public String product;
    
    public void setProduct(String product) {
        this.product = product;
    }
    
    @Action(value="waybillAction_save")
    public String save() {
        wayBillService.save(getModel());
        return NONE;
    }

}
  
