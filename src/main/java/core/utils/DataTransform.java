package core.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class DataTransform {

    /**
     * int到byte[]
     * @param i
     * @return
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        // 由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * byte[]转int
     * @param bytes
     * @return
     */
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        // 由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }

    public static int bytesToInt(byte b1,byte b2,byte b3,byte b4) {
        return byteArrayToInt(new byte[]{b1, b2, b3, b4});
    }

    public static String imageToBase64(InputStream in, String imageType) throws IOException {
        BufferedImage image = ImageIO.read(in);

        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, imageType, bos);
        byte[] imageBytes = bos.toByteArray();
        Base64.Encoder encoder = Base64.getEncoder();
        imageString = encoder.encodeToString(imageBytes);
        bos.close();
        return imageString;
    }

}
