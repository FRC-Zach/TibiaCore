package com.acuity.tibia.core;

import SevenZip.Compression.LZMA.Decoder;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Zach on 9/6/2017.
 */
public class SpriteDumper {


    public SpriteDumper() throws IOException {
        File sprite = new File("C:\\Users\\S3108772\\IdeaProjects\\TibiaCore\\src\\main\\resources\\sprites-00f0659e1bf675d5b0a4d0400923defc390b80885d635f90733d1b0ee73e8b57.bmp.lzma");

        Decoder decoder = new Decoder();

        FileInputStream fileInputStream = new FileInputStream(sprite);
        fileInputStream.skip(6);

        while ((fileInputStream.read() & 0x80) == 0x80) {
            System.out.println("Skipping");
        }

        byte[] properties = new byte[5];
        fileInputStream.read(properties);
        decoder.SetDecoderProperties(properties);

        fileInputStream.skip(8);

        ByteOutputStream buffer = new ByteOutputStream();
        decoder.Code(fileInputStream, buffer, -1);
        buffer.flush();

        byte[] bytes = buffer.getBytes();

        BufferedImage read = ImageIO.read(new ByteInputStream(bytes, bytes.length));
        ImageIO.write(read, "png", new File("test.png"));
    }

    public static void main(String[] args) {
        try {
            new SpriteDumper();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
