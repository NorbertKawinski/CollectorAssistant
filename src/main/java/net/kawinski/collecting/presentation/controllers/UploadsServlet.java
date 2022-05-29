package net.kawinski.collecting.presentation.controllers;


import net.kawinski.collecting.service.media.MediaService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


//@WebServlet(name = "UploadsServlet", urlPatterns = {"/uploads/*"})
@WebServlet(name = "UploadsServlet", urlPatterns = {"/uploads"})
public class UploadsServlet extends HttpServlet {

    @Inject
    private MediaService mediaService;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String filename = request.getParameter("file");
        if(filename == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            final byte[] content = mediaService.getUploadContent(filename);

            // TODO: Detect jpg/png
            response.setContentType("image/jpeg");

            // No need for closing. This stream is managed by the container
            final OutputStream os = response.getOutputStream();
            os.write(content);

        } catch (final IllegalArgumentException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
