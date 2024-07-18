package com.project.picpay.mapper;

import com.project.picpay.domain.user.User;
import com.project.picpay.dto.UserPostDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserPostDTO userPostDTO);
}
