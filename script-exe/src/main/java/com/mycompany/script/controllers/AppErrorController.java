/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.controllers;

import com.mycompany.script.dto.AnswerDto;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author nova
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class AppErrorController implements ErrorController{
    private static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
    
    @RequestMapping(produces = "application/json")
    @ResponseBody
    public AnswerDto processJsonError(Throwable t, HttpServletResponse r){
        return new AnswerDto(false, ""+t);
    }
    
    @RequestMapping(produces = "text/html")
    public ModelAndView processHtmlError(Throwable t, HttpServletResponse r){
        ModelAndView m = new ModelAndView("error");
        m.addObject("message", ""+t);
        m.addObject("date", new Date());
        m.addObject("status", r.getStatus());
        return m;
    }
    
    
}
