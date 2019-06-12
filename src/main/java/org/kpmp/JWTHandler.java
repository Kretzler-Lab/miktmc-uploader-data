package org.kpmp;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JWTHandler {

	private static final String BEARER = "Bearer ";
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public String getUserIdFromToken(String token) {
		if (token != null) {
			String[] pieces = token.split("\\.");
			if (pieces.length == 3) {
				String base64Payload = pieces[1];
				String jsonString;
				try {
					jsonString = new String(Base64.decodeBase64(base64Payload), "UTF-8");

					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonObject = mapper.readTree(jsonString);
					return jsonObject.get("sub").textValue();
				} catch (Exception e) {
					log.error(ApplicationConstants.LOG_MESSAGE_FORMAT, null, null,
							this.getClass().getSimpleName() + ".getUserIdFromToken",
							"Unable to get UserID from token: " + e.getMessage());
					return "";
				}
			}
		}

		log.warn(ApplicationConstants.LOG_MESSAGE_FORMAT, null, null,
				this.getClass().getSimpleName() + ".getUserIdFromToken", "Unable to get UserID from JWT " + token);
		return "";
	}

	public String getJWTFromHeader(HttpServletRequest request) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader != null) {

			String[] headerPieces = authHeader.split(BEARER);
			if (headerPieces.length == 2) {
				return headerPieces[1];
			}
		}

		log.warn(ApplicationConstants.LOG_MESSAGE_FORMAT, null, null, "JWTHandler.getJWTFromHeader",
				"Authorization Header either missing or malformed");

		return null;
	}

	public String getUserIdFromHeader(HttpServletRequest request) {
		String token = getJWTFromHeader(request);
		return getUserIdFromToken(token);
	}

}
