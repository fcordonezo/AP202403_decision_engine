package co.com.pragma.decision_engine.application.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collections;

@Service
public class JWTAuthorizationFilter extends OncePerRequestFilter {

  private final JWTUtilities jwtUtilities;

  @Autowired
  public JWTAuthorizationFilter(JWTUtilities jwtUtilities) {
    this.jwtUtilities = jwtUtilities;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      unauthorizedResponse(response);
      return;
    }
    String token = header.replace("Bearer ", "");
    try {
      JWTClaimsSet claims = jwtUtilities.parseJwt(token);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    } catch (JOSEException | ParseException e) {
      unauthorizedResponse(response);
      return;
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }

    filterChain.doFilter(request, response);
  }

  private void unauthorizedResponse(HttpServletResponse response) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write("{\"status\": 401, \"error\": \"unauthorized\", \"message\": \"You are not able to see this content!\"}");
    response.getWriter().flush();
    response.getWriter().close();
  }
}
