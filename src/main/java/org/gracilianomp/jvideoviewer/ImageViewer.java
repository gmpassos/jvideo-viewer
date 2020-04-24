package org.gracilianomp.jvideoviewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageViewer extends JFrame implements ImageListener  {

    static public String buildTitle(ImageFormat imageFormat, String host, Dimension dimension) {
        return "ImageViewer[imageFormat: "+ imageFormat +", host: "+ host + ( dimension != null ? ", size: "+ dimension.width +"x"+ dimension.height : "" ) +"]" ;
    }

    static final public File DEFAULT_SAVE_DIRECTORY = new File("/tmp") ;

    final private long sessionID = System.currentTimeMillis() ;

    final private ImageFormat imageFormat ;
    final private String host ;
    final private File saveDirectory ;

    public ImageViewer(ImageFormat imageFormat, String host) throws HeadlessException {
        this(imageFormat, host, DEFAULT_SAVE_DIRECTORY) ;
    }

    public ImageViewer(ImageFormat imageFormat, String host, File saveDirectory) throws HeadlessException {
        super( buildTitle(imageFormat, host, null) );

        if (saveDirectory == null) saveDirectory = DEFAULT_SAVE_DIRECTORY ;

        this.imageFormat = imageFormat ;
        this.host = host ;
        this.saveDirectory = saveDirectory ;

        build() ;

        setSize(700, 520);

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        setVisible(true);
    }

    private void build() {

        Container contentPane = getContentPane();

        contentPane.setLayout( new BorderLayout() );

        contentPane.add( new Label("Reading image...") ) ;

    }

    private Dimension imageDimension ;

    private void updateTitle( int width , int height ) {
        Dimension dimension = new Dimension(width, height);

        if ( imageDimension == null || !imageDimension.equals(dimension) ) {
            String title = buildTitle(imageFormat, host, dimension);

            setTitle(title);
            setSize( dimension.width+10 , dimension.height+40 );

            imageDimension = dimension ;
        }

    }

    private BufferedImage image ;
    private int imageCounter = 0 ;

    public void setImage(BufferedImage image) {
        this.image = image ;
        this.imageCounter++ ;

        JLabel imgLabel = new JLabel(new ImageIcon(image));
        imgLabel.setSize( image.getWidth() , image.getHeight() );

        imgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onImageClicked();
            }
        });

        Container contentPane = getContentPane();
        contentPane.removeAll();

        contentPane.add(imgLabel, BorderLayout.CENTER) ;

        contentPane.repaint();

        updateTitle( image.getWidth() , image.getHeight() );
    }

    private void onImageClicked() {
        BufferedImage image = this.image ;
        int imageCounter = this.imageCounter ;
        if (image == null) return ;

        File imgFile = new File(saveDirectory, "jvideo-viewer--image--"+ sessionID +"--"+ imageCounter+".png") ;

        try {
            ImageIO.write(image , "PNG", imgFile) ;
            System.out.println("-- Saved image: "+ imgFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReadImage(BufferedImage image) {
        setImage(image);
    }

}
