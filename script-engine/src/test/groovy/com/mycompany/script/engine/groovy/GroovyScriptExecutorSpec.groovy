package com.mycompany.script.engine.groovy
import spock.lang.*
import com.mycompany.script.engine.groovy.GroovyScriptExecutor
import org.slf4j.LoggerFactory

@Unroll
class GroovyScriptExecutorSpec extends Specification {
    def "execScriptWithError"() {
        setup:
        def executor = new GroovyScriptExecutor()
        when:
        def res = executor.execScript(
"ErrorScrpipt.groovy", 
"src/test/groovy", LoggerFactory.getLogger("logger"), ["n":"v"])
        print "res=$res"
        then:
        
        res!=null
        and:
        res.exception != null
        
    }
    
    def "execScript"() {
        setup:
        def executor = new GroovyScriptExecutor()
        when:
        def res = executor.execScript(
"SimpleGroovyScript.groovy", 
"src/test/groovy", LoggerFactory.getLogger("logger"), ["n":"v"])
        print "res=$res"
        then:
        
        res!=null
        and:
        res.result == "hello"
        
    }
    
}
