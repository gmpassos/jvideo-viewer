package org.gracilianomp.jvideoviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

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

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int y = 0 ;
        int x = 0 ;

        byte[] buff = new byte[4] ;

        int imageSize = 0 ;

        while ( !isClosed() ) {

            int read = 0 ;

            while (read < 4) {
                int r = in.read(buff, read, 4-read) ;

                if (r < 0) {
                    close();
                    return null ;
                }

                read += r ;
            }

            imageSize += 4 ;

            int y1 = buff[0] & 0xFF ;
            int u = buff[1] & 0xFF ;
            int y2 = buff[2] & 0xFF ;
            int v = buff[3] & 0xFF ;

            float y1F = y1 / 255f;
            float y2F = y2 / 255f;
            float uF = u / 255f -0.5f;
            float vF = v / 255f -0.5f;

            int rgb1 = YUVtoRGB(y1F, uF, vF);

            img.setRGB(x,y, rgb1);

            x++ ;
            if (x == width) {
                x = 0 ;
                y++ ;
                if (y == height) return img ;
            }

            int rgb2 = YUVtoRGB(y2F, uF, vF);
            img.setRGB(x,y, rgb2);

            x++ ;
            if (x == width) {
                x = 0 ;
                y++ ;
                if (y == height) {
                    return img ;
                }
            }

        }

        return null ;
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
