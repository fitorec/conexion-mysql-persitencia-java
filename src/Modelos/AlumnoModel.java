/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelos;

import Core.BD;
import Core.Model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fitorec
 */
public class AlumnoModel extends Model {
    
    /** El nombre . */
    private String nombre = "";
    
    /** El email . */
    private String email = "";
    
    public AlumnoModel() {
        super("alumnos");
    }
 
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AlumnoModel(int id) {
        super("alumnos");
        this.id = id;
        this.cargar();
    }
 
    /* Se encarga de cargar el contenido del Alumno con determinado ID
     *
     * @see models.ModelInterface#load()
     */
    public Boolean cargar() {
        try {
            ResultSet rs = this.read(this.getId());
            if (rs != null && rs.first()) {
              this.nombre(rs.getString("nombre"));
              this.email(rs.getString("email"));
              return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            this.appendError("Error en ItemPedidoModel.load()\n"+this);
            return false;
        }
    }
    
    //Actualiza un registro
    /**
     * Actualizar.
     *
     * @param alumno the alumno
     * @return the boolean
     */
    public Boolean actualizar(AlumnoModel alumno) {
        Boolean result = false;
        try {
            String sql =
                    "UPDATE " + AlumnoModel.super.tabla_nombre + " SET nombre = ?, email = ?"
                    + " WHERE " + AlumnoModel.super.tabla_nombre + ".id = ?";
            PreparedStatement preparedStmt = BD.conexion().prepareStatement(sql);
            //Agregando datos
            preparedStmt.setString(1, alumno.nombre());
            preparedStmt.setString(2, alumno.email());
            //Agregando el ID(where)
            preparedStmt.setInt(7, alumno.getId());
            preparedStmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            this.appendError("Error en ItemPedidoModel.actualizar("+alumno.getId()+")");
        }
        return result;
    }
    
    /**
     * Nuevo.
     *
     * @param alumno El alumno en cuestión
     * @return boolean, true en caso que se pudo guardar con exito
     */
    public static Boolean nuevo(AlumnoModel alumno) {
        Boolean result = false;
        try {
            String sql = "INSERT INTO alumnos" +
                " (`nombre`, `email`) " +
                " VALUES (?, ?);";

            //Agregando Datos
            PreparedStatement preparedStmt = BD.conexion().prepareStatement(sql);
            //Agregando datos
            preparedStmt.setString(1, alumno.nombre());
            preparedStmt.setString(2, alumno.email());

            preparedStmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String nombre() {
        return this.nombre;
    }
    public String nombre(String nombreNuevo) {
        this.nombre = nombreNuevo;
        return this.nombre();
    }
    
    public String email() {
        return this.email;
    }
    public String email(String emailNuevo) {
        this.email = emailNuevo;
        return this.email();
    }
}
