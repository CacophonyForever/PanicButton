package Common;

import java.text.DecimalFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonFunctions.
 */
public final class CommonFunctions {

    /**
     * Format byte size human.
     * 
     * @param bytes
     *            the bytes
     * @return the string
     */
    public static String formatByteSizeHuman(long bytes) {
	int div = 0;
	double divsize = bytes;

	while (divsize > 1000) {
	    divsize /= 1000;
	    div++;
	}

	String suf = "";
	switch (div) {
	case 0:
	    suf = "bytes";
	    break;
	case 1:
	    suf = "kB";
	    break;
	case 2:
	    suf = "MB";
	    break;
	case 3:
	    suf = "GB";
	    break;
	case 4:
	    suf = "TB";
	    break;
	}
	DecimalFormat df = new DecimalFormat("#.##");

	return df.format(divsize) + suf;

    }

}
