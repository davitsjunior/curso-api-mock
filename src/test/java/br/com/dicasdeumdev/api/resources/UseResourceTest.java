package br.com.dicasdeumdev.api.resources;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.services.UserService;
import br.com.dicasdeumdev.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class UseResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Davi";
    public static final String EMAIL = "dv@dv.com.br";
    public static final String PASSWORD = "123";

    @InjectMocks
    private UseResource useResource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(userService.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = useResource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void whenFindByIdThenReturnException() {
        when(userService.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado!"));

        try{
            ResponseEntity<UserDTO> response = useResource.findById(ID);
        }catch (Exception e){
            assertNotNull(e);
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Objeto não encontrado!", e.getMessage());
        }
    }

    @Test
    void WhenFindallThenReturnAListOfUserDTO() {
        when(userService.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = useResource.findall();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(0).getClass());
        assertEquals(ID, response.getBody().get(0).getId());
        assertEquals(NAME, response.getBody().get(0).getName());
        assertEquals(EMAIL, response.getBody().get(0).getEmail());
        assertEquals(PASSWORD, response.getBody().get(0).getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(userService.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = useResource.create(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertNotNull(response.getHeaders().get("Location"));
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(userService.update(userDTO)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = useResource.update(ID, userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(userService).delete(anyInt());

        ResponseEntity<UserDTO> response = useResource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).delete(anyInt());

    }
    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}