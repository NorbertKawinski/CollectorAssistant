package net.kawinski.collecting.presentation.controllers;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;


@WebServlet(name = "BarcodeServlet", urlPatterns = {"/barcode"})
@Slf4j
public class BarcodeServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        final String value = request.getParameter("value");
        if(value == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            final BufferedImage image = generateBarcodeImage(value);
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            final byte[] content = baos.toByteArray();
            response.setContentType("image/png");

            // No need for closing. This stream is managed by the container
            final OutputStream os = response.getOutputStream();
            os.write(content);
        } catch (final Exception e) {
            log.error("", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public static BufferedImage generateBarcodeImage(final String barcodeText) throws Exception {
        final Code128Writer barcodeWriter = new Code128Writer();
//        final int width = Math.min(150, 33 * barcodeText.length());
//        final BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, width, 25);
        final BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, 0, 25);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
