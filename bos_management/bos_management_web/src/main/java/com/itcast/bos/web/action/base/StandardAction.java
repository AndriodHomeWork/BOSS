package com.itcast.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.itcast.bos.domain.base.Standard;
import com.itcast.bos.service.StandardService;
import com.itcast.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:standardAction <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午3:17:59 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends CommonAction<Standard> {

    public StandardAction() {
        super(Standard.class);
    }

    @Autowired
    private StandardService standardService;

    @Action(value = "standardAction_save", results = {
            @Result(name = "success", location = "/pages/base/standard.html", type = "redirect")})
    public String save() {
        standardService.save(getModel());// TODO

        return SUCCESS;
    }

    // AJAX请求不需要跳转页面
    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws IOException {
        // EasyUI的页码是从1开始的
        // SPringDataJPA的页码是从0开始的
        // 所以要-1

        Pageable pageable = new PageRequest(page - 1, rows);// TODO
        Page<Standard> page = standardService.findAll(pageable);

        page2json(page, null);

        return NONE;
    }

    // 查找所有,为了快递员的下拉框选项
    @Action(value = "standard_findAll")
    public String findAll() throws IOException {
        // 查询数据
        Page<Standard> page = standardService.findAll(null);
        // 获取页面的数据
        List<Standard> list = page.getContent();
        // 转换数据为json并传回页面

        /*
         * String json = JSONArray.fromObject(list).toString(); HttpServletResponse response =
         * ServletActionContext.getResponse();
         * response.setContentType("application/json;charset=UTF-8");
         * response.getWriter().write(json);
         */
        list2json(list, null);
        return NONE;
    }

}
