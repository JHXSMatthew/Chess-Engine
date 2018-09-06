import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by JHXSMatthew on 6/9/18.
 */
public class ChessEngineInterfaceTest {

    private static ChessEngineI engine;

    @BeforeClass
    public static void init(){
        engine = new ChessEngine();
    }

    @Test
    public void initStateTest(){
        Assert.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", engine.getInitState());
    }
}
