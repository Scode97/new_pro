package user.admin.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.admin.ws.io.entity.AddressEntity;
import user.admin.ws.io.entity.UserEntity;
import user.admin.ws.io.repositories.AddressRepository;
import user.admin.ws.io.repositories.UserRepository;
import user.admin.ws.shared.dto.AddressDTO;

import user.admin.ws.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;
	
	public List<AddressDTO> getAddresses(String userId){
		
		List<AddressDTO> returnValue = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		
	    UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null) return returnValue;
        
        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for(AddressEntity addressEntity:addresses)
        {
            returnValue.add( modelMapper.map(addressEntity, AddressDTO.class) );
        }
		
		return returnValue;
	}

	@Override
	public AddressDTO getAddress(String addressId) {
		
		AddressDTO returnValue = null;
		
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		
		if(addressEntity != null) {
			returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);
		}
		return returnValue;
		
	}
}
