package ma.youcode.lineperm.dao;

import java.sql.Connection;

import ma.youcode.lineperm.database.DBConnection;

public abstract class AbstractDao<T> implements Dao<T> {

    protected Connection con;

    public AbstractDao(){

        this.con = DBConnection.getInstance().getConnection();
    }


}
