/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script;

import java.util.Date;
import org.junit.Test;
import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 *
 * @author nova
 */
public class ChronTest {
    
    @Test
    public void testChron(){
        System.out.println(new Date());
        System.out.println(new CronSequenceGenerator("0 */1 * * * *").next(new Date()));
    }
    
}
