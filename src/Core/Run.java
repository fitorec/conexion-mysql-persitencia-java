/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import Modelos.AlumnoModel;
import Modelos.CalificacionModel;


//import java.sql.ResultSet;

/**
 *
 * @author fitorec
 */
public class Run {
    public static void main(String arg[]) {
        BD.conexion();
        System.out.println(BD.status());
        AlumnoModel alumno = new AlumnoModel();
        System.out.println("Clase Alumno " + alumno);
        CalificacionModel calificacion = new CalificacionModel();
        System.out.println("Clase Calificacion " + calificacion);
        System.out.println("Clase Alumno " + alumno);
        String sql = "INSERT INTO `clase_bd_2`.`alumnos` (`id`, `nombre`, `email`) VALUES (NULL, 'nombre 2', 'email2@gmail.com');";
        //String sql = "SELECT * FROM alumnos;";
        System.out.println(BD.consulta(sql));
        
        alumno.nombre("fitorec");
        alumno.email("chanerec@gmail.com");
        if( AlumnoModel.nuevo(alumno) ) {
            System.out.println("Se guardo exitosamente el Alumno");
        } else {
            System.out.println("Error! " + alumno);
        }
        //ResultSet result = BD.consulta("SELECT * FROM alumnos WHERE 1");
    }
}
