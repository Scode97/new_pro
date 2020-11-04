
package user.admin.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import user.admin.ws.service.AddressService;
//import user.admin.ws.io.entity.Order;
//import user.admin.ws.service.OrderService;
import user.admin.ws.service.UserService;
import user.admin.ws.shared.dto.AddressDTO;
import user.admin.ws.shared.dto.UserDto;
import user.admin.ws.ui.exceptions.UserServiceException;
import user.admin.ws.ui.model.request.UserDetailsRequestModel;
import user.admin.ws.ui.model.response.AddressesRest;
import user.admin.ws.ui.model.response.ErrorMessages;
import user.admin.ws.ui.model.response.OperationStatusModel;
import user.admin.ws.ui.model.response.RequestOperationName;
import user.admin.ws.ui.model.response.RequestOperationStatus;
import user.admin.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users (make sure don't end this with slash whilst testing
//@CrossOrigin(origins = "*")
public class UserController {
 	
	@Autowired
	UserService userService;

	@Autowired
	AddressService addressService;
	
	// http://localhost:8080/users/public_id_of_user (which is returned as part of headers
	@GetMapping(path="/{id}", produces= {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
			})
	public UserRest getUser(@PathVariable String id) {
		
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);
		
		return returnValue;
	}
	

	@GetMapping(produces= {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE
			})
	public List<UserRest> getUsers(@RequestParam(value="page", defaultValue="0") int page, 
			@RequestParam(value="limit", defaultValue="25") int limit) {
		
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page,limit);
		
		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		return returnValue;
	}

	
	
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		
		UserRest returnValue = new UserRest();
		
		if(userDetails.getFirstName().isEmpty()) 
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());


		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
//		BeanUtils.copyProperties(createdUser, returnValue);
		returnValue = modelMapper.map(createdUser, UserRest.class);
		
		return returnValue;
	}

	
	
	


	@PutMapping(path="/{id}",
	consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
	produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		//provide UserId (which is in path variable)/ no updating email (update only if signed in)
		
		UserRest returnValue = new UserRest();
		
		if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updateUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updateUser, returnValue);
		
		return returnValue;
	}

	

	@DeleteMapping(path="/{id}", produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })	
	public OperationStatusModel deleteUser(@PathVariable String id) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	} 
	
	
	// http://localhost:8080/users/user_id/addresses
		@GetMapping(path="/{id}/addresses", produces= {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE
				})
		public List<AddressesRest> getUserAddresses(@PathVariable String id) {
			
			List<AddressesRest> returnValue = new ArrayList<>();

			List<AddressDTO> addressesDTO = addressService.getAddresses(id);
			
			if (addressesDTO != null && !addressesDTO.isEmpty()) {
				java.lang.reflect.Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
				ModelMapper modelMapper = new ModelMapper();
				returnValue = modelMapper.map(addressesDTO, listType);
			}
			
			return returnValue;
		}
		
		// http://localhost:8080/users/public_id_of_user/addresses/address_id
		@GetMapping(path="/{userId}/addresses/{addressId}", produces= {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE
				})
		public AddressesRest getUserAddress(@PathVariable String userId, 
				@PathVariable String addressId) {
			
			AddressDTO addressesDto = addressService.getAddress(addressId);
			

			ModelMapper modelMapper = new ModelMapper();
			AddressesRest returnValue = modelMapper.map(addressesDto, AddressesRest.class);
			
			// http://localhost:8080/users/<userId>
			Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("user");
			Link userAddressesLink = WebMvcLinkBuilder.linkTo(UserController.class)
					.slash(userId)
					.slash("addresses")
					.withRel("addresses");
			Link selfLink = WebMvcLinkBuilder.linkTo(UserController.class)
					.slash(userId)
					.slash("addresses")
					.slash(addressId)
					.withSelfRel();
			
			returnValue.add(userLink);
			returnValue.add(userAddressesLink);
			returnValue.add(selfLink);
			
			return returnValue;
		
		}
	
}
