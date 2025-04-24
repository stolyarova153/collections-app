package ru.spbpu.collections.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.spbpu.collections.model.response.ResponseDto;
import ru.spbpu.collections.model.response.enums.ResponseMessage;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {

        log.error("Unauthorized error: {}", authException.getMessage());

        final ResponseDto responseDto = ResponseDto
                .builder()
                .message(ResponseMessage.FAILED)
                .details(authException.getMessage())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final OutputStream responseStream = response.getOutputStream();
        objectMapper.writeValue(responseStream, responseDto);

        responseStream.flush();
    }
}