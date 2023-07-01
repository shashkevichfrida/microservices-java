package com.example.userservice.services.impl;

import com.example.interfaces.dto.UserDto;
import com.example.interfaces.entities.User;
import com.example.interfaces.models.Role;
import com.example.interfaces.repositories.UserRepository;
import com.example.userservice.services.UserService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    public UserDto save(UserDto userDto) {
        String encode = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encode);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
        Long id = user.getId();
        return getById(id);
    }

    @Override
    public UserDto getById(long id) {
        UserDto userDto = new UserDto();
        User userFind = userRepository.findById(id).get();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        userDto = modelMapper.map(userFind, UserDto.class);
        /*User user = userRepository.findByName(getAuthentication().getName());

        if (user.getRole().equals(Role.ADMIN) || user.getId() == id) {
            User userFind = userRepository.findById(id).get();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            userDto = modelMapper.map(userFind, UserDto.class);
        }*/

        return userDto;
    }
    @Override
    public List<UserDto> getAll() {
        /*User user = userRepository.findByName(getAuthentication().getName());

        if (user.getRole().equals(Role.ADMIN)) {
            List<User> users = userRepository.findAll();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            List<UserDto> usersDto = users.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
            return usersDto;
        }



        UserDto userDto = getById(user.getId());
        List<UserDto> users = new ArrayList<>();
        users.add(userDto);
        return users;*/
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = users.stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
        return usersDto;

    }
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        /*User user = userRepository.findByName(getAuthentication().getName());

        if (user.getRole().equals(Role.ADMIN) || user.getId() == id) {
            userRepository.deleteById(id);
        }*/
    }

    @Override
    public void deleteByEntity(Long id) {
        User user = userRepository.findByName(getAuthentication().getName());

        if (user.getRole().equals(Role.ADMIN) || user.getId() == id) {
            Optional<User> userFindById = userRepository.findById(id);
            userRepository.delete(userFindById.get());
        }
    }

    @Override
    public void deleteAll(){
        /*User user = userRepository.findByName(getAuthentication().getName());

        if (user.getRole().equals(Role.ADMIN)){
            userRepository.deleteAll();
        }

        else {
            userRepository.deleteById(user.getId());
        }*/
        userRepository.deleteAll();
    }

    @Override
    public UserDto update(UserDto userDto, Long oldId){
        /*User user = userRepository.findByName(getAuthentication().getName());

        if (user.getRole().equals(Role.ADMIN) || user.getId() == oldId){
            String encode = passwordEncoder.encode(userDto.getPassword());
            User updateUser = userRepository.findById(oldId).get();
            updateUser.setName(userDto.getName());
            updateUser.setRole(userDto.getRole());
            updateUser.setStatus(userDto.getStatus());
            updateUser.setPassword(encode);
            userRepository.save(updateUser);
        }*/
        String encode = passwordEncoder.encode(userDto.getPassword());
        User updateUser = userRepository.findById(oldId).get();
        updateUser.setName(userDto.getName());
        updateUser.setRole(userDto.getRole());
        updateUser.setStatus(userDto.getStatus());
        updateUser.setPassword(encode);
        userRepository.save(updateUser);

        return getById(oldId);
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}
