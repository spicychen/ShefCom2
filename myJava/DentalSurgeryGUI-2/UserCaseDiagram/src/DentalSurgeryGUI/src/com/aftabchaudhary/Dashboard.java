package DentalSurgeryGUI.src.com.aftabchaudhary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Dashboard extends JFrame {

    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
    }

    JLabel welcome = new JLabel("Welcome to a Dashboard");
    JPanel panel = new JPanel();
    Icon addIcon = new ImageIcon("image/add.png");
    JButton addbtn = new JButton(addIcon);
    Icon repIcon = new ImageIcon("image/report.png");
    JButton repbtn = new JButton(repIcon);
    Icon scheduleIcon = new ImageIcon("image/schedule.png");
    JButton schedulebtn = new JButton(scheduleIcon);
    Icon patientIcon = new ImageIcon("image/patient.jpg");
    JButton patbtn = new JButton(patientIcon);
    Icon appointIcon = new ImageIcon("image/appoint.png");
    JButton appointbtn = new JButton(appointIcon);
    Icon visitIcon = new ImageIcon("image/visit.png");
    JButton visitbtn = new JButton(visitIcon);
    Icon billIcon = new ImageIcon("image/bill.png");
    JButton billbtn = new JButton(billIcon);
    Icon payIcon = new ImageIcon("image/payment.png");
    JButton paybtn = new JButton(payIcon);




    Dashboard(){
        super("Dashboard");
        setSize(300,200);
        setLocation(500,280);
        panel.setLayout (null);

        welcome.setBounds(900,50,150,60);
        welcome.setForeground(Color.WHITE);
        addbtn.setBounds(350,300,130,130);
        repbtn.setBounds(850,300,130,130);
        schedulebtn.setBounds(350,550,130,130);
        appointbtn.setBounds(1300,300,130,130);
        patbtn.setBounds(850,550,130,130);
        visitbtn.setBounds(1300,550,130,130);
        billbtn.setBounds(600,800,130,130);
        paybtn.setBounds(1075,800,130,130);


        panel.add(welcome);
        panel.add(addbtn);
        panel.add(repbtn);
        panel.add(schedulebtn);
        panel.add(appointbtn);
        panel.add(patbtn);
        panel.add(visitbtn);
        panel.add(billbtn);
        panel.add(paybtn);
        panel.setBackground(Color.DARK_GRAY);


        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}