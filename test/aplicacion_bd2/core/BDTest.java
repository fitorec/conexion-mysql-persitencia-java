/*
 * @Author: fitorec

 */

package aplicacion_bd2.core;

import Core.BD;
import java.sql.ResultSet;
import org.junit.AfterClass;
import org.junit.Test;
//import static org.junit.Assert.*;

/**
 *
 * @author fitorec
 */
public class BDTest {

    @AfterClass
    public void tearDown() {
        BD.finalizar();
    }

    /**
     * Probando el metodo consulta.
     */
    @Test
    public void consultaTest() {
        ResultSet result = BD.consulta("SELECT * FROM alumnos WHERE 1");
    }
    
}
