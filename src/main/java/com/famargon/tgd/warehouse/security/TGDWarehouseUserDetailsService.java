package com.famargon.tgd.warehouse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.famargon.tgd.warehouse.datamodel.TGDUser;
import com.famargon.tgd.warehouse.datamodel.repository.TGDUserRepository;

@Service
public class TGDWarehouseUserDetailsService implements UserDetailsService {

	@Autowired
	private TGDUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TGDUser tgdUser = userRepository.findByUsername(username);
		if(tgdUser==null){
			throw new UsernameNotFoundException("Not found username with name "+username);
		}
		return new User(username, tgdUser.getPwd(), AuthorityUtils.createAuthorityList(tgdUser.getRole()));
	}
}
