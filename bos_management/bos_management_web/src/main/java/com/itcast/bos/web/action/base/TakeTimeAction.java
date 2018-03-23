package com.itcast.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcast.bos.domain.base.TakeTime;
import com.itcast.bos.service.TakeTimeService;
import com.itcast.bos.web.action.CommonAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午3:12:51 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime> {

    public TakeTimeAction() {
        super(TakeTime.class);  
    }
    
    @Autowired
    private TakeTimeService takeTimeService;
    
    //获取所有的收派时间
    @Action(value = "takeTimeAction_listajax")
    public String listajax() throws IOException {
       List<TakeTime> list = takeTimeService.listajax();
       
       list2json(list, null);
       
        return NONE;
    }

}
  
