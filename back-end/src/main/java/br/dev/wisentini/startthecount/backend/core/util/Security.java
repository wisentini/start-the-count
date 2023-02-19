package br.dev.wisentini.startthecount.backend.core.util;

import org.bouncycastle.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Security {

    private static final String ENCRYPTION_ALGORITHM = "Ed25519";

    private static final String HASHING_FUNCTION = "SHA-512";

    private static final BouncyCastleProvider provider;

    static {
        provider = new BouncyCastleProvider();
        java.security.Security.addProvider(new BouncyCastleProvider());
    }

    public static boolean verifySignature(String content, String signature, byte[] publicKey) throws
        InvalidKeyException,
        SignatureException,
        IOException,
        NoSuchAlgorithmException,
        InvalidKeySpecException
    {
        SubjectPublicKeyInfo keyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed25519), publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyInfo.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM, provider);
        PublicKey key = keyFactory.generatePublic(keySpec);

        Signature sig = Signature.getInstance(ENCRYPTION_ALGORITHM, provider);

        sig.initVerify(key);
        sig.update(Hex.decode(content));

        return sig.verify(Hex.decode(signature));
    }

    public static String sha512(String content) throws
        NoSuchAlgorithmException,
        NoSuchProviderException
    {
        MessageDigest digest = MessageDigest.getInstance(HASHING_FUNCTION, provider);

        digest.reset();
        digest.update(content.getBytes());

        return Hex.toHexString(digest.digest()).toUpperCase();
    }
}
