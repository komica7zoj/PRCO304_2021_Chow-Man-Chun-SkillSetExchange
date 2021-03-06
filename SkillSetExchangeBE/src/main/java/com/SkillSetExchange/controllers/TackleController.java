package com.SkillSetExchange.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SkillSetExchange.BlockChainUtil;
import com.SkillSetExchange.jwt.JwtUtils;
import com.SkillSetExchange.models.SkillSetInfo;
import com.SkillSetExchange.models.TackleInfo;
import com.SkillSetExchange.models.UserMultichainContent;
import com.SkillSetExchange.models.DAO.CreditUnitDAO;
import com.SkillSetExchange.models.DAO.SearchConditionDAO;
import com.SkillSetExchange.models.DAO.TackleInfoDAO;
import com.SkillSetExchange.repository.MultichainRepository;
import com.SkillSetExchange.services.MultiChainService;
import com.SkillSetExchange.services.SearchSkillSetService;
import com.SkillSetExchange.services.TackleService;
import com.SkillSetExchange.services.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import multichain.command.MultichainException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tackle")
public class TackleController {
	  @Autowired
	  private JwtUtils jwtUtils;

	  @Autowired
	  private UserDetailsServiceImpl userDetailsService;
	
	  @Autowired
	  private MultichainRepository multichainRepository;
	  
	  @Autowired
	  private MultiChainService multiChainService;
	  
	  @Autowired
	  SearchSkillSetService searchSkillSetService;
	
	  @Autowired
	  TackleService tackleService;
		
      @PostMapping("/createTackle")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public int newTackle(@RequestBody Map<String, Object> dao) {
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature .USE_LONG_FOR_INTS, true);
			
			List<TackleInfo>	tackleInfo =  (List<TackleInfo>) objectMapper.convertValue(((Map<String, Object>) dao.get("dao")).get("tackleInfo"), new TypeReference<List<TackleInfo>>() {});
    	 return  tackleService.newTackleIssue(tackleInfo.get(0));
      }
	@PostMapping("/viewskillset")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public SearchConditionDAO ViewSkillSet(@RequestBody Map<String, Object> infoId) {

		SearchConditionDAO	searchConditionDAO =  new SearchConditionDAO();
		searchConditionDAO = searchSkillSetService.getSkillSetByid(((Number)infoId.get("infoId")).longValue());
		searchConditionDAO.userSkillSetDAO = searchSkillSetService.getSkillSetByName((String)infoId.get("username")).skillSetSearchResultDAO;
	        return searchConditionDAO;
	}
	@PostMapping("/viewTackle")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public TackleInfoDAO viewTackle(@RequestBody Map<String, Object> username) {

		TackleInfoDAO	tackleInfoDAO =  new TackleInfoDAO() {{
		tackleInfoByTacklename = tackleService.getTackleInfoByTackleName(username.get("username").toString());
		tackleInfoByusername = tackleService.getTackleInfoByUsername(username.get("username").toString());}};
	        return tackleInfoDAO;
	}
	@PostMapping("/tackleAgree")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public int agreeTackle(@RequestBody Map<String, Object> param) throws MultichainException {
		
//Map<String, Object> params =(Map<String, Object>) param.get("param");
String id = param.get("id").toString();
String username = param.get("username").toString();
String tackleName = param.get("tackleName").toString();
String transId ;
String skillSetName ;
int success = tackleService.tackleAgreement(param);


		
	        return success;
	}
	
	@PostMapping("/objectTackle")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String objectTackle(@RequestBody Map<String, Object> username) {

		TackleInfoDAO	tackleInfoDAO =  new TackleInfoDAO() {{
		tackleInfoByTacklename = tackleService.getTackleInfoByTackleName(username.get("username").toString());
		tackleInfoByusername = tackleService.getTackleInfoByUsername(username.get("username").toString());}};
	        return "";
	}

	
}
