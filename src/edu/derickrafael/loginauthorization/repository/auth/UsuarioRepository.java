package edu.derickrafael.loginauthorization.repository.auth;

import edu.derickrafael.loginauthorization.config.ConnectionDB;
import edu.derickrafael.loginauthorization.model.auth.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {

    public Usuarios findByEmail(String email) {
        String sql = "SELECT id_usuario, id_docente, contrasena_hash, email FROM usuarios WHERE email = ?";

        try (Connection conn =  ConnectionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuarios usuario = new Usuarios();
                    usuario.setIdUsuario(rs.getString("id_usuario"));
                    usuario.setUsername(rs.getString("id_docentes"));
                    usuario.setPassword(rs.getString("contrasena_hash"));
                    usuario.setEmail(rs.getString("email"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por email: " + e.getMessage());
        }
        return null;
    }

    public String findUserByEmail(String email) {
        boolean result = false;
        String sql = "select contrasena_hash from Usuarios where email = ?";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("contrasena_hash");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public boolean registerUser(String idUser, String username, String email, String password) {
        boolean result = false;
        String sql = "insert into usuarios values (uuid(), ?, ?, ?)";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, username);
            pstm.setString(2, email);
            pstm.setString(3, password);

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                result = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
