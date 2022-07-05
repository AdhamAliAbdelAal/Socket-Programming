package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread {
    public static final int MAKE_PORT=6666;
    public static final int CANCEL_PORT=6667;
    public static Hospital hospital;
    private Socket socket;
    public Server(Socket s)
    {
        socket=s;
    }
    public void run()
    {
        try {
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            Scanner in=new Scanner(socket.getInputStream());
            String patientName=in.nextLine();
            System.out.println(patientName+" is connected to the server");
            while(true)
            {
                int docID=in.nextInt();
                if(docID==-1)
                {
                    break;
                }
                int timeSlot=in.nextInt();
                if(socket.getLocalPort()==CANCEL_PORT)
                {
                    int response=hospital.cancelAppointment(docID,timeSlot,patientName);
                    switch(response)
                    {
                        case 1:
                            out.println("Cancelling the appointment is done successfully (Success)");
                            break;
                        case 2:
                            out.println("the doctor id is not found in hospital (Failure)");
                            break;
                        case 3:
                            out.println("the timeslot index is out of boundary (Failure)");
                            break;
                        case 4:
                            out.println("the doctor doesn’t have an appointment at this timeslot (Failure)");
                            break;
                        default:
                            out.println("the doctor has an appointment to a different patient name at this timeslot (Failure)");
                    }
                }
                else
                {
                    int response=hospital.makeAppointment(docID,timeSlot,patientName);
                    switch(response)
                    {
                        case 1:
                            out.println("Making the appointment is done successfully (Success)");
                            break;
                        case 2:
                            out.println("the doctor id is not found in hospital (Failure)");
                            break;
                        case 3:
                            out.println("the timeslot index is out of boundary (Failure)");
                            break;
                        case 4:
                            out.println("the doctor is already busy at this timeslot (Failure)");
                            break;
                    }
                }
                hospital.print();
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[]args) throws FileNotFoundException {
        hospital=new Hospital("doctors.txt");
        new Thread(){
            public void run()
            {
                try {
                    ServerSocket ss=new ServerSocket(MAKE_PORT);
                    while(true)
                    {
                        new Server(ss.accept()).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            public void run()
            {
                try {
                    ServerSocket ss=new ServerSocket(CANCEL_PORT);
                    while(true)
                    {
                        new Server(ss.accept()).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
