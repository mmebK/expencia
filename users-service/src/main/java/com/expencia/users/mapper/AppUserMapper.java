package com.expencia.users.mapper;

import com.expencia.users.dto.AppUserDTO;
import com.expencia.users.modal.AppUser;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUserDTO fromAppUser(AppUser appUser);
    AppUser fromAppUserDTO(AppUserDTO AppUserDTO);


}
