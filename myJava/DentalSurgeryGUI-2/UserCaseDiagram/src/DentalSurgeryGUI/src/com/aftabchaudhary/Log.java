package DentalSurgeryGUI.src.com.aftabchaudhary;

/**
 * Created by aftabahmed on 28/10/2016.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Log extends JFrame {

    public static void main(String[] args) {
        Log frame = new Log();
    }

    JButton login = new JButton("Login");
    JPanel panel = new JPanel();
    JTextField user = new JTextField(10);
    JPasswordField pass = new JPasswordField(10);

    Log(){
        super("Login Autentification");
        setSize(300,200);
        setLocation(500,280);
        panel.setLayout (null);


        user.setBounds(850,300,250,20);
        pass.setBounds(850,350,250,20);
        login.setBounds(930,400,80,20);

        panel.add(login);
        panel.add(user);
        panel.add(pass);
        panel.setBackground(Color.DARK_GRAY);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionlogin();
    }

    public void actionlogin(){
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String username = user.getText();
                String password = pass.getText();
                if(username.equals("admin") && password.equals("badar313")) {
                    Dashboard dashboard =new Dashboard();
                    dashboard.setVisible(true);
                    dispose();
                } else {

                    JOptionPane.showMessageDialog(null,"Wrong Password / Username");
                    user.setText("");
                    pass.setText("");
                    user.requestFocus();
                }

            }
        });
    }
}

