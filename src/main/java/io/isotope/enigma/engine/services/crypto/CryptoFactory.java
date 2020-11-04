package io.isotope.enigma.engine.services.crypto;

import java.nio.charset.Charset;
import java.util.Map;

public interface CryptoFactory {

    Decryptor<String> stringDecryptor(Charset charset);
    Encryptor<String> stringEncryptor(Charset charset);

    Decryptor<Map<String, String>> stringMapDecryptor(Charset charset);
    Encryptor<Map<String, String>> stringMapEncryptor(Charset charset);

}
