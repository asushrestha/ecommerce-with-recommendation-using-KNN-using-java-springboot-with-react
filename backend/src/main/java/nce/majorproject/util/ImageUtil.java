package nce.majorproject.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
public class ImageUtil {



    public static byte[] compressBytes(byte[] data) {

        Deflater deflater = new Deflater();

        deflater.setInput(data);

        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];

        while (!deflater.finished()) {

            int count = deflater.deflate(buffer);

            outputStream.write(buffer, 0, count);

        }

        try {

            outputStream.close();

        } catch (IOException e) {

        }

        log.info("Compressed Image Byte Size ::{} " + outputStream.toByteArray().length);

        return outputStream.toByteArray();

    }

    // uncompress the image bytes before returning it to the angular application

    public static byte[] decompressBytes(byte[] data) {

        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];
        try {

            while (!inflater.finished()) {

                int count = inflater.inflate(buffer);

                outputStream.write(buffer, 0, count);

            }

            outputStream.close();

        } catch (IOException ioe) {

        } catch (DataFormatException e) {

        }

        return outputStream.toByteArray();

    }
}
