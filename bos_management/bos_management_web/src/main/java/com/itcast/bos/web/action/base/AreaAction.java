package com.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
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
import com.itcast.utils.FileDownloadUtils;
import com.itcast.utils.PinYin4jUtils;

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

    private String q;

    public void setQ(String q) {
        this.q = q;
    }
    
    @Action(value = "areaAction_findAll")
    public String findAll() throws IOException {
        List<Area> list;
        
        if(StringUtils.isNotEmpty(q)) {
            // 根据用户输入的条件进行模糊匹配
            list = areaService.findQ(q);
        }else {
            //查询所有
            Page<Area> page=areaService.findAll(null);
            list=page.getContent();
        }
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        list2json(list, jsonConfig);
        return null;
        
    }
    
    // 加载文件
    // 读取sheet
    // 读取行
    // 读取列

    // 创建文件
    // 创建sheet
    // 创建行
    // 创建列
    // 写入数据
    @Action(value = "areaAction_exportExcel")
    public String exportExcel() throws IOException {
        //查询出所有的数据
        Page<Area> page = areaService.findAll(null);
        List<Area> list = page.getContent();
        
        
     // 在内存中创建了一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
     // 创建sheet
        HSSFSheet sheet = workbook.createSheet();
     // 创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("省");
        titleRow.createCell(1).setCellValue("市");
        titleRow.createCell(2).setCellValue("区");
        titleRow.createCell(3).setCellValue("邮编");
        titleRow.createCell(4).setCellValue("简码");
        titleRow.createCell(5).setCellValue("城市编码");
      //遍历数据，创建数据行
        for (Area area : list) {
         // 获取最后一行的行号
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow dataRow = sheet.createRow(lastRowNum+1);
            dataRow.createCell(0).setCellValue(area.getProvince());
            dataRow.createCell(1).setCellValue(area.getCity());
            dataRow.createCell(2).setCellValue(area.getDistrict());
            dataRow.createCell(3).setCellValue(area.getPostcode());
            dataRow.createCell(4).setCellValue(area.getShortcode());
            dataRow.createCell(5).setCellValue(area.getCitycode());
           }
        
        
       /*文件输入输出：一流两头
                    流：输入输出流
                    信息头：写出的格式：response.setContentType();//文件类型 .xls
                       response.setHearder("","") //;*/
        //文件名
        String filename = "区域数据统计.xls";
        
        // 一个流两个头
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        
        ServletContext servletContext = ServletActionContext.getServletContext();
        
        ServletOutputStream outputStream = response.getOutputStream();
      
        
        // 获取mimeType
        // 先获取mimeType再重新编码,避免编码后后缀名丢失,导致获取失败
        String mimeType = servletContext.getMimeType(filename);
        // 获取浏览器的类型
        String userAgent = request.getHeader("User-Agent");
        // 对文件名重新编码
        filename =FileDownloadUtils.encodeDownloadFilename(filename, userAgent);
        
        // 设置信息头
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition","attachment; filename=" + filename);
        
        // 写出文件
        workbook.write(outputStream);
        workbook.close();
        return NONE;
    }
}
  
