package core.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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

    public static short byteArrayToShort(byte[] bytes) {
        short value = 0;
        for (int i=0; i < 2; i++) {
            int shift = (2 -1 -i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    public static short[] intToShort(int value) {
        byte[] bytes = intToByteArray(value);
        short[] shorts = new short[2];
        shorts[0] = byteArrayToShort(Arrays.copyOfRange(bytes, 0, 2));
        shorts[1] = byteArrayToShort(Arrays.copyOfRange(bytes, 2, 4));
        return shorts;
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

    public static void saveToJS(String name, String script) throws IOException {
        FileWriter writer = new FileWriter("/script/"+ name + ".js");
        writer.write(script);
        writer.close();
    }

    public static String getScriptPathByName(String name) {
        return "/script/" + name + ".js";
    }

}
