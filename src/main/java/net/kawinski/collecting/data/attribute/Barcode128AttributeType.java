package net.kawinski.collecting.data.attribute;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import net.kawinski.utils.java.JavaUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Barcode128AttributeType implements BaseAttributeType<String> {
    @Override
    public String parse(final String value) {
        if(value.isEmpty() || value.length() > 80)
            throw new IllegalArgumentException("Invalid barcode length");
        return value;
    }

    @Override
    public String toString(final String value) {
        return value;
    }

    public static String scanImage(final String fileName, final byte[] imageRaw) throws FormatException, ChecksumException, NotFoundException, IOException {
        final String fileExt = JavaUtils.getExtensionOrThrow(fileName);
        if(!fileExt.equalsIgnoreCase("png") && !fileExt.equalsIgnoreCase("jpg"))
            throw new IllegalArgumentException("Unsupported file type '" + fileExt + "'");
        final InputStream is = new BufferedInputStream(new ByteArrayInputStream(imageRaw));
        final BufferedImage awtImage = ImageIO.read(is);
        return scanQRImage(awtImage);
    }

    private static String scanQRImage(final BufferedImage image) throws FormatException, ChecksumException, NotFoundException {
        final int[] intArray = new int[image.getWidth() * image.getHeight()];
        //copy pixel data from the Bitmap into the 'intArray' array
//        image.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), intArray, 0, image.getWidth());

        final LuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), intArray);
        final BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        final Reader reader = new MultiFormatReader();
        final Result result = reader.decode(bitmap);
        return result.getText();
    }
}
