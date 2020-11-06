package StegnoApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static StegnoApp.EmbedText.embed;
import static StegnoApp.ExtractText.extract;
import static StegnoApp.SaveImage.saveImage;

public class App {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JButton browseImageButton1;
    private JButton encodeButton;
    private JTextField EncodePath;
    private JTextArea MessageToEncode;
    private JButton browseImageButton2;
    private JTextPane DecodedMessage;
    private JButton decodeButton;
    private JTextField messageToEncode;
    private JTextField DecodePath;
    private JLabel eLabel;
    private JLabel dLabel;
    private JPanel EncodePanel;
    private JPanel DecodePanel;
    private JPanel LogInPanel;
    private JTextField Username;
    private JPasswordField Password;
    private JButton logInButton;
    private JButton resetButton;
    String epath= null;
    String dpath= null;
    String uName= null;
    String pWord= null;
    String EncodeMessage= null;
    String DecodeMessage= null;
    FileReader fr= null;
    BufferedReader br=null;
    BufferedImage originalImage= null;
    BufferedImage encryptedImage= null;
    JFileChooser fc = new JFileChooser();

    private void check() {
        try {
            fr = new FileReader("Credentials.txt");
            br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {
                if (str.equals(uName + " " + pWord)) {
                    JOptionPane.showMessageDialog(null,"Logged In Successfully...");
                    tabbedPane1.setEnabledAt(1,true);
                    tabbedPane1.setEnabledAt(2,true);
                    br.close();
                    fr.close();
                    break;
                }
                else{
                    JOptionPane.showMessageDialog(null,"Invalid Username or Password !");
                }

            }
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Something went Wrong!!!");
        }

    }

public App(){
        tabbedPane1.setEnabledAt(0,true);
        tabbedPane1.setEnabledAt(1,false);
        tabbedPane1.setEnabledAt(2,false);

        browseImageButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //fc.setCurrentDirectory(new File("K:"));
                fc.setDialogTitle("Select Image ");
                fc.showOpenDialog(null);
                File efile= fc.getSelectedFile();
                epath = efile.getAbsolutePath();
                EncodePath.setText(epath);
                try {
                    originalImage=ImageIO.read(new File(epath));
                } catch (IOException ioException) {
                    //ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Invalid Image Path");
                }
            }
        });
        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EncodeMessage=MessageToEncode.getText();
                BufferedImage eImage = embed(originalImage, EncodeMessage);
                saveImage(eImage);
                encryptedImage= eImage;
                eLabel.setText("Your message was hidden successfully....");
            }
        });
        browseImageButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc.setDialogTitle("Select Image ");
                fc.showOpenDialog(null);
                File dfile= fc.getSelectedFile();
                dpath = dfile.getAbsolutePath();
                dpath = dfile.getAbsolutePath();
                DecodePath.setText(dpath);
                try {
                   encryptedImage=ImageIO.read(new File(dpath));
                } catch (IOException ioException) {
                    //ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Invalid Image Path");
                }
            }
        });
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    StringBuilder hiddenText = extract(encryptedImage);
                    DecodeMessage= hiddenText.toString();
                    DecodedMessage.setText(DecodeMessage);
                    dLabel.setText("Your hidden message is printed above...");
                    //JOptionPane.showMessageDialog(null,DecodeMessage);
                } catch (HeadlessException headlessException) {
                    // headlessException.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Invalid Image To Decode");
                }
            }
        });
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uName=Username.getText();
                pWord=Password.getText();
                check();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Username.setText("");
                Password.setText("");
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame= new JFrame("Steganography App");
        frame.setContentPane(new App().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setSize(475,300);
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
