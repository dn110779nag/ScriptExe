
package com.mycompany.script.engine.js.nashorn
import spock.lang.*
import org.slf4j.LoggerFactory

/**
 *
 * @author user
 */
@Unroll
class JsNashornScriptExecutorSpec  extends Specification {
	
    def "execScriptWithError"() {
        setup:
        def executor = new JsNashornScriptExecutor()
        when:
        def res = executor.execScript(
"errorExample.js", 
"src/test/javascript", LoggerFactory.getLogger("logger"), ["n":"v"])
        print "res=$res"
        then:
        
        res!=null
        and:
        res.exception != null
        
    }
    
    def "execScript by Nashorn"() {
        setup:
        def executor = new JsNashornScriptExecutor()
        when:
        def res = executor.execScript(
"example.js", 
"src/test/javascript", LoggerFactory.getLogger("logger"), ["n":"v"])
        print "res=$res"
        then:
        
        res!=null
        and:
        res.result == "hello"
        
    }
    
}
