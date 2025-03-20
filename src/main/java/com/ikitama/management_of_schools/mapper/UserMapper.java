package com.ikitama.management_of_schools.mapper;

import com.ikitama.management_of_schools.domain.User;
import com.ikitama.management_of_schools.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toUser(UserEntity userEntity);

    UserEntity fromUser(User user);
}
