package com.example.user_service.service;

import com.example.user_service.dto.CreateUserDTO;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.entity.User;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.util.TCKNValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDTO);
    }
    
    public Optional<UserDTO> getUserByTckn(String tckn) {
        String validatedTCKN = TCKNValidator.validateAndFormat(tckn);
        if (validatedTCKN == null) {
            return Optional.empty();
        }
        return userRepository.findByTckn(validatedTCKN)
                .map(this::convertToDTO);
    }
    
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        if (userRepository.existsByPhoneNumber(createUserDTO.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }
        
        // TCKN doğrulaması
        String validatedTCKN = TCKNValidator.validateAndFormat(createUserDTO.getTckn());
        if (validatedTCKN == null) {
            throw new RuntimeException("Invalid TCKN");
        }
        
        if (userRepository.existsByTckn(validatedTCKN)) {
            throw new RuntimeException("TCKN already exists");
        }
        
        User user = new User();
        user.setEmail(createUserDTO.getEmail());
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setPassword(createUserDTO.getPassword()); // In real app, encode this
        user.setPhoneNumber(createUserDTO.getPhoneNumber());
        user.setTckn(validatedTCKN);
        user.setAddress(createUserDTO.getAddress());
        
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }
    
    public Optional<UserDTO> updateUser(Long id, CreateUserDTO updateUserDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    // TCKN doğrulaması
                    String validatedTCKN = TCKNValidator.validateAndFormat(updateUserDTO.getTckn());
                    if (validatedTCKN == null) {
                        throw new RuntimeException("Invalid TCKN");
                    }
                    
                    // Mevcut kullanıcının TCKN'i değişiyorsa ve yeni TCKN başka birinde varsa hata ver
                    if (!user.getTckn().equals(validatedTCKN) && userRepository.existsByTckn(validatedTCKN)) {
                        throw new RuntimeException("TCKN already exists");
                    }
                    
                    user.setEmail(updateUserDTO.getEmail());
                    user.setFirstName(updateUserDTO.getFirstName());
                    user.setLastName(updateUserDTO.getLastName());
                    user.setPhoneNumber(updateUserDTO.getPhoneNumber());
                    user.setTckn(validatedTCKN);
                    user.setAddress(updateUserDTO.getAddress());
                    return convertToDTO(userRepository.save(user));
                });
    }
    
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setTckn(user.getTckn());
        dto.setAddress(user.getAddress());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setActive(user.getActive());
        return dto;
    }
}
