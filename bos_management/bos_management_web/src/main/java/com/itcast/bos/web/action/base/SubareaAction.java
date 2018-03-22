package com.itcast.bos.web.action.base;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.apache.struts2.convention.annotation.Result;

import com.itcast.bos.domain.base.Standard;
import com.itcast.bos.domain.base.SubArea;
import com.itcast.bos.service.SubareaService;
import com.itcast.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午5:15:08 <br/>       
 */
@Controller
@Namespace("/") 
@ParentPackage("struts-default")
@Scope("prototype") 
public class SubareaAction extends CommonAction<SubArea> {

    public SubareaAction() {
        super(SubArea.class);  
    }

    @Autowired
    private SubareaService subareaService;
    
    @Action(value = "subareaAction_save", results = {
            @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect")})
    public String subareaAction_save() {
        subareaService.save(getModel());
        return SUCCESS;
    }
    
    @Action(value="subareaAction_pageQuery")
    public String SubareaAction_pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<SubArea> page = subareaService.findAll(pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        
        page2json(page, jsonConfig);
        
        return NONE;
    }
}
  
