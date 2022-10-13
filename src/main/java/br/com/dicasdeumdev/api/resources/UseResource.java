package br.com.dicasdeumdev.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UseResource {

	private static final String ID = "/{id}";

	@Autowired
	private ModelMapper mapper;
	
    @Autowired
    private UserService userService;

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){

        return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDTO.class));
    }
    
    @GetMapping
    public ResponseEntity<List<UserDTO>> findall(){
    	//List<User> list = userService.findAll();
    	//List<UserDTO> listDTO = userService.findAll().stream().map(x -> mapper.map(x, UserDTO.class)).collect(Collectors.toList());
    	return ResponseEntity.ok()
    			.body(userService.findAll()
    					.stream()
    					.map(x -> mapper.map(x, UserDTO.class)).collect(Collectors.toList()));
    }
    
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj){
    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
                .buildAndExpand(userService.create(obj).getId()).toUri();
    	return ResponseEntity.created(uri).build();
    }
    
    @PutMapping(value = ID)
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO obj){
    	obj.setId(id);
    	User newObj = userService.update(obj);
    	
    	return ResponseEntity.ok().body(mapper.map(newObj, UserDTO.class));
    }
    
    @DeleteMapping(value = ID)
    public ResponseEntity<UserDTO> delete(@PathVariable Integer id){
    	userService.delete(id);
    	return ResponseEntity.noContent().build();
    }
}
