package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Hospital {
    //ArrayList<Doctor>doctors;
    HashMap<Integer,Doctor>doctors;
    public Hospital(String file) throws FileNotFoundException {
        File myFile = new File(file);
        doctors=new HashMap<Integer,Doctor>();
        Scanner cin =new Scanner(myFile);
        while(cin.hasNext())
        {
            int id=cin.nextInt();
            String name=cin.nextLine();
            doctors.put(id,new Doctor(id,name));
        }
    }
    public int makeAppointment(int docID,int timeSlot,String patientName)
    {
        if(!doctors.containsKey(docID))
            return 2;
        return doctors.get(docID).makeAppointment(timeSlot,patientName);
    }
    public int cancelAppointment(int docID,int timeSlot,String patientName)
    {
        if(!doctors.containsKey(docID))
            return 2;
        return doctors.get(docID).cancelAppointment(timeSlot,patientName);
    }
    public void print()
    {
        for(var doctor:doctors.entrySet())
        {
            System.out.println("Doctor:"+doctor.getValue().name+", Doctor ID: "+doctor.getValue().id);
            boolean[]timeSlots=doctor.getValue().timeSlots;
            String[]patients=doctor.getValue().patients;
            for(int i=0;i<3;i++)
            {
                if(timeSlots[i])
                {
                    System.out.println("Time Slot #"+i+" -----> "+patients[i]);
                }
                else
                {
                    System.out.println("Time Slot #"+i+" -----> empty");
                }
            }
        }
    }
}
