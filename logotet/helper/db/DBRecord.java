package rs.logotet.helper.db;


import rs.logotet.helper.DBRecordException;


/**
 *
 */
public abstract class DBRecord implements java.io.Serializable{
    protected DBClass dbClass;
    protected boolean inDB;



       /**
     * Proverava da li DBecord ima svoj zapis u bazi ili ne, tj. da li je nastao nakon dobijanja zapisa iz baze
     * ili je nastao nekon new(), a u bazi ga jos uvek nema
     */
    public boolean isInDB() {
        return inDB;
    }


    /**
     * Postavlja true ili false da bi oznacilo da li DBRecord ima svoj zapis u bazi ili ne,
     * tj. da li je nastao nakon dobijanja zapisa iz baze
     * ili je nastao nekon new(), a u bazi ga jos uvek nema
     */
    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    public abstract String getId();


    /**
     * pronalazi odgovarajucu DBClass koja komunicira sa bazom i cita, pisae. ...odgovrajuci Entitet u bazu, iz baze
     */
    public abstract DBClass getDBClass();

    public void makePersistent() throws DBRecordException {
        if (dbClass == null)
            dbClass = getDBClass();
        if (inDB)
            dbClass.update(this);
        else
            dbClass.insert(this);
    }

    public void delete() {
        if (dbClass == null)
            dbClass = getDBClass();
        try {
            if(inDB)
                dbClass.delete(this);
        } catch (DBRecordException e) {
        }
    }
}
