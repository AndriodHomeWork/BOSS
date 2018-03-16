package com.itcast.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.apache.struts2.ServletActionContext;
import org.apache.tomcat.dbcp.pool.impl.GenericKeyedObjectPool.Config;
import org.springframework.data.domain.Page;

import com.itcast.bos.domain.base.Standard;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommonAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午3:00:53 <br/>       
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T>{

    private T model ;
    
    private Class<T> clazz;
    
    public CommonAction(Class<T> clazz) {
        this.clazz = clazz;

        try {
            model = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T getModel() {
          
        return model;
    }
    
  //使用属性驱动获得数据
    protected int page;//第几页
    protected int rows;//每页显示几条数据
    
    public void setPage(int page) {
        this.page = page;
    }

   public void setRows(int rows) {
       this.rows = rows;
   }
   
   
   /**
    * 把page对象转为json数据
    * 
    * @param page
    *            : Page对象
    * @param jsonConfig
    *            : 转换json的设置
 * @throws IOException 
    */
   
   public void page2json(Page<T> page,JsonConfig jsonConfig) throws IOException {
       // 总数据条数
       long total = page.getTotalElements();
       // 当前页要实现的内容
       List<T> list = page.getContent();
       
       // 封装数据
       Map<String, Object> map = new HashMap<>();
       
       map.put("total", total);
       map.put("rows", list);
       
       // JSONObject : 封装对象或map集合
       // JSONArray : 数组,list集合
       // 把对象转化为json字符串
       String json ;
       
       if(jsonConfig == null) {
           json = JSONObject.fromObject(map).toString();
       }else{
           json = JSONObject.fromObject(map, jsonConfig).toString();
       }
       
       
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("application/json;charset=UTF-8");
       response.getWriter().write(json);
   }
   
   
   /**
    * 把list集合转为json字符串
    * 
    * @param list
    *            : 集合
    * @param jsonConfig
    *            : 转换json的设置
 * @throws IOException 
    */
   
   public void list2json(List<T> list,JsonConfig jsonConfig) throws IOException {
       String json;
       if(jsonConfig != null) {
           json = JSONArray.fromObject(list, jsonConfig).toString();
       }else {
           json = JSONArray.fromObject(list).toString();
       }
       
       
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("application/json;charset=UTF-8");
       response.getWriter().write(json);
   }
  

}
  
