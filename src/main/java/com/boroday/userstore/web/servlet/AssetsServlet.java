package com.boroday.userstore.web.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class AssetsServlet extends HttpServlet {
    private static final int BUFFER_SIZE = 8192;

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        log.info("Resources are requested");

        String requestURI = request.getRequestURI().substring(1);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(requestURI)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int numBytesRead;
            while ((numBytesRead = inputStream.read(buffer)) > 0) {
                response.getOutputStream().write(buffer, 0, numBytesRead);
            }
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (NullPointerException e) {
            log.error("Resources URI is not found");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException("Not possible to get resource by the request: " + requestURI);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException("Not possible to get resource by the request: " + requestURI, e);
        }

    }
}
