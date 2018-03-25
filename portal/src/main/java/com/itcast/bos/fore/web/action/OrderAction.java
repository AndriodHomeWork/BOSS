package com.itcast.bos.fore.web.action;

import javax.jws.WebService;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ctc.wstx.util.StringUtil;
import com.itcast.bos.domain.base.Area;
import com.itcast.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 上午9:38:54 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    private Order model = new Order();
    
    @Override
    public Order getModel() {
        return model;
    }

    // 使用属性驱动获取发件和收件的区域信息
    private String recAreaInfo;
    private String sendAreaInfo;
    
    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }
    
    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }
    
    
    @Action(value = "orderAction_add", results = {@Result(name = "success",
            location = "/index.html", type = "redirect")})
    public String saveOrder(){
      //属性驱动封装的两个字段保存之前做飞空判断
        if(StringUtils.isNotEmpty(recAreaInfo)) {
            //**省/**市/**区
            String[] split = recAreaInfo.split("/");
            String province = split[0];
            String city = split[1];
            String district = split[2];
            
            //去掉省市区
            province = province.substring(0, province.length()-1);
                city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            
            //封装数据
            Area recArea = new Area();
            recArea.setProvince(province);
            recArea.setCity(city);
            recArea.setDistrict(district);
            
            //Order类保存Area字段
            model.setRecArea(recArea);
          
        }
        
        if(StringUtils.isNotEmpty(sendAreaInfo)) {
          //属性驱动封装的两个字段保存之前做飞空判断
            if(StringUtils.isNotEmpty(sendAreaInfo)) {
                //**省/**市/**区
                String[] split = sendAreaInfo.split("/");
                String province = split[0];
                String city = split[1];
                String district = split[2];
                
                //去掉省市区
                province = province.substring(0, province.length()-1);
                    city = city.substring(0, city.length()-1);
                district = district.substring(0, district.length()-1);
                
                //封装数据
                Area sendArea = new Area();
                sendArea.setProvince(province);
                sendArea.setCity(city);
                sendArea.setDistrict(district);
                
                //Order类保存Area字段
                model.setSendArea(sendArea);
        }
        
        //webservice 向后台发送请求，订单保存到后台
        //创建后台 TODO     
            WebClient.create("http://localhost:8080/bos_management_web/webService/orderService/saveOrder")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .post(model);

       
       
    }
        return SUCCESS;
}
    }
  
