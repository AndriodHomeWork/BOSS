package com.itcast.bos.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itcast.bos.dao.StandardRepository;
import com.itcast.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午8:08:38 <br/>       
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {

    @Autowired
    private StandardRepository standardRepository;
    @Test
    public void test() {
       List<Standard> list = standardRepository.findAll();
       for (Standard standard : list) {
        System.out.println(standard);
    }
    }
    
    
    @Test
    public void testAdd(){
        Standard s = new Standard();
        s.setName("李四");
        s.setMaxLength(100);
        standardRepository.save(s);
    }
    
    @Test
    public void testUpdate() {
        Standard s = new Standard();
        
       // 进行更改操作,必须传入主键
        s.setId(1L);
        s.setName("三张");
        s.setMaxLength(400);
        standardRepository.save(s);
    }
    
    @Test
    public void testFindone() {
        Standard standard = standardRepository.findOne(1L);
        System.out.println(standard);
    }
    
    @Test
    public void testDelete() {
        standardRepository.delete(1L);
    }
    
    @Test
    public void testFindByName() {
        List<Standard> list = standardRepository.findByName("张三");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void testFindByNameAndMaxLength() {
        List<Standard> list = standardRepository.findByNameAndMaxLength("张三",100);
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void testFindByNameAndMaxLength2() {
        List<Standard> list = standardRepository.findByNameAndMaxLength(100,"张三");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test//TODO
    public void testDel() {
        standardRepository.deleteByName("张三");
    }
    
    @Test
    public void testUpdate2() {
        standardRepository.updateMaxLengthByName(888,"张三");
    }
    
    
   

}
  
