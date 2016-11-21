package edu.kvcc.cis298.criminalintent.database;

/**
 * Created by amahler4096 on 11/9/2016.  This class holds the layout for the database.
 */
public class CrimeDbSchema {

        // This inner class will define a constant for the name of the table:
    public static final class CrimeTable {
        public static final String NAME = "crimes";

            // This inner class defines constants for the column names:
            public static final class Cols {
                public static final String UUID = "uuid";
                public static final String TITLE = "title";
                public static final String DATE = "date";
                public static final String SOLVED = "solved";
            }
    }
}
