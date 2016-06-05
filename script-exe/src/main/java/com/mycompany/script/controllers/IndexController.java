/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.controllers;

import groovy.lang.GroovySystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author user
 */
@Controller
public class IndexController {
    @RequestMapping({"/"})
    public ModelAndView returnIndex(){
        ModelAndView m = new ModelAndView("index", "groovyVersion", GroovySystem.getVersion());
        m.addObject("bootVersion", "???");
        return m;
    }
}
