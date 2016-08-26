/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.controllers;




import java.text.SimpleDateFormat;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author user
 */
@Controller
public class IndexController {
    
    @Autowired
    private BuildProperties buildProperties;
    
    @RequestMapping({"/"})
    public ModelAndView returnIndex(@RequestParam(value="name", required = false) String name){
        ModelAndView m = new ModelAndView("index");
        m.addObject("timestamp", buildProperties.getTime().getTime());
        m.addObject("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(buildProperties.getTime()));
        m.addObject("version", buildProperties.getVersion());
        return m;
    }
}
