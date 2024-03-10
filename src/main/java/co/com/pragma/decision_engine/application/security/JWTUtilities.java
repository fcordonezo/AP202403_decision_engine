package co.com.pragma.decision_engine.application.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

public interface JWTUtilities {
  public JWTClaimsSet parseJwt(String jwt) throws ParseException, JOSEException, IOException, NoSuchAlgorithmException, InvalidKeySpecException;
}
