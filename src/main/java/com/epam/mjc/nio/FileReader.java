package com.epam.mjc.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        // 1. Reading file data into string
        String textFromFile = "";

        try(RandomAccessFile aFile = new RandomAccessFile(file, "r");

            FileChannel inChannel = aFile.getChannel();) {
            long fileSize = inChannel.size();

            //Create buffer of the file size
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);

            buffer.flip();

            // Verify the file content
            for (int i = 0; i < fileSize; i++) {
                textFromFile += (char) buffer.get();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Parse this string for key-value pairs
        String[] arrOfText = textFromFile.split("\n", 4);

        String name = "";
        Integer age = 0;
        String email = "";
        Long phone = 0L;

        try {

            name = arrOfText[0].substring(6);
            age = Integer.parseInt(arrOfText[1].substring(5));
            email = arrOfText[2].substring(7);
            phone =  Long.parseLong(arrOfText[3].substring(7, arrOfText[3].length() - 1));

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        return new Profile(name, age, email, phone);
    }
}
