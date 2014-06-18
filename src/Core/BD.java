/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.sql.*;

/**
 *
 * @author fitorec
 */
public class BD {
    private static String login;
    private static final String loginDefault = "root";
    private static String password = null;
    private static final String passwordDefault = "";
    private static String baseNombre   = null;
    private static final String baseBDDefault = "clase_bd_2";
    private static String host     = null;
    private static final String hostDefault = "localhost";
    private static Connection conexion = null;
    private static String url;

    /**
     * Devuelve la variable de configuracion con el nombre campo 
     * 
     * @param campo
     * @return El valor del campo en caso de ser null devuelve un valor por defecto
     */
    public static String config(String campo) {
        if (campo.equalsIgnoreCase("login")) {
            if(BD.login == null) {
                return BD.loginDefault;
            }
            return BD.login;
        }
        if (campo.equalsIgnoreCase("password")) {
            if(BD.password == null) {
                return BD.passwordDefault;
            }
            return BD.password;
        }
        if (campo.equalsIgnoreCase("baseBD")) {
            if(BD.baseNombre == null) {
                return BD.baseBDDefault;
            }
            return BD.baseNombre;
        }
        if (campo.equalsIgnoreCase("host")) {
            if(BD.host == null) {
                return BD.hostDefault;
            }
            return BD.host;
        }
        return campo;
    }
 
    /**
     * Setea una variable de configuracion
     * 
     * @param campo el campo de configuracion
     * @param valor el valor a setearle
     * @return el valor final de campo
     */
    public static String config(String campo, String valor) {
        if (campo.equals("login") && BD.login == null) {
            BD.login = valor;
            return valor;
        }
        if (campo.equals("password") && BD.password == null) {
            BD.password = valor;
            return BD.password;
        }
        if (campo.equalsIgnoreCase("baseBD") && BD.baseNombre == null) {
            BD.baseNombre = valor;
            return BD.baseNombre;
        }
        if (campo.equalsIgnoreCase("host") && BD.host == null) {
            BD.host = valor;
            return BD.host;
        }
        System.out.println("Campo incorrecto de configuración : " + campo);
        return campo;
    }

    /**
     * Se conecta a la base de datos con la configuración previa
     * 
     * @return BD.conexion;
     */
    public static Connection conexion() {
        if(BD.conexion == null) {
            BD.url = "jdbc:mysql://" +
                BD.config("host") +
                "/" + BD.config("baseBD");
            try {
                DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
                BD.conexion = DriverManager.getConnection(BD.url,"root", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return BD.conexion;
    }

    /**
     * Ejecuta una consulta y devuelve el resultado
     * 
     * @param strQuery
     * @return ResultSet result, el resultado de la consulta realizada.
     */
    public static ResultSet consulta(String strQuery) {
        Connection c = BD.conexion();
        ResultSet result = null;
        if(strQuery == null) {
            return null;
        }
        String sql = strQuery.trim();
        if(c != null) {
            try {
                Statement st = BD.conexion.createStatement();
                if (BD.laConsultaTieneResultados(sql)) {
                    result = st.executeQuery(sql);
                } else {
                    st.execute(sql);
                }
            } catch (Exception e ) {
                System.out.println("Error en procesar la consulta "+ sql);
                e.printStackTrace();
            }
        }
        return result;
    }
 
    /**
     * Devuelve true si el tipo de consulta SQL debe de devolver algo.
     * 
     * @param strQuery
     * @return 
     */
    private static boolean laConsultaTieneResultados(String strQuery) {
        String querysTypeUpdate[] = {"UPDATE", "INSERT"};
        String sqlUpper = strQuery.toUpperCase();
        for (int i = 0; i < querysTypeUpdate.length; i++) {
            String currentSqlType = querysTypeUpdate[i];
            if(sqlUpper.indexOf(currentSqlType) == 0) {
                return false;
            }
        }
        return true;
    }
 
    public static void finalizar() {
    	if(BD.conexion != null ) {
	        try {
	            BD.conexion.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    	}
    }

    public void finalize() {
    	BD.finalizar();
    }

    /**
     * Regresa un String que nos muestra el objeto BD
     * y el estado de la conexión que maneja
     */
    public static String status() {
        String out = 
            "Login    : " + BD.config("login") +  "\n" +
            "Password : " + BD.config("password") +  "\n" +
            "host     : " + BD.config("host") +  "\n" +
            "Base     : " + BD.config("baseBD") +  "\n\n";
        if(BD.conexion != null ) {
            out += "Conexión abierta, url: \n" +  BD.url;
        } else {
             out += "Conexión cerrada";
        }
        return out;
    }

    /**
     * devuelve  la configuacion actual, asi como el edo de la conexión.
     * 
     * @return la descripción del Objeto BD
     */
    public String toString() {
        return BD.status();
    }
}