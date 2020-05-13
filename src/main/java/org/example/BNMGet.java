package org.example;

import com.thoughtworks.xstream.XStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class BNMGet {
    public static void main(String[] args) throws Exception {
        DateParser dateParser = new DateParser();
        Set<String> userDate = dateParser.parsedSet();
        int i = 1;

        for (String s : userDate) {
            System.out.println("\nCurrency values for date : " + s);
            System.out.println("\n\n");

            try {
                XStream xstream = new XStream();
                xstream.processAnnotations(Valute.class);
                xstream.processAnnotations(ValCurs.class);
                HttpClient httpClient = HttpClientBuilder.create().build();


                HttpGet getRequest = new HttpGet(
                        "https://bnm.md/en/official_exchange_rates?date=" + s);
                getRequest.addHeader("accept", "application/xml");


                HttpResponse response = httpClient.execute(getRequest);

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + response.getStatusLine().getStatusCode());
                }

                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

                String output;
                String xml = "";
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    xml = xml.concat(output.trim());

                }

//            System.out.println(xml);
                ValCurs valCurs;

                if (xml != null) {
                    valCurs = (ValCurs) xstream.fromXML(xml);

                    File outFile = new File("outFile" + i + ".txt");
                    if (outFile.createNewFile()) {
                        System.out.println("File created: " + outFile.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                    FileWriter fileWriter = new FileWriter("outFile" + i + ".txt", StandardCharsets.UTF_8);

                    for (Valute currentVal : valCurs.getValutes()) {
                        System.out.println(currentVal);
                        fileWriter.write(String.valueOf(currentVal));
                    }
                    i++;
                    fileWriter.close();
                }

            } catch (IOException e) {

                e.printStackTrace();

            }
        }
    }

}
