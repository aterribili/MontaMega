package br.com.android.mmega.extras;

public abstract class Extras {
	//
	public static final String EXPRESSION = "-";
	public static final String FILENAME = "statistics.txt";
	public static final String DATABASE_NAME = "megaManager";
	public static final String TABLE_MEGA = "table_mega";
	public static final int DATABASE_VERSION = 1;

	//
	public static final String SELECT_MIN = "SELECT MIN(id) AS id FROM ";
	public static final String SELECT = "SELECT id, lastResult, mega, date FROM ";
	public static final String SELECT_ALL = "SELECT * FROM ";
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

	//
	public static final String ID = "id=";
	public static final String ID_ = "id";
	public static final String LAST_RESULT = "lastResult";
	public static final String MEGA = "mega";
	public static final String DATE = "date";

	//
	public static final String ZERO = "0";
	public static final String LINE = " - ";
	public static final String DATE_FORMAT = "d, MMM";
	public static final String INTENT = "text/plain";

}
