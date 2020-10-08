package StegnoApp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImage
{
    public static void saveImage(BufferedImage image)
    {
        try
        {
            ImageIO.write(image,"png",new File("K:\\encryptedImage.png"));
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}