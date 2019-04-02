package org.gracilianomp.jvideoviewer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageViewer extends JFrame implements ImageListener {

    static public String buildTitle(ImageFormat imageFormat, String host, Dimension dimension) {
        return "ImageViewer[imageFormat: "+ imageFormat +", host: "+ host + ( dimension != null ? ", size: "+ dimension.width +"x"+ dimension.height : "" ) +"]" ;
    }

    final private ImageFormat imageFormat ;
    final private String host ;

    public ImageViewer(ImageFormat imageFormat, String host) throws HeadlessException {
        super( buildTitle(imageFormat, host, null) );
        this.imageFormat = imageFormat ;
        this.host = host ;

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

    public void setImage(BufferedImage image) {
        this.image = image ;

        JLabel imgLabel = new JLabel(new ImageIcon(image));
        imgLabel.setSize( image.getWidth() , image.getHeight() );

        Container contentPane = getContentPane();
        contentPane.removeAll();

        contentPane.add(imgLabel, BorderLayout.CENTER) ;

        contentPane.repaint();

        updateTitle( image.getWidth() , image.getHeight() );
    }


    @Override
    public void onReadImage(BufferedImage image) {
        setImage(image);
    }
}
