package org.gracilianomp.jvideoviewer;

import java.util.Arrays;

public class StreamHeader {

    static public class Plane {
        final int streamPacketFmtVideoSizeFmtPlane ;
        final int g_sizeimage ;
        final int g_bytesperline ;

        public Plane(int streamPacketFmtVideoSizeFmtPlane, int g_sizeimage, int g_bytesperline) {
            this.streamPacketFmtVideoSizeFmtPlane = streamPacketFmtVideoSizeFmtPlane;
            this.g_sizeimage = g_sizeimage;
            this.g_bytesperline = g_bytesperline;
        }

        @Override
        public String toString() {
            return "Plane{" +
                    "streamPacketFmtVideoSizeFmtPlane=" + streamPacketFmtVideoSizeFmtPlane +
                    ", g_sizeimage=" + g_sizeimage +
                    ", g_bytesperline=" + g_bytesperline +
                    '}';
        }
    }

    final public int streamID ;
    final public int streamVersion ;
    final public int streamPacketFmtVideo ;
    final public int streamPacketFmtVideoSize ;
    final public int streamPacketFmtVideoFmt ;
    final public int g_num_planes ;
    final public int g_pixelformat ;
    final public int g_width ;
    final public int g_height ;
    final public int g_field ;
    final public int g_colorspace ;
    final public int g_ycbcr_enc ;
    final public int g_quantization ;
    final public int g_xfer_func ;
    final public int g_flags ;
    final public int pixelaspectNumerator ;
    final public int pixelaspectDenominator ;
    final public Plane[] planes ;

    public StreamHeader(int streamID, int streamVersion, int streamPacketFmtVideo, int streamPacketFmtVideoSize, int streamPacketFmtVideoFmt, int g_num_planes, int g_pixelformat, int g_width, int g_height, int g_field, int g_colorspace, int g_ycbcr_enc, int g_quantization, int g_xfer_func, int g_flags, int pixelaspectNumerator, int pixelaspectDenominator, Plane[] planes) {
        this.streamID = streamID;
        this.streamVersion = streamVersion;
        this.streamPacketFmtVideo = streamPacketFmtVideo;
        this.streamPacketFmtVideoSize = streamPacketFmtVideoSize;
        this.streamPacketFmtVideoFmt = streamPacketFmtVideoFmt;
        this.g_num_planes = g_num_planes;
        this.g_pixelformat = g_pixelformat;
        this.g_width = g_width;
        this.g_height = g_height;
        this.g_field = g_field;
        this.g_colorspace = g_colorspace;
        this.g_ycbcr_enc = g_ycbcr_enc;
        this.g_quantization = g_quantization;
        this.g_xfer_func = g_xfer_func;
        this.g_flags = g_flags;
        this.pixelaspectNumerator = pixelaspectNumerator;
        this.pixelaspectDenominator = pixelaspectDenominator;
        this.planes = planes;
    }

    @Override
    public String toString() {
        return "StreamHeader{" +
                "streamID=" + streamID +
                ", streamVersion=" + streamVersion +
                ", streamPacketFmtVideo=" + streamPacketFmtVideo +
                ", streamPacketFmtVideoSize=" + streamPacketFmtVideoSize +
                ", streamPacketFmtVideoFmt=" + streamPacketFmtVideoFmt +
                ", g_num_planes=" + g_num_planes +
                ", g_pixelformat=" + g_pixelformat +
                ", g_width=" + g_width +
                ", g_height=" + g_height +
                ", g_field=" + g_field +
                ", g_colorspace=" + g_colorspace +
                ", g_ycbcr_enc=" + g_ycbcr_enc +
                ", g_quantization=" + g_quantization +
                ", g_xfer_func=" + g_xfer_func +
                ", g_flags=" + g_flags +
                ", pixelaspectNumerator=" + pixelaspectNumerator +
                ", pixelaspectDenominator=" + pixelaspectDenominator +
                ", planes=" + Arrays.toString(planes) +
                '}';
    }

}
