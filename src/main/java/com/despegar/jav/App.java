package com.despegar.jav;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    /**
     * host estable, bajo rate de errores
     */
    private static final String host = "dbUserDespegar.com";

    /**
     * Host inestable, alto rate de errores
     */
    //private static final String host = "dbUserOld.com";

    private static final int port = 3015;
    private static final String user = "carlos.alvarez";
    private static final String pass = "despegar1234";

    public static void main(String[] args) {
        final Connector connector = ConnectorDummy.newInstance(host, port, user, pass);
        LOGGER.info("Connector built with host: {} and PORT: {}}", host, port); ;
        final Request request = new Request(1000);

        try {
            LOGGER.debug("Retrieving {} users", request.limit());
            Response searchResponse = connector.search(request);
            LOGGER.info("{} users retrieved", searchResponse.read());
            boolean mustScroll = !searchResponse.end();
            try {
                while (mustScroll) {
                    LOGGER.info("Scrolling {} users", request.limit());
                    final Response scrollResponse = connector.scroll(request);
                    LOGGER.info("{} users scrolled", scrollResponse.read());
                    mustScroll = !scrollResponse.end();
                }
            } catch (SearchException se) {
                LOGGER.error("Error while performing search operation", se);
            }
        } catch (SearchException se) {
            LOGGER.info("Closing scroll");
        } finally {
            try {
                connector.clearScroll();
            } catch (SearchException se) {
                LOGGER.error("Error while performing clear scroll", se);
            }
        }
        LOGGER.info("end process");
    }
}
