package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import forms.ActorForm;

@Transactional
@Service
public class AdministratorService {
	
	// Managed Repository ----------------------------------------------
	@Autowired
	private AdministratorRepository adminRepository;
	
	// Supporting Services ---------------------------------------------
	@Autowired
	private ActorService actorService;
	
	// Simple CRUD methods
	public Administrator create(){
		UserAccount account = new UserAccount();
		List<Authority> authorities = new ArrayList<Authority>();
		Authority auth = new Authority();
		
		auth.setAuthority("ADMIN");
		authorities.add(auth);
		account.setAuthorities(authorities);
		
		Administrator result = new Administrator();
		result.setUserAccount(account);
		
		return result;
	}
	
	public void save(Administrator admin){
		Assert.notNull(admin);
		actorService.checkAuhtority("ADMIN");
		
		UserAccount account = admin.getUserAccount();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = account.getPassword();
		
		password = encoder.encodePassword(password, null);
		account.setPassword(password);
		
		adminRepository.save(admin);
	}
	
	public long count(){
		long result = adminRepository.count();
		return result;
	}
	
	// Other Business Methods -----------------------------------------------
	public Administrator findByUserAccount(UserAccount account){
		Administrator result;
		result = adminRepository.findByUserAccount(account);
		return result;
	}
	
	public Administrator findByPrincipal(){
		Administrator result;
		UserAccount account;
		
		account = LoginService.getPrincipal();
		result = findByUserAccount(account);
		
		Assert.notNull(result, "no admin with principal's account");
		return result;
	}
	
	public Administrator reconstruct(ActorForm form){
		Assert.isTrue(form.getPassword().equals(form.getConfirmPassword()), "actor.error.passwordmatch");
		Assert.isTrue(form.getAcceptConditions(), "actor.error.conditionsaccept");
		
		Administrator result = create();
		result.getUserAccount().setUsername(form.getUsername());
		result.getUserAccount().setPassword(form.getPassword());
		result.setName(form.getName());
		result.setSurname(form.getSurname());
		result.setEmail(form.getEmail());
		result.setPhone(form.getPhone());
		
		return result;
	}

	public void flush() { adminRepository.flush(); }
}
