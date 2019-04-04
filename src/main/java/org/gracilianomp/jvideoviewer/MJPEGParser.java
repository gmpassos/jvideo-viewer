package org.gracilianomp.jvideoviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MJPEGParser extends ImageParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(MJPEGParser.class);

    public MJPEGParser(InputStream in) throws IOException {
        super(in);
    }

    int imageCount = 0 ;

    public BufferedImage parseImage() throws IOException {
        FrameHeader frameHeader = parseFrameHeader();

        byte[] frameBytes = readFramePlaneData(frameHeader.planes[0]);
        if (frameBytes == null) return null;

        imageCount++ ;

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(frameBytes));

            LOGGER.info("Read JPEG Image> frameBytes: {}, imageCount: {}", frameBytes.length, imageCount);

            return image;
        }
        catch (IOException e) {
            return null ;
        }

    }

}
