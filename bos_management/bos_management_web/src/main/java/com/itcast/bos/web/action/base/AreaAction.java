package com.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import com.itcast.bos.domain.base.Area;
import com.itcast.bos.service.AreaService;
import com.itcast.bos.web.action.CommonAction;
import com.itcast.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:areaAction_importXLS <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:29:05 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {
    public AreaAction() {
        super(Area.class);  
    }

    // 使用属性驱动获取用户上传的文件
    private File file;
    
    public void setFile(File file) {
        this.file = file;
    }
    
    @Autowired
    private AreaService areaService;
    
    @Action(value = "areaAction_importXLS", results = {@Result(name = "success",
            location = "/pages/base/area.html", type = "redirect")})
    public String importXLS() {
        
     // 用来保存数据的集合
        List<Area> list = new ArrayList<>();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
         // 读取工作簿
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            //遍历行
           for (Row row : sheetAt) {
            //第一行是标题,不要
               if(row.getRowNum()==0) {
                   continue;
               }
            // 读取表格的数据
               String province = row.getCell(1).getStringCellValue();
               String city = row.getCell(2).getStringCellValue();
               String district  = row.getCell(3).getStringCellValue();
               String postcode = row.getCell(4).getStringCellValue();
             
            // 截掉省市区的最后一个字符
                province = province.substring(0,province.length()-1);
                city = city.substring(0, city.length()-1);
                district = district.substring(0, district.length()-1);
                
              // 获取城市编码
                String citycode = PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
              //获取简码
                String[] headByString  = PinYin4jUtils.getHeadByString(province+city+district,true);
                String shortcode = PinYin4jUtils.stringArrayToString(headByString);
                
             // 构造一个Area
                Area area = new Area();
                area.setProvince(province);
                area.setCity(city);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setShortcode(shortcode);
                area.setCitycode(citycode);
                
             // 添加到集合
                list.add(area);
        }
           
        // 一次性保存区域数据
           areaService.save(list);
           
        // 释放资源
           workbook.close();
            
        } catch (IOException e) {
              
            e.printStackTrace();  
            
        }
        
        return SUCCESS;
    }
    
    
  
     
    @Action(value="areaAction_pageQuery")
    public String pageQuery() throws IOException {
     
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Area> page = areaService.findAll(pageable);
     
   
        // 灵活控制输出的内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        
        page2json(page, jsonConfig);
        return NONE;
    }

}
  
