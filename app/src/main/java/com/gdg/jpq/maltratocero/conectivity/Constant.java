package com.gdg.jpq.maltratocero.conectivity;

import com.gdg.jpq.maltratocero.R;

/**
 * Class that contains the codes and application variables
 */
public class Constant {

    /**
     * time that the presentation is displayed (3 seconds)
     */
    public static final int DURATION_PRESENTATION = 2000;

    /**
     * IP address of the web server
     */
    private static final String IP = "http://juanpabloquiniones.com/mcweb/webservice/";

    /**
     * URLs of web service
     */
    public static final String GET_CENTERS_BY_LAT_LNG = IP + "ws_get_centers_latlng.php";
    public static final String GET_CENTERS_BY_PARTY = IP + "ws_get_centers_party.php";
    public static final String GET_CENTER_BY_ID = IP + "ws_get_center.php";
    public static final String SEND_ALERT = IP + "ws_send_alert.php";
    public static final String SEND_MAIL = IP + "ws_send_mail.php";

    public static final String EMAIL_ALERT = "jpq.1987@gmail.com";
    public static final String PHONE_ALERT = "2216081502";
    public static final String GPS_SEARCH_PHONE = "144";

    /**
     * Google Maps
     */
    public static final int UPDATE_INTERVAL = 7000; // update location every 7 sec
    public static final int FATEST_INTERVAL = 30000; // handle one call every 30 sec
    public static final int DISPLACEMENT = 1000; // update location every 100 meters
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    /*
     * CHARSET
     */
    public static final String TYPE_UTF8_CHARSET = "UTF-8";

    /*
     * Image Ping
     */
    public static final int AREAMUJER = R.mipmap.icon_pingbd;
    public static final int ADULTOSMAYORES = R.mipmap.icon_pinggd;
    public static final int AREASOCIAL = R.mipmap.icon_pinggl;
    public static final int CENTROREFERENCIA = R.mipmap.icon_pingod;
    public static final int COMISARIA = R.mipmap.icon_pingpd;
    public static final int COMISARIAMUJER = R.mipmap.icon_pingpl;
    public static final int DERIVACION = R.mipmap.icon_pingppl;
    public static final int FAMILIA = R.mipmap.icon_pingol;
    public static final int ONG = R.mipmap.icon_pingbl;
    public static final int NINEZADOLESCENCIA = R.mipmap.icon_pingppd;
    public static final int SALUD = R.mipmap.icon_pinggd;
    public static final int VIOLENCIAMEDIATICA = R.mipmap.icon_pingbd;
}
