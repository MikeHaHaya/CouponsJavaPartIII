package app.core.controllers;

import app.core.exceptions.ControllerException;
import app.core.security.JwtGenerate;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientController {

    @Autowired
    protected JwtGenerate jwtUtil;

    public abstract String login(String email, String password);

    public void jwtValidation(String jwt) throws ControllerException {

        try {
            jwtUtil.extractAllClaims(jwt);

        } catch (ExpiredJwtException e) {
            throw new ControllerException("Timed out, please log in again. ");
        }
    }

    public int getIdFromJwt(String jwt) throws ControllerException {

        try {
            return Integer.parseInt(jwtUtil.extractID(jwt));

        } catch (ExpiredJwtException e) {
            throw new ControllerException("Timed out, please log in again.");
        }
    }

}
