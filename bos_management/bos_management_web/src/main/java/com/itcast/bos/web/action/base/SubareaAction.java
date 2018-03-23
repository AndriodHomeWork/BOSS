package com.itcast.bos.web.action.base;

import java.io.IOException;
import java.util.List;

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
    
    //查询未关联的分区
    @Action(value ="subAreaAction_findUnAssociatedSubAreas")
    public String findUnAssociatedSubAreas() throws IOException {
        //方法一
        //方法二：查找分区的定区属性为空的，分区集合
        List<SubArea> list = subareaService.findUnAssociatedSubAreas();
        JsonConfig jsonConfig = new JsonConfig();
        
        //这里不用忽略{couriers}的原因：因为这是未关联定区的分区，定区为NULL，那么根本就走不到fixed_area里面属性couriers
        jsonConfig.setExcludes(new String[]{"subareas"});
        list2json(list, jsonConfig);
        
        return NONE;
    }
    
    //属性驱动获得fixedAreaId
    private Long fixedAreaId;
    
    public void setFixedAreaId(Long fixedAreaId) {
        this.fixedAreaId = fixedAreaId;
    }
    
    //查询已经关联的分区
    @Action(value = "subAreaAction_findAssociatedSubAreas")
    public String findAssociatedSubAreas() throws IOException {
        List<SubArea> list = subareaService.findAssociatedSubAreas(fixedAreaId);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        list2json(list, jsonConfig);
        
       
       
        return NONE;
    }
}
  
