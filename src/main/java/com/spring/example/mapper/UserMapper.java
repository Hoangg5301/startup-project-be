package com.spring.example.mapper;

import com.spring.example.common.role.SystemRole;
import com.spring.example.dto.request.user.CreateUserRequest;
import com.spring.example.dto.request.user.UpdateUserRequest;
import com.spring.example.dto.response.user.CreateUserResponse;
import com.spring.example.dto.response.user.GetUserResponse;
import com.spring.example.entity.UserEntity;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "roles", expression = "java(getDefaultRoles())")
    @Mapping(target = "active", constant = "true")
    UserEntity toEntity(CreateUserRequest request);

    CreateUserResponse toCreateResponse(UserEntity entity);

    GetUserResponse toGetResponse(UserEntity entity);

    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "password", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateUserRequest request, @MappingTarget UserEntity entity);

    default Set<SystemRole> getDefaultRoles() {
        return Set.of(SystemRole.USER);
    }
}
