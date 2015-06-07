package rs.logotet.helper;


import java.util.Enumeration;
import java.util.Vector;

/**
 * Klasa koja prikazuje ogranicenja  za pojedini db pristup
 * <p/>
 * // @todo do kraja razraditi pricu sa offset i limit
 */
public class Criteria implements Cloneable {
    private Vector ogranicenja;
    private Vector sort;
    private Vector groupby;
    private int dbOffset;
    private int dbLimit;

    public Criteria() {
        ogranicenja = new Vector();
        sort = new Vector();
        groupby = new Vector();
        dbOffset = -1;
        dbLimit = 1000;
    }

    public void addOgranicenje(FieldCriteria fc) {
        if (fc != null)
            ogranicenja.add(fc);
    }


    public void addSort(String s, boolean asc) {
        String txt = asc ? " ASC" : " DESC";
        if (s != null)
            sort.add(s + txt);
    }

    public void addSort(String s) {
        addSort(s, true);
    }

    public void addGroupBy(String s) {
        groupby.add(s);
    }

    public boolean satisfiedFor(Object obj) {
        return true;
    }

    public int getDbLimit() {
        return dbLimit;
    }

    public void setDbLimit(int dbLimit) {
        this.dbLimit = dbLimit;
    }

    public void incrementDbOffset() {
        if (dbOffset < 0)
            dbOffset = 1;
        else
            dbOffset += dbLimit;
    }

    public Vector getOgranicenja() {
        return ogranicenja;
    }

    public Vector getSort() {
        return sort;
    }

    public void addCriteria(Criteria c) {
        if (c == null)
            return;
        Enumeration enm = c.getOgranicenja().elements();
        while (enm.hasMoreElements()) {
            FieldCriteria obj = (FieldCriteria) enm.nextElement();
            ogranicenja.add(obj);
        }
        enm = c.getSort().elements();
        while (enm.hasMoreElements()) {
            String obj = (String) enm.nextElement();
            sort.add(obj);
        }
    }

    public String getSQLuslov() {
        StringBuffer sb = new StringBuffer("");
//        if (ogranicenja.size() == 0)
//            return "";
        Enumeration enm = ogranicenja.elements();
        while (enm.hasMoreElements()) {
            FieldCriteria obj = (FieldCriteria) enm.nextElement();
            sb.append(" AND " + obj.getSQLuslov());
        }

        if (groupby.size() > 0) {
            sb.append(" GROUP BY");
            Enumeration enmg = groupby.elements();
            while (enmg.hasMoreElements()) {
                String obj = (String) enmg.nextElement();
                sb.append(",  " + obj);
            }
            int zarez = sb.indexOf("GROUP BY,");
            sb.deleteCharAt(zarez + 8);
        }

        if (sort.size() > 0) {
            sb.append(" ORDER BY");
            Enumeration enms = sort.elements();
            while (enms.hasMoreElements()) {
                String obj = (String) enms.nextElement();
                sb.append(",  " + obj);
            }
            int zarez = sb.indexOf("ORDER BY,");
            sb.deleteCharAt(zarez + 8);
        }

// @todo ukloniti ovaj komentar kada bude vreme
//        if(dbOffset > 0){
//            sb.append( " LIMIT " + dbLimit + " OFFSET " + dbOffset);
//        }
        return sb.toString();
    }


    public boolean equals(Criteria crit) {
        if (crit == null)
            return false;
        Vector v1 = (Vector) ogranicenja.clone();
        Vector v2 = (Vector) crit.getOgranicenja().clone();
        Enumeration enm1;
        Enumeration enm2;
        enm1 = v1.elements();
        while (enm1.hasMoreElements()) {
            FieldCriteria fld1 = (FieldCriteria) enm1.nextElement();
            boolean found = false;
            enm2 = v2.elements();
            while (enm2.hasMoreElements()) {
                FieldCriteria fld2 = (FieldCriteria) enm2.nextElement();
                if (fld1.equals(fld2)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }

        enm2 = v2.elements();
        while (enm2.hasMoreElements()) {
            FieldCriteria fld2 = (FieldCriteria) enm2.nextElement();
            boolean found = false;
            enm1 = v1.elements();
            while (enm1.hasMoreElements()) {
                FieldCriteria fld1 = (FieldCriteria) enm1.nextElement();
                if (fld1.equals(fld2)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Criteria c1 = new Criteria();
        Criteria c2 = new Criteria();
        FieldCriteria fc1 = new FieldCriteria("ime", "ime", FieldCriteria.GREATER_THEN, "Boban");
        FieldCriteria fc2 = new FieldCriteria("prezime", "prezime", FieldCriteria.IS_EQUAL, "Jankovic");
        FieldCriteria fc3 = new FieldCriteria("broj", "broj", FieldCriteria.LESS_THEN, 10);
        //      FieldCriteria fc4 = new FieldCriteria("mesto", "mesto", FieldCriteria.LIKE, "Zagreb");

        FieldCriteria fc5 = new FieldCriteria("ime", "ime", FieldCriteria.NOT_EQUAL, "Boban");
        FieldCriteria fc6 = new FieldCriteria("prezime", "prezime", FieldCriteria.NOT_GREATER, "Jankovic");
        FieldCriteria fc7 = new FieldCriteria("broj", "broj", FieldCriteria.NOT_LESS, 10);
//        FieldCriteria fc8 = new FieldCriteria("mesto", "mesto", FieldCriteria.NOT_LIKE, new Date());

        c1.addOgranicenje(fc1);
        c1.addOgranicenje(fc2);
        c1.addOgranicenje(fc3);
//        c1.addOgranicenje(fc4);

//        c2.addOgranicenje(fc8);
        c2.addOgranicenje(fc6);
        c2.addOgranicenje(fc7);
        c2.addOgranicenje(fc5);

        c1.addSort("polje");
        c1.addSort("drugo", false);
        c1.addSort("trece", true);


        c2.addSort("polje");
        c2.addSort("drugo", false);
        c2.addSort("trece", true);

        System.out.println(c1.getSQLuslov());
        System.out.println(c2.getSQLuslov());
        if (c1.equals(c2))
            System.out.println("EQUALS!");
        else
            System.out.println("NOt EQUALS!");

    }

    public String getGroupBy() {
        if (groupby.size() == 0)
            return "";
        int brojac = 0;
        StringBuffer sb = new StringBuffer(" GROUP BY ");
        Enumeration enm = groupby.elements();
        while (enm.hasMoreElements()) {
            String obj = (String) enm.nextElement();
            if (brojac > 0)
                sb.append(", ");
            sb.append(obj);
            brojac++;
        }
        return sb.toString();
    }
}
