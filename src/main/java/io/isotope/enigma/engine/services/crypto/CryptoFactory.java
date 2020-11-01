package io.isotope.enigma.engine.services.crypto;

import java.nio.charset.Charset;

public interface CryptoFactory {

    StringDecryptor stringDecryptor(Charset charset);
    StringEncryptor stringEncryptor(Charset charset);

    MapStringDecryptor stringMapDecryptor(Charset charset);
    MapStringEncryptor stringMapEncryptor(Charset charset);

}
