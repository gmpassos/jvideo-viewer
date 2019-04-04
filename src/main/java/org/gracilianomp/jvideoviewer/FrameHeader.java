package org.gracilianomp.jvideoviewer;

import java.util.Arrays;

public class FrameHeader {

    static public class Plane {
        final public int streamPacketFrameVideoSizePlaneHDR ;
        final public int used ;
        final public int rle_size ;

        public Plane(int streamPacketFrameVideoSizePlaneHDR, int used, int rle_size) {
            this.streamPacketFrameVideoSizePlaneHDR = streamPacketFrameVideoSizePlaneHDR;
            this.used = used;
            this.rle_size = rle_size;
        }

        @Override
        public String toString() {
            return "Plane{" +
                    "streamPacketFrameVideoSizePlaneHDR=" + streamPacketFrameVideoSizePlaneHDR +
                    ", used=" + used +
                    ", rle_size=" + rle_size +
                    '}';
        }
    }

    final public int streamPacketFrameVideo ;
    final public int streamPacketFrameVideoSize ;
    final public int streamPacketFrameVideoSizeHDR ;
    final public int field ;
    final public int flags ;
    final public Plane[] planes ;

    public FrameHeader(int streamPacketFrameVideo, int streamPacketFrameVideoSize, int streamPacketFrameVideoSizeHDR, int field, int flags, Plane[] planes) {
        this.streamPacketFrameVideo = streamPacketFrameVideo;
        this.streamPacketFrameVideoSize = streamPacketFrameVideoSize;
        this.streamPacketFrameVideoSizeHDR = streamPacketFrameVideoSizeHDR;
        this.field = field;
        this.flags = flags;
        this.planes = planes ;
    }

    @Override
    public String toString() {
        return "FrameHeader{" +
                "streamPacketFrameVideo=" + streamPacketFrameVideo +
                ", streamPacketFrameVideoSize=" + streamPacketFrameVideoSize +
                ", streamPacketFrameVideoSizeHDR=" + streamPacketFrameVideoSizeHDR +
                ", field=" + field +
                ", flags=" + flags +
                ", planes=" + Arrays.toString(planes) +
                '}';
    }

}
