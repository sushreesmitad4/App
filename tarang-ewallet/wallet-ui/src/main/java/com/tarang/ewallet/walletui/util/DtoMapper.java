package com.tarang.ewallet.walletui.util;

import java.util.ArrayList;
import java.util.List;

import com.tarang.ewallet.dto.RoleDto;
import com.tarang.ewallet.model.Role;



/**
* @author  : prasadj
* @date    : Oct 10, 2012
* @time    : 8:55:45 AM
* @version : 1.0.0
* @comments: helper class for mapping model objects to dto objects
*
*/
public class DtoMapper {

	public static RoleDto map(Role role) {
		RoleDto dto = new RoleDto();
		dto.setId(role.getId());
		dto.setName(role.getName());
		dto.setDescription(role.getDescription());
		return dto;
	}
	
	public static List<RoleDto> map(List<Role> roles) {
		List<RoleDto> dtos = new ArrayList<RoleDto>();
		
		for( int i = 0; i < roles.size(); i++ ){
			dtos.add(map(roles.get(i)));
		}
		return dtos;
	}
	
}
