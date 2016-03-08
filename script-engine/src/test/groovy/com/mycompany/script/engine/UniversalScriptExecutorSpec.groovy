package com.mycompany.script.engine
import spock.lang.*
import com.mycompany.script.engine.*
import org.slf4j.LoggerFactory

@Unroll
class UniversalScriptExecutorSpec extends Specification {
    
    def "test extension"(){
        setup:
        UniversalScriptExecutor executor = new UniversalScriptExecutor()
        when:
        def ext = executor.getExtension("qqq.groovy");
        then:
        ext == "groovy";
    }
    
    def "test extension error"(){
        setup:
        UniversalScriptExecutor executor = new UniversalScriptExecutor()
        when:
        def ext = executor.getExtension("qqq");
        then:
        thrown IndexOutOfBoundsException
    }
    
    def "execScriptWithError"() {
        setup:
        def executor = new UniversalScriptExecutor()
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
        def executor = new UniversalScriptExecutor()
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
