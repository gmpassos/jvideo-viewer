package org.gracilianomp.jvideoviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class VideoStream implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoStream.class);

    final private Socket socket ;
    final private ImageParser imageParser ;
    final private ImageListener imageListener ;

    public VideoStream(Socket socket, ImageParser imageParser, ImageListener imageListener) {
        if (socket == null) throw new IllegalArgumentException("Null socket") ;
        if (imageParser == null) throw new IllegalArgumentException("Null imageParser") ;
        if (imageListener == null) throw new IllegalArgumentException("Null imageListener") ;

        this.socket = socket;
        this.imageParser = imageParser;
        this.imageListener = imageListener;
    }

    @Override
    public void run() {

        LOGGER.info("Processing socket: {}", socket);

        try {
            process() ;
        }
        catch (IOException e) {
            LOGGER.error("Error processing socket: "+ socket, e);
        }

    }

    private void process() throws IOException {
        while ( !imageParser.isClosed() ) {
            BufferedImage image = imageParser.parseImage() ;

            imageListener.onReadImage(image);
        }

        imageParser.close();
    }

}
