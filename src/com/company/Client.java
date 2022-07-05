package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final int MAKE_PORT=6666;
    public static final int CANCEL_PORT=6667;
    public static void main(String[]args) throws IOException {

        Scanner cin=new Scanner(System.in);
        System.out.print("Welcome please enter your name: ");
        String patientName=cin.nextLine();

        //creating sockets
        Socket sMake=new Socket("localhost",MAKE_PORT);
        Socket sCancel=new Socket("localhost",CANCEL_PORT);

        //creating two printWriters for two ports
        PrintWriter outMake = new PrintWriter(sMake.getOutputStream(), true);
        PrintWriter outCancel = new PrintWriter(sCancel.getOutputStream(), true);

        //creating two scanners for two ports
        Scanner inMake=new Scanner(sMake.getInputStream());
        Scanner inCancel=new Scanner(sCancel.getInputStream());

        //sending the patient name to the two ports
        outMake.println(patientName);
        outCancel.println(patientName);

        while(true)
        {
            System.out.print("Enter the operation you want (1)make an appointment\n");
            System.out.print("                             (2)cancel an appointment\n");
            System.out.print("                             (3)exit the application\n");
            int op=cin.nextInt();
            System.out.print("the doctor ID: ");
            int docID=cin.nextInt();
            System.out.print("the time slot you want: ");
            int timeSlot=cin.nextInt();
            String message;
            if(op==1)
            {
                outMake.println(docID);
                outMake.println(timeSlot);
                message=inMake.nextLine();
            }
            else if(op==2)
            {
                outCancel.println(docID);
                outCancel.println(timeSlot);
                message=inCancel.nextLine();
            }
            else
            {
                outMake.println(-1);
                outCancel.println(-1);
                break;
            }
            System.out.println(message);
        }
        outMake.close();
        outCancel.close();
        inMake.close();
        inCancel.close();
        sMake.close();
        sCancel.close();
    }

}
