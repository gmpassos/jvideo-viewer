package org.gracilianomp.jvideoviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class JVideoViewer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JVideoViewer.class);

    final private int listenPort ;
    final private ImageFormat imageFormat ;
    final private String[] formatParameters ;

    public JVideoViewer(int listenPort, ImageFormat imageFormat, String[] formatParameters) {
        this.listenPort = listenPort;
        this.imageFormat = imageFormat;
        this.formatParameters = formatParameters != null ? formatParameters : new String[0];

        try {
            testFormat(imageFormat, formatParameters);
        }
        catch (Exception e) {
            LOGGER.error("Can't use format "+ imageFormat +" with formatParameters: "+ Arrays.toString(formatParameters), e);
            throw new IllegalStateException("ImageFormat error", e) ;
        }
    }

    public void testFormat(ImageFormat imageFormat, String[] formatParameters) throws IOException {
        imageFormat.createParser(new ByteArrayInputStream(new byte[0]), formatParameters);
    }

    private ServerSocket server;

    public boolean isInitialized() {
        return this.server != null ;
    }

    synchronized  public void start() throws IOException {
        if ( isInitialized() ) throw new IllegalStateException("Already initialized") ;
        this.server = new ServerSocket(listenPort);

        LOGGER.info("Server listening: {}", server);

        new Thread( this::acceptLoop , "Server["+ listenPort +"].acceptLoop" ).start() ;
    }

    private void acceptLoop() {

        LOGGER.info("Initialized server: {} > imageFormat: {} , formatParameters: {}", server, imageFormat, formatParameters);

        try {
            while ( !server.isClosed() ) {
                Socket socket = server.accept();

                accept(socket) ;
            }
        }
        catch (IOException e) {
            LOGGER.error("Error accepting socket", e);
        }

    }

    private void accept(Socket socket) throws IOException {

        LOGGER.info("Accepted socket: {}", socket);

        ImageParser imageParser = imageFormat.createParser(socket.getInputStream(), formatParameters);

        String hostAddress = socket.getInetAddress().getHostAddress();
        ImageViewer imageViewer = new ImageViewer( imageFormat , hostAddress);

        VideoStream videoStream = new VideoStream(socket, imageParser, imageViewer);

        new Thread(videoStream, "VideoStream["+ hostAddress +"]").start(); ;

    }

    static public void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println("USAGE:");
            System.out.println("  $> java "+ JVideoViewer.class.getName() +" %port %format %formatParameters");
            System.exit(1);
        }

        int port = Integer.parseInt( args[0] ) ;
        String formatStr = args[1] ;

        ImageFormat imageFormat = ImageFormat.valueOf(formatStr.toUpperCase());

        String[] formatParameters = args.length > 2 ? Arrays.copyOfRange(args, 2, args.length) : null ;

        JVideoViewer jVideoViewer = new JVideoViewer( port , imageFormat , formatParameters );

        jVideoViewer.start();

    }

}
