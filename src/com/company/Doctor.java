package com.company;

public class Doctor {
    int id;
    String name;
    boolean []timeSlots;
    String[]patients;
    public Doctor(int id,String name)
    {
        this.id=id;
        this.name=name;
        timeSlots=new boolean[3];
        patients=new String[3];
    }
    public int makeAppointment(int index,String patientName)
    {
        if(index>2||index<0)
        {
            return 3;
        }
        if(!timeSlots[index])
        {
            timeSlots[index]=true;
            patients[index]=patientName;
            return 1;
        }
        return 4;
    }
    public int cancelAppointment(int index,String patientName)
    {
        if(index>2||index<0)
        {
            return 3;
        }
        if(timeSlots[index])
        {
            if(!patientName.equals(patients[index]))
                return 5;
            timeSlots[index]=false;
            patients[index]="";
            return 1;
        }
        return 4;
    }

}
