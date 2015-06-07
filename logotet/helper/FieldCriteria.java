package rs.logotet.helper;


import rs.logotet.util.BJDatum;

import java.util.Date;

/**
 * kreirano:
 * Date: Oct 14, 2005
 * Time: 10:17:33 AM
 * Klasa koja predtsvlja kriterijum za pojedinacno polje. Ima najnizu i navisu vrednost, ili samo
 * jedno od ta dva;
 */
public class FieldCriteria {
    public static final int INT = 0;
    public static final int DOUBLE = 1;
    public static final int STRING = 2;
    public static final int DATE = 3;
    public static final int BOOLEAN = 4;
    public static final int UNKNOWN = -1;

    public static final int LESS_THEN = 0;
    public static final int GREATER_THEN = 1;
    public static final int IS_EQUAL = 2;
    public static final int NOT_LESS = 3;
    public static final int NOT_GREATER = 4;
    public static final int NOT_EQUAL = 5;
    public static final int LIKE = 6;
    public static final int NOT_LIKE = 7;
    public static final int IN = 8;
    public static final int NOT_IN = 9;

    public final static String[] oper = {"   < ", "   > ", "   = ", "   >= ", "   <= ", "   != ", "LIKE", "NOT LIKE", "IN", "NOT IN"};

    private int fieldType;

    private String tekst;
    private String dbFieldName;
    private String value;
    private int operater;

    public FieldCriteria(String tekst, String dbFieldName, int operater, String value) {
        this.tekst = tekst;
        this.dbFieldName = dbFieldName;
        this.operater = operater;
        setValue(value);
    }

    public FieldCriteria(String tekst, String dbFieldName, int operater, int value) {
        this(tekst, dbFieldName, operater, "" + value);
        setValue(value);
    }

    public FieldCriteria(String tekst, String dbFieldName, int operater, double value) {
        this(tekst, dbFieldName, operater, "" + value);
        setValue(value);
    }

    public FieldCriteria(String tekst, String dbFieldName, int operater, Date value) {
        this(tekst, dbFieldName, operater, "");
        setValue(value);
    }

    public FieldCriteria(String tekst, String dbFieldName, int operater, String[] value) {
        this.tekst = tekst;
        this.dbFieldName = dbFieldName;
        this.operater = operater;
        setValue(value);
    }

    public FieldCriteria(String tekst, String dbFieldName, int operater, int[] value) {
        this(tekst, dbFieldName, operater, "" + value);
        setValue(value);
    }

    public FieldCriteria(String tekst, String dbFieldName, int operater, double[] value) {
        this(tekst, dbFieldName, operater, "" + value);
        setValue(value);
    }


    public String getSQLuslov() {
        StringBuffer sb = new StringBuffer();
        if ((operater == IN) || (operater == NOT_IN))
            if(value.trim().length() == 0)
            return " 1 = 1 ";


        sb.append(dbFieldName + " " + oper[operater] + " ");
        if ((operater == IN) || (operater == NOT_IN))
             sb.append("(");
        if (fieldType == DATE)
            sb.append("DATE('");
        if ((fieldType == STRING))
            sb.append("'");

        if (fieldType == STRING) {
            if ((operater == LIKE) || (operater == NOT_LIKE))
                sb.append("%");
        }

        sb.append(value);

        if (fieldType == STRING) {
            if ((operater == LIKE) || (operater == NOT_LIKE))
                sb.append("%");
        }
        if ((fieldType == STRING))
            sb.append("'");
        if (fieldType == DATE)
            sb.append("')");

        if ((operater == IN) || (operater == NOT_IN))
            sb.append(")");
        return sb.toString();
    }

    public void setValue(String value) {
        this.value = value;
        fieldType = STRING;
    }

    public void setValue(int value) {
        this.value = "" + value;
        fieldType = INT;
    }

    public void setValue(double value) {
        this.value = "" + value;
        fieldType = DOUBLE;
    }

    public void setValue(Date value) {

        BJDatum bjd = new BJDatum(value);
        this.value = bjd.getSQLDateString();
        fieldType = DATE;
    }


    public void setValue(String[] val) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < val.length; i++) {
            if (i != 0)
                sb.append("','");
            sb.append(val[i]);
        }
        this.value = sb.toString();
        fieldType = STRING;
    }

    public void setValue(int[] val) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < val.length; i++) {
            if (i != 0)
                sb.append(",");
            sb.append("" + val[i]);
        }
        this.value = sb.toString();
        fieldType = INT;
    }

    public void setValue(double[] val) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < val.length; i++) {
            if (i != 0)
                sb.append(",");
            sb.append(val[i]);
        }
        this.value = sb.toString();
        fieldType = DOUBLE;
    }


//    public int hashCode() {
//        try{
//            return tekst.length();
//        }catch(NullPointerException npe){
//           return 123;
//        }
//    }

    public boolean equals(FieldCriteria fc) {
        //FieldCriteria fc = (FieldCriteria) obj;
        boolean istina;
        boolean eqString;
        istina = ((fc.tekst.equals(tekst)) &&
                (fc.operater == operater) &&
                (fc.fieldType == fieldType));

        if (!istina)
            return istina;
        if ((fc.value == null) && (value != null))
            return false;
        if ((fc.value != null) && (value == null))
            return false;
        if ((fc.value == null) && (value == null))
            return false;
        try {
            eqString = fc.value.equals(value);
        } catch (NullPointerException npe) {
            eqString = true;
        }
        return (istina && eqString);
    }

    public static void main(String[] args) {
        String[] t = {"ab", "cd", "efg"};
        int[] ti = {1, 2, 3};
        FieldCriteria fc = new FieldCriteria("", "polje", FieldCriteria.IN, t);
        System.out.println(fc.getSQLuslov());
        FieldCriteria fci = new FieldCriteria("", "polje", FieldCriteria.IN, ti);
        System.out.println(fci.getSQLuslov());
        ti = new int[0];
        FieldCriteria fci1 = new FieldCriteria("", "polje", FieldCriteria.IN, ti);
        System.out.println(fci1.getSQLuslov());
    }
}


