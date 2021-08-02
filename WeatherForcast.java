package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class WeatherForcast {
    LinkedHashMap<String, LinkedHashMap<String, List<Float>>> map = new LinkedHashMap<>();//This holds all the data
    LinkedHashMap<String, List<Float>> dayvalue2 = new LinkedHashMap<>();
    List<LinkedHashMap<String, List<Float>>> dayvalue1 = new ArrayList<>();// This holds data for the temprature
    List<Float> temp2 = new ArrayList<>();
    LinkedList<String> Day = new LinkedList<>(Arrays.asList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"));
    int counter,fixed=2,count =0,r=0;
    String name,minname,maxname,name1,avename;
    float aveday,wmin,wmax,max,min;
    List<Float> a = new LinkedList<>();



// to read the file and put into the map
    private  void data_reader() {

        try {

                File myFile = new File("/home/biruk/IdeaProjects/1st project/DATA.txt");
                BufferedReader reader = new BufferedReader((new FileReader(myFile)));
                String line;
                 while ((line = reader.readLine()) != null) {
                         List<String> temp = new ArrayList<>();
                         List<Float> temp1 = new ArrayList<>();
                         LinkedHashMap<String, List<Float>> dayvalue = new LinkedHashMap<>();
                if(r<5)
                {

                    temp = Arrays.asList(line.split(" "));

                        name = temp.get(0);

                    count = 0;
                    fixed = 2;
                    for (counter = 1; counter <= 14; counter++)

                                temp1.add(Float.valueOf(temp.get(counter)));



                                for (int i = 0; i < 7; i++) {
                                    temp2 = temp1.subList(count, fixed);
                                    count += 2;
                                    fixed += 2;
                                    dayvalue.put(Day.get(i), temp2);
                                }
                                dayvalue1.add(dayvalue);
                                map.put(name,dayvalue1.get(r));
                }
                r++; // This helps to track the line
            }
            reader.close();
        }

        catch (IOException e) {

            System.out.println("File not Found");
        }

    }
    private void printmaxvalues(){
        for(String l :Day){
          printmap(true,true,l,l,false,false);
        }


    }


    //to display the map
    public void printmap(boolean daymaxs,boolean daymins,String daymax,String daymin,boolean weeklymax,boolean weaklylowest) {
        wmin=100;
        wmax=0;
                  min = 100;
                  max=0;
        map.forEach((K,V)->{      // mapofmaps entries
            V.forEach((X,Y)->{   // inner Hashmap enteries
               // System.out.println(X + " " + Y);  // print key and value of inner Hashmap
                float ave;
              if(weeklymax) {
                  if (wmax < Collections.max(Y)) {
                      wmax = Collections.max(Y);
                      name1 = K;
                  }
              }
                if(weaklylowest) {
                    if (wmin > Collections.min(Y)) {
                        wmin = Collections.min(Y);
                        name = K;
                    }
                }
              if(daymaxs){
                  if(daymax == X){
                      if (max < Collections.max(Y)) {
                          max = Collections.max(Y);
                          maxname = K;
                      }
                  }
              }
                if(daymins){
                    if(daymin == X){
                        if ( min > Collections.min(Y)) {
                            min = Collections.min(Y);
                            minname = K;
                        }
                    }
                }

                        if(daymin == X) {
                    if(aveday <  (Y.get(0) + Y.get(1)) / 2){
                        aveday = (Y.get(0) + Y.get(1)) / 2;
                        avename = K;
                    }





                        }





            });







        });
        aveday = (max + min)/2;
        if(daymaxs) {
            System.out.println("The highest temperature for " + daymax + " is in " + maxname + " with " + max + "°C");
            System.out.println("The Average temperature  of " + daymax + " is " + aveday + "°C");
            System.out.println("The lowest temperature for " + daymin + " is in " + minname + " with " + min + "°C"+"\n" +"\n" + "\n");
        }




    }
    public static void main(String[] args) {
        WeatherForcast weatherForcast = new WeatherForcast();
        weatherForcast.data_reader();


        System.out.println("----------------------------------------------DAILY Highest,Lowest and Average  temperature ..........................................");
        weatherForcast.printmaxvalues();
        weatherForcast.printmap(false,false,"test","test",true,true);
        System.out.println("...............................................Weakly Highest and Lowest City -------------------------------------------------");
        System.out.println("The Coldest City for this week is "+ weatherForcast.name + " with " + weatherForcast.wmin + "°C") ;
        System.out.println("The Hottest City for this week is "+ weatherForcast.name1 +" with " + weatherForcast.wmax + "°C");


    }
}
