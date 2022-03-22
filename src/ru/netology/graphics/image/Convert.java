package ru.netology.graphics.image;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Convert implements TextGraphicsConverter {

    private int maxHeight;
    private int maxWidth;
    private double maxRatio;
    private Schema schema = new Schema();


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));
        int width = img.getWidth();
        int height = img.getHeight();

        double ratio;
        if (((double) width / height) >= ((double) height / width)) {
            ratio = ((double) height / width);
        } else {
            ratio = ((double) height / width);
        }

        if (ratio > maxRatio) throw new BadImageSizeException(ratio, maxRatio);


        int newHeight = height;
        int newWidth = width;

        if ((height > maxHeight) & (width <= maxWidth)) {
            newHeight = maxHeight;
            double doubleNewWidth = ((double) width) / (((double) height) / (maxHeight));
            newWidth = ((int) doubleNewWidth);
        }

        if ((width > maxWidth) & (height <= maxHeight)) {
            newWidth = maxWidth;
            double doubleNewHeight = ((double) height) / (((double) width) / (maxWidth));
            newHeight = ((int) doubleNewHeight);
        }

        if ((width > maxWidth) & (height > maxHeight)) {
            if ((width - maxWidth) > (height - maxHeight)) {
                newWidth = maxWidth;
                double doubleNewHeight = ((double) height) / (((double) width) / (maxWidth));
                newHeight = ((int) doubleNewHeight);
            } else {
                newHeight = maxHeight;
                double doubleNewWidth = ((double) width) / (((double) height) / (maxHeight));
                newWidth = ((int) doubleNewWidth);

            }
        }


        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();

        StringBuilder stringPicture = new StringBuilder();

        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                stringPicture.append(c);
                stringPicture.append(c);
            }
            stringPicture.append('\n');
        }


        return String.valueOf(stringPicture);
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }


    @Override
    public void setTextColorSchema(TextColorSchema schema) {
            this.schema = (Schema) schema;
    }

}



