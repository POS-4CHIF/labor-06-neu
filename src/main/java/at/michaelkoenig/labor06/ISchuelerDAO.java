/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.michaelkoenig.labor06;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 20160451
 */
public interface ISchuelerDAO extends AutoCloseable {

    // persisitiert in einer Transaktion alle in lst gespeicherten Schueler
    int persistSchueler(List<Schueler> lst) throws SQLException;

    // löscht alle in der DB gespeicherten Schueler
    int deleteAll() throws SQLException;

    List<Schueler> schuelerInKlasse(String klasse) throws SQLException;

    List<Schueler> schuelerNachGeschlecht(char geschlecht) throws SQLException;

    Map<String, Integer> getKlassen() throws SQLException;
}
