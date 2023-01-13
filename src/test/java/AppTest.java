import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     *
     * @return
     */
    @Test
    public void createPersonListTest()
    {
        List<Person> list = App.createPersonList();
        assertNotNull(list);
    }

    @Test
    public void createFileTest()
    {
        File file = new File(App.FILE_PATH);
        assertNotNull(file);
    }

}
