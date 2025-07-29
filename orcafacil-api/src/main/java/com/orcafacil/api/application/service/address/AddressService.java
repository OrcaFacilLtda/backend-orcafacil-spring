package com.orcafacil.api.application.service.address;

import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.address.AddressRepository;
import com.orcafacil.api.interfaceadapter.request.address.AddressRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(AddressRequest request) {
        Address address = mapToAddress(request, null);
        return addressRepository.save(address);
    }

    public Address getAddressById(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }

    public List<Address> getAddressByUserId(Integer userId) {
        return addressRepository.findByUserId(userId);
    }

    public List<Address> getAddressByProviderId(Integer providerId) {
        return addressRepository.findByProviderId(providerId);
    }

    // Atualizar endereço a partir de AddressRequest e id
    public Address updateAddress(Integer id, AddressRequest request) {
        if (!addressRepository.findById(id).isPresent()) {
            throw new RuntimeException("Endereço não encontrado");
        }

        Address updatedAddress = mapToAddress(request, id);

        return addressRepository.update(updatedAddress);
    }

    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    private Address mapToAddress(AddressRequest request, Integer id) {
        return new Address(
                id,
                request.getStreet(),
                request.getNumber(),
                request.getComplement(),
                request.getNeighborhood(),
                request.getCity(),
                request.getState(),
                request.getZipCode()
        );
    }
}
