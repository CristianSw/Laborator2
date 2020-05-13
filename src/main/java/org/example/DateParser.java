package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DateParser  {
    File file = new File("date.txt");
    Set<String> dateSet = new HashSet<>();

    public Set<String> parsedSet() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String string;
        while ((string = bufferedReader.readLine()) != null) {
            dateSet.add(string);
        }
        System.out.println("User dates are : ");
        for (String s : dateSet) {

            System.out.println(s);
        }
        return dateSet;
    }

    public Set<String> getDateSet() {
        return dateSet;
    }

    public void setDateSet(Set<String> dateSet) {
        this.dateSet = dateSet;
    }
}
