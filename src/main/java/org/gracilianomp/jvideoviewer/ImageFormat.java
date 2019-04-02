package org.gracilianomp.jvideoviewer;

import java.io.IOException;
import java.io.InputStream;

public enum  ImageFormat {
    MJPEG,
    YUYV;

    public ImageParser createParser(InputStream in, String[] formatParamaters) throws IOException {

        switch (this) {
            case MJPEG: return new MJPEGParser(in);
            case YUYV: return new YUYVParser(in, formatParamaters);
            default: throw new IllegalStateException("Can't handle: "+ this) ;
        }

    }
}
