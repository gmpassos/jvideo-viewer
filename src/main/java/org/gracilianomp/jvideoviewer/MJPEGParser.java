package org.gracilianomp.jvideoviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MJPEGParser extends ImageParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(MJPEGParser.class);

    public MJPEGParser(InputStream in) {
        super(in);
    }

    private ByteArrayOutputStream jpgOut = new ByteArrayOutputStream(8192);

    int prev ;

    @Override
    public BufferedImage parseImage() throws IOException {

        while ( !isClosed() ) {
            int cur = in.read();

            if (cur < 0) {
                close();
                return null ;
            }

            if (prev == 0xFF && cur == 0xD8) {
                jpgOut.reset();
                jpgOut.write(prev);
            }

            jpgOut.write(cur);

            if (prev == 0xFF && cur == 0xD9) {
                byte[] frameBytes = jpgOut.toByteArray();
                jpgOut.reset();

                try {
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(frameBytes));
                    return image;
                }
                catch (IOException e) {
                    //LOGGER.error("Can't decode frame", e);
                }
            }

            prev = cur;
        }

        return null ;
    }

}
