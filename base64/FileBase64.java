package base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FileBase64 {
    public static void main(String[] args) throws IOException {
        FileBase64 handler = new FileBase64();
        if (args == null || args.length < 3) {
            handler.showUsage();
            return;
        }
        if (args[0].equals("encode")) {
            String prefix = "";
            if (args.length == 4) {
                prefix = args[3];
            }
            handler.encodeFile(args[1], args[2], prefix);
        } else if (args[0].equals("decode")) {
            String prefix = "";
            if (args.length == 4) {
                prefix = args[3];
            }
            handler.decodeFile(args[1], args[2], prefix);
        } else {
            handler.showUsage();
            return;
        }
    }

    private void showUsage() {
        System.out.println("Usage: java base64.FileBase64 <encode|decode> <input file> <output file> [prefix]");
    }

    private void encodeFile(String inFile, String outFile, String prefix) throws IOException {
        final Base64.Encoder encoder = Base64.getEncoder();
        FileInputStream in = new FileInputStream(inFile);
        byte[] bytes = in.readAllBytes();
        in.close();
        String encodedValue = encoder.encodeToString(bytes);
        FileWriter writer = new FileWriter(outFile);
        try {
            writer.write(prefix);
            writer.write(encodedValue);
            writer.flush();
        } finally {
            writer.close();
        }
    }

    private void decodeFile(String inFile, String outFile, String prefix) throws IOException {
        final Base64.Decoder decoder = Base64.getDecoder();
        String encoded = new String(Files.readAllBytes(Paths.get(inFile)));
        encoded = encoded.substring(prefix.length());
        byte[] bytes = decoder.decode(encoded);
        FileOutputStream out = new FileOutputStream(new File(outFile));
        try {
            out.write(bytes);
            out.flush();
        } finally {
            out.close();
        }
    }
}