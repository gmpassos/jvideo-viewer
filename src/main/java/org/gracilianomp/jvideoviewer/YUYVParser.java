package org.gracilianomp.jvideoviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class YUYVParser extends ImageParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(YUYVParser.class);

    private final int width;
    private final int height;

    public YUYVParser(InputStream in, String... formatParameters) throws IOException {
        this(in, Integer.parseInt( formatParameters[0] ) , Integer.parseInt( formatParameters[1] ) ) ;
    }

    public YUYVParser(InputStream in, int width, int height) throws IOException {
        super(in);
        this.width = width ;
        this.height = height ;
    }

    @Override
    public BufferedImage parseImage() throws IOException {

        FrameHeader frameHeader = parseFrameHeader();

        byte[] data = readFramePlaneData(frameHeader.planes[0]);
        if (data == null) return null;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        ////////////

        int y = 0 ;
        int x = 0 ;


        for (int i = 0; i < data.length; i+= 4) {
            int y1 = data[i] & 0xFF ;
            int u = data[i+1] & 0xFF ;
            int y2 = data[i+2] & 0xFF ;
            int v = data[i+3] & 0xFF ;

            float y1F = y1 / 255f;
            float y2F = y2 / 255f;
            float uF = u / 255f -0.5f;
            float vF = v / 255f -0.5f;

            int rgb1 = YUVtoRGB(y1F, uF, vF);

            img.setRGB(x,y, rgb1);

            x++ ;
            if (x == width) {
                throw new IllegalStateException() ;
            }

            int rgb2 = YUVtoRGB(y2F, uF, vF);
            img.setRGB(x,y, rgb2);

            x++ ;
            if (x == width) {
                x = 0 ;
                y++ ;
                if (y == height) {
                    break;
                }
            }


        }

        return img ;
    }


    public static int YUVtoRGB(float y, float u, float v){
        int r = (int)((y + 0.000f * u + 1.140f * v) * 255f);
        int g = (int)((y - 0.396f * u - 0.581f * v) * 255f);
        int b = (int)((y + 2.029f * u + 0.000f * v) * 255f);

        r = clip(r) ;
        g = clip(g) ;
        b = clip(b) ;

        return (r << 16) | (g << 8) | b ;
    }

    private static int clip(int p) {
        return p < 0 ? 0 : (p > 255 ? 255 : p) ;
    }


}
