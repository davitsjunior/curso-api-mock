package br.com.dicasdeumdev.api.resources.exceptions;

import br.com.dicasdeumdev.api.services.exceptions.DataIntegrityViolationException;
import br.com.dicasdeumdev.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    @InjectMocks
    ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundThenReturnResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler.objectNotFound(
                new ObjectNotFoundException("Objeto não encontrado!"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Objeto não encontrado!", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void whenDataIntegrityViolationExceptionReturnResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler.dataIntegratyViolationException(
                new DataIntegrityViolationException("E-mail já cadastrado!"),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("E-mail já cadastrado!", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}