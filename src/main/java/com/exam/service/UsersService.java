package com.exam.service;

import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.exam.dto.UsersDTO;
import com.exam.entity.Users;

@Service
@Transactional
public interface UsersService {
	
	public int idCheck(String id);
	public UsersDTO authenticate(Map<String, String> map);
	public int saveUsers(UsersDTO dto);
	public UsersDTO findById(String id);
	public Users findByUserId(Integer userId);
	public Users modifyUser(Integer userId, UsersDTO.UsersModifyDTO modifyDTO);
	
	public int createCartId(Integer userId);
	public boolean checkPassword(Integer userId, String pw);
	
	public boolean checkEmail(String email1, String email2);
	public String getTmpPw();
	public void findPw(String tmpPw, String email1, String email2);
	
	public int createDeliveryId(Integer userId, UsersDTO usersDTO);
	
	public String findIdByUserId(int userId);
	
	
}
