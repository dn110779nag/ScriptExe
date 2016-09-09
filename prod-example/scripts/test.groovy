import lib.*
import com.mycompany.script.dto.*

def d = new AnswerDto(true,"Hello");

logger.info("test ok"+d)

logger.info(new Util().sayHello())

logger.info(conf.obj.test.string)

logger.info(new sample.SampleService().getSample())