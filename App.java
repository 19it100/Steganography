package StegnoApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static StegnoApp.EmbedText.embed;
import static StegnoApp.ExtractText.extract;
import static StegnoApp.SaveImage.saveImage;

public class App {
    private JButton encode;
    private JPanel panel1;
    private JButton decode;
    private JTextField EncodePath;
    private JTextField messageToEncode;
    private JTextField DecodePath;
    private JFileChooser imageToDecode;
    private JLabel eLabel;
    private JLabel dLabel;
    private JButton button1;
    String epath= null;
    String dpath= null;
    String EncodeMessage= null;
    String DecodeMessage= null;
    BufferedImage originalImage= null;
    BufferedImage encryptedImage= null;
    //JTabbedPane

    public App() {
        EncodePath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String ep= new String();
                ep= EncodePath.getText();
                epath=ep;
                // JOptionPane.showMessageDialog(null,s); //Print in New Message Window..
                BufferedImage eImage = null;
                try {
                    eImage= ImageIO.read(new File(epath));
                    originalImage=eImage;
                } catch (IOException ioException) {
                    //ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Invalid Image Path");
                }
            }
        });
        messageToEncode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg= new String();
                msg = messageToEncode.getText();
                EncodeMessage=msg;
                //JOptionPane.showMessageDialog(null,EncodeMessage);
            }

        });
        encode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage eImage = embed(originalImage, EncodeMessage);
                saveImage(eImage);
                encryptedImage= eImage;
            }
        });
        DecodePath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dp= new String();
                dp= DecodePath.getText();
                dpath=dp;
                // JOptionPane.showMessageDialog(null,s); //Print in New Message Window..
                BufferedImage dImage = null;
                try {
                    dImage= ImageIO.read(new File(dpath));

                } catch (IOException ioException) {
                    //ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Invalid Image Path");
                }

            }
        });

        decode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                   StringBuilder hiddenText = extract(encryptedImage);
                   DecodeMessage= hiddenText.toString();
                   JOptionPane.showMessageDialog(null,DecodeMessage);
               } catch (HeadlessException headlessException) {
                  // headlessException.printStackTrace();
                   JOptionPane.showMessageDialog(null,"Invalid Image To Decode");
               }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame= new JFrame("Steganography App");
        frame.setContentPane(new App().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,250);
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}