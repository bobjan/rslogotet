package rs.logotet.helper.parser;

import java.text.ParseException;


/**
 * kreirano:
 * Date: Aug 31, 2008
 * Time: 8:37:12 PM
 */
public interface Parser {
    void parse(String s) throws ParseException;

    void setListener(ParserListener pl);

}
