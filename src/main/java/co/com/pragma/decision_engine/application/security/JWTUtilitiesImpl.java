package co.com.pragma.decision_engine.application.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;

@Service
public class JWTUtilitiesImpl implements JWTUtilities{

  @Value("classpath:cert/public_key.pem")
  private Resource publicKeyResource;

  @Override
  public JWTClaimsSet parseJwt(String jwt) throws JOSEException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException {
    PublicKey publicKey = loadPublicKey();
    SignedJWT signedJwt = SignedJWT.parse(jwt);
    JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
    if (!signedJwt.verify(verifier)) {
      throw new JOSEException("Invalid signature");
    }
    signedJwt.verify(verifier);
    signedJwt.getPayload();
    return signedJwt.getJWTClaimsSet();
  }

  private PublicKey loadPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] bytesKey = Files.readAllBytes(Paths.get(publicKeyResource.getURI()));
    String publicKeyPEM = new String(bytesKey, StandardCharsets.UTF_8)
      .replace("-----BEGIN PUBLIC KEY-----", "")
      .replace("-----END PUBLIC KEY-----", "")
      .replaceAll("\\s", "");
    byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
  }
}
