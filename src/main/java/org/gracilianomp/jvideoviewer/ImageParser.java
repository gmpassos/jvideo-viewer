package org.gracilianomp.jvideoviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

abstract public class ImageParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageParser.class);

    final InputStream in ;

    public ImageParser(InputStream in) throws IOException {
        this.in = in;
        parseStreamHeader();
    }

    private StreamHeader streamHeader ;

    public StreamHeader getStreamHeader() {
        return streamHeader;
    }

    public void parseStreamHeader() throws IOException {
        DataInputStream din = new DataInputStream(in);

        int streamID = din.readInt();
        int streamVersion = din.readInt();
        int streamPacketFmtVideo = din.readInt();
        int streamPacketFmtVideoSize = din.readInt();
        int streamPacketFmtVideoFmt = din.readInt();
        int g_num_planes = din.readInt();
        int g_pixelformat = din.readInt();
        int g_width = din.readInt();
        int g_height = din.readInt();
        int g_field = din.readInt();
        int g_colorspace = din.readInt();
        int g_ycbcr_enc = din.readInt();
        int g_quantization = din.readInt();
        int g_xfer_func = din.readInt();
        int g_flags = din.readInt();
        int pixelaspectNumerator = din.readInt();
        int pixelaspectDenominator = din.readInt();

        StreamHeader.Plane[] planes = new StreamHeader.Plane[g_num_planes];

        for (int i = 0; i < g_num_planes; i++) {
            int streamPacketFmtVideoSizeFmtPlane = din.readInt();
            int g_sizeimage = din.readInt();
            int g_bytesperline = din.readInt();
            planes[i] = new StreamHeader.Plane(streamPacketFmtVideoSizeFmtPlane, g_sizeimage, g_bytesperline);
        }

        this.streamHeader = new StreamHeader(streamID, streamVersion, streamPacketFmtVideo, streamPacketFmtVideoSize, streamPacketFmtVideoFmt, g_num_planes, g_pixelformat, g_width, g_height, g_field, g_colorspace, g_ycbcr_enc ,g_quantization, g_xfer_func, g_flags, pixelaspectNumerator, pixelaspectDenominator, planes) ;

        LOGGER.info("StreamHeader> {}", this.streamHeader);
    }


    public FrameHeader parseFrameHeader() throws IOException {
        DataInputStream din = new DataInputStream(in);

        int streamPacketFrameVideo = din.readInt();
        int streamPacketFrameVideoSize = din.readInt();
        int streamPacketFrameVideoSizeHDR = din.readInt();
        int field = din.readInt();
        int flags = din.readInt();

        int num_planes = this.streamHeader.g_num_planes;

        FrameHeader.Plane[] planes = new FrameHeader.Plane[num_planes] ;

        for (int i = 0; i < num_planes; i++) {
            int streamPacketFrameVideoSizePlaneHDR = din.readInt();
            int used = din.readInt();
            int rle_size = din.readInt();

            planes[i] = new FrameHeader.Plane(streamPacketFrameVideoSizePlaneHDR, used, rle_size) ;
        }

        FrameHeader frameHeader = new FrameHeader(streamPacketFrameVideo, streamPacketFrameVideoSize, streamPacketFrameVideoSizeHDR, field, flags, planes);

        LOGGER.info("frameHeader> {}", frameHeader);

        return frameHeader;
    }

    public byte[] readFramePlaneData(FrameHeader.Plane plane) throws IOException {
        byte[] data = new byte[ plane.rle_size ] ;

        int read = 0 ;

        while ( read < data.length ) {
            int r = in.read(data, read, data.length-read) ;

            if (r < 0) {
                close();
                return null;
            }

            read += r ;
        }

        return data;
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
