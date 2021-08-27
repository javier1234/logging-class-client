package com.despegar.jav;

public class App {

    public static void main(String[] args) {
        final Connector connector = ConnectorDummy.getInstance("dbData", 65535, "carlos.alvarez", "despegar1234");
        System.out.println("Connector built \n with user: carlos.alvaruez \n PORT: despegar1234");
        final Request request = new Request(1000);

        try {
            Response searchResponse = connector.search(request);
            System.out.println("retrieved: " + searchResponse.toString() + "\n with request:" + request.toString());
            boolean mustScroll = !searchResponse.end();
            try {
                while (mustScroll) {
                    Response scrollResponse = connector.scroll(request);
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
