package org.gracilianomp.jvideoviewer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

abstract public class ImageParser {

    final InputStream in ;

    public ImageParser(InputStream in) {
        this.in = in;
    }

    abstract public BufferedImage parseImage() throws IOException;

    private boolean closed ;

    synchronized public boolean isClosed() {
        return closed ;
    }

    synchronized public void close() {
        if ( isClosed() ) return ;

        this.closed = true ;

        try {
            in.close();
        }
        catch (IOException ignored) { }
    }

}
