package com.itcast.bos.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.bos.dao.AreaRepository;
import com.itcast.bos.dao.FixedAreaRepository;
import com.itcast.bos.dao.OrderRepository;
import com.itcast.bos.dao.WorkbillRepository;
import com.itcast.bos.domain.base.Area;
import com.itcast.bos.domain.base.Courier;
import com.itcast.bos.domain.base.FixedArea;
import com.itcast.bos.domain.base.SubArea;
import com.itcast.bos.domain.take_delivery.Order;
import com.itcast.bos.domain.take_delivery.WorkBill;
import com.itcast.bos.service.OrderService;

/**  
 * ClassName:OrderServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 上午10:47:05 <br/>    
 * 根据发件地址完全匹配(发件地址数据来自CRM系统中customer表,而且这个客户一定要和某一个定区关联)
 * 根据发件地址模糊匹配(发件地址数据来自后台系统中subArea表,而且这个分区一定要和某一个定区关联,这个分区所属的区域数据一定要对得上号)   
 */


/*--通过省市区查找area数据库，找到area的id，找到的新的area有id，为持久态
对象area。
--重新设置进去order中*/
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository  OrderRepository;

    @Autowired
    private AreaRepository areaRepository;
    
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Autowired
    private WorkbillRepository workbillRepository;
    
    
    @Override
    //保存订单
    public void saveOrder(Order order) {
        // 把瞬时态的Area转换为持久态的Area
        Area recArea = order.getRecArea();
        if(recArea != null) {
            //持久化对象
            Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict( recArea.getProvince(), recArea.getCity(),recArea.getDistrict());
            order.setRecArea(recAreaDB);
        }
        
        
        Area sendArea = order.getSendArea();
        if(sendArea != null) {
                //持久化对象
                Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
                order.setSendArea(sendAreaDB);
        }
        
        //保存订单
        order.setOrderNum(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setOrderTime(new Date());
        OrderRepository.save(order);
        
        
        // 自动分单
        //客户表详细地址-查找定区id-查定区-快递员表--时间--当班快递员-收派标准
        
      String sendAddress = order.getSendAddress();
      if (StringUtils.isNotEmpty(sendAddress)) {
          // ---根据发件地址完全匹配
          // 让crm系统根据发件地址查询定区ID
          // 做下面的测试之前,必须在定区中关联一个客户,下单的页面填写的地址,必须和这个客户的地址一致
          String fixedAreaID = WebClient.create("http://localhost:8180/crm/webService/customerService/findFixedAreaIdByAdddress")
                  .type(MediaType.APPLICATION_JSON)
                  .query("address", sendAddress)
                  .accept(MediaType.APPLICATION_JSON).get(String.class);
              
                  
         // 根据定区ID查询定区
          if(StringUtils.isNotEmpty(fixedAreaID) ) {
              FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaID));
              //查快递员
              if(fixedArea != null) {
                 Set<Courier> couriers = fixedArea.getCouriers();
                     if(!couriers.isEmpty()) {
                         Iterator<Courier> iterator = couriers.iterator();
                         Courier courier = iterator.next();
                         // 指派快递员
                         order.setCourier(courier);
                         // 生成工单
                         WorkBill workBill = new WorkBill();
                         workBill.setAttachbilltimes(0);
                         workBill.setBuildtime(new Date());
                         workBill.setCourier(courier);
                         workBill.setOrder(order);
                         workBill.setPickstate("新单");
                         workBill.setRemark(order.getRemark());
                         workBill.setSmsNumber("111");
                         workBill.setType("新");
                         
                         workbillRepository.save(workBill);
                         // 发送短信,推送一个通知
                         // 中断代码的执行
                         order.setOrderType("自动分单");
                         return;
                     }
                 
              }
          }else {
              // 定区关联分区,在页面上填写的发件地址,必须是对应的分区的关键字或者辅助关键字
              //客户表address不完整--根据省市区---区域-分区维护关键字段-定区-快递员表--时间--当班快递员-收派标准
              Area sendArea2 = order.getSendArea();
              if(sendArea2 != null) {
                      //遍历这个区域的每一个分区维护的关键字
                  Set<SubArea> subareas = sendArea2.getSubareas();
                  for (SubArea subArea : subareas) {
                    String keyWords = subArea.getKeyWords();
                    String assistKeyWords = subArea.getAssistKeyWords();
                    
                    if(sendAddress.contains(keyWords)||sendAddress.contains(assistKeyWords)) {
                            //分区查到定区
                        FixedArea fixedArea2 = subArea.getFixedArea();
                        //查询快递员
                        if(fixedArea2 != null) {
                            Set<Courier> couriers = fixedArea2.getCouriers();
                            if(!couriers.isEmpty()) {
                                Iterator<Courier> iterator = couriers.iterator();
                                Courier courier = iterator.next();
                                // 指派快递员
                                order.setCourier(courier);
                                // 生成工单
                                WorkBill workBill = new WorkBill();
                                workBill.setAttachbilltimes(0);
                                workBill.setBuildtime(new Date());
                                workBill.setCourier(courier);
                                workBill.setOrder(order);
                                workBill.setPickstate("新单");
                                workBill.setRemark(order.getRemark());
                                workBill.setSmsNumber("111");
                                workBill.setType("新");
                                
                                workbillRepository.save(workBill);
                                // 发送短信,推送一个通知
                                // 中断代码的执行
                                order.setOrderType("自动分单");
                                return;
                            }
                        }
                    }
                }
              }
          }
                  
      }
    
        
      // ---根据发件地址模糊匹配
      order.setOrderType("人工分单");
        
    }
    
}
  
