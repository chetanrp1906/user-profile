package com.chetan.userprofile.user.mapper;

import com.chetan.userprofile.user.dto.UserDTO;
import com.chetan.userprofile.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDTO userDTO);
}
