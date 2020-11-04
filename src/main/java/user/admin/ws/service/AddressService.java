package user.admin.ws.service;

import java.util.List;

import user.admin.ws.shared.dto.AddressDTO;

public interface AddressService {
	
	List<AddressDTO> getAddresses(String userId);
	AddressDTO getAddress(String addressId);
	
}
