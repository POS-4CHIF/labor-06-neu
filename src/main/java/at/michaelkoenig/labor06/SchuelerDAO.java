/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.michaelkoenig.labor06;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 20160451
 */
public class SchuelerDAO implements ISchuelerDAO {

    private Connection con;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SchuelerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void setup() throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate("DROP TABLE IF EXISTS schueler; "
                + "CREATE TABLE schueler ( "
                + "vorname varchar(100), "
                + "nachname varchar(100), "
                + "geschlecht char, "
                + "katalognummer int, "
                + "klasse char(5), "
                + "constraint pk primary key(katalognummer, klasse)"
                + ");");
    }

    @Override
    public int persistSchueler(List<Schueler> lst) throws SQLException {
        int count = 0;
        PreparedStatement pst = con.prepareStatement("INSERT INTO schueler"
                + "(vorname, nachname, geschlecht, katalognummer, klasse) VALUES (?,?,?,?,?);");

        try {
            con.setAutoCommit(false);
            for (Schueler schueler : lst) {
                if (schuelerDoesExist(schueler) == 0) {
                    pst.setString(1, schueler.getVorname());
                    pst.setString(2, schueler.getNachname());
                    pst.setString(3, Character.toString(schueler.getGeschlecht()));
                    pst.setInt(4, schueler.getKatalognummer());
                    pst.setString(5, schueler.getKlasse());
                    pst.executeUpdate();
                    pst.clearParameters();
                    count++;
                }
            }
            con.commit();

        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
        return count;
    }

    public int schuelerDoesExist(Schueler schueler) throws SQLException {
        PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM schueler WHERE katalognummer=? AND klasse=?");
        pst.setInt(1, schueler.getKatalognummer());
        pst.setString(2, schueler.getKlasse());
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public int deleteAll() throws SQLException {
        Statement st = con.createStatement();
        return st.executeUpdate("DELETE FROM schueler;");
    }

    @Override
    public List<Schueler> schuelerInKlasse(String klasse) throws SQLException {
        List<Schueler> lst = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement("SELECT vorname, nachname, geschlecht, katalognummer, klasse FROM schueler "
                + "WHERE klasse = ? ORDER BY katalognummer;");
        pst.setString(1, klasse);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            lst.add(new Schueler(rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), rs.getInt(4), rs.getString(5)));
        }

        return lst;
    }

    @Override
    public List<Schueler> schuelerNachGeschlecht(char geschlecht) throws SQLException {
        List<Schueler> lst = new ArrayList<>();
        PreparedStatement pst = con.prepareStatement("SELECT vorname, nachname, geschlecht, katalognummer, klasse FROM schueler "
                + " WHERE geschlecht = ? ORDER BY klasse, katalognummer;");
        pst.setString(1, Character.toString(geschlecht));
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            lst.add(new Schueler(rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), rs.getInt(4), rs.getString(5)));
        }

        return lst;
    }

    @Override
    public Map<String, Integer> getKlassen() throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT klasse, COUNT(*) FROM schueler GROUP BY klasse;");
        Map<String, Integer> map = new TreeMap<>();
        while (rs.next()) {
            map.put(rs.getString(1), rs.getInt(2));
        }

        return map;
    }

    @Override
    public void close() throws SQLException {
        if (con != null) {
            con.close();
            con = null;
        }
    }

}
