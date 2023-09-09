package com.github.superbackend.service.mapper;

import com.github.superbackend.dto.MemberDTO;
import com.github.superbackend.repository.member.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    // hyuna Entity => DTO
    @Mapping(source = "email", target = "email")
    //@Mapping(source = "password", target = "password")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "profileImage", target = "profileImage")
    // AboutMe는...?
    MemberDTO memberEntityToDto(Member member);
}
