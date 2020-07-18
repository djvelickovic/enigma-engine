package io.isotope.enigma.engine.services.crypto;

import io.isotope.enigma.engine.services.exceptions.EngineException;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.io.OutputStream;

public class Engine {

    private static final Integer BUFFER_SIZE = 128;
    private final Cipher cipher;

    public Engine(Cipher cipher) {
        this.cipher = cipher;
    }

    public void process(InputStream is, OutputStream os) {
        byte[] readBuffer = new byte[BUFFER_SIZE];
        byte[] encBuffer = new byte[BUFFER_SIZE];

        try {
            int encoded;
            int read;

            while (true) {
                read = is.read(readBuffer);

                if (read < BUFFER_SIZE) {
                    encoded = cipher.doFinal(readBuffer, 0, read, encBuffer);
                    os.write(encBuffer, 0, encoded);
                    os.flush();
                    break;
                }

                encoded = cipher.update(readBuffer, 0, read, encBuffer);
                os.write(encBuffer, 0, encoded);
            }
        }
        catch (Exception e) {
            throw new EngineException(e);
        }
    }
}
