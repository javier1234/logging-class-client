package com.despegar.jav;

public class App {

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
        final Connector connector = ConnectorDummy.newInstance(host, port, "carlos.alvarez", "despegar1234");
        System.out.println("Connector built \n with user: carlos.alvaruez \n PORT: despegar1234");
        final Request request = new Request(1000);

        try {
            Response searchResponse = connector.search(request);
            System.out.println("retrieved: " + searchResponse.toString() + "\n with request:" + request.toString());
            boolean mustScroll = !searchResponse.end();
            int totalRead = 0;
            try {
                while (mustScroll) {
                    Response scrollResponse = connector.scroll(request);
                    totalRead += scrollResponse.read();
                    mustScroll = !scrollResponse.end();
                    System.out.println("retrieved: " + searchResponse.toString() + "\n with request:" + request.toString());
                }
                System.out.println("end scolling");
            } catch (SearchException se) {
                System.out.println("Error");
            }
        } catch (SearchException se) {
            System.out.println("Error");
        } finally {
            try {
                connector.clearScroll();
            } catch (SearchException se) {}
        }
        System.out.println("The end");
    }
}
