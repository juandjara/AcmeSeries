package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Customer;
import domain.Mark;
import domain.ProducerCompany;
import forms.ActorForm;

@Transactional
@Service
public class CustomerService {
	
	// Managed Repository ----------------------------------
	@Autowired
	private CustomerRepository customerRepository;
	
	// Supporting services ---------------------------------
	@Autowired
	private ActorService actorService;
	
	// Simple CRUD methods
	public Customer findOne(int customerId){
		Customer result;
		result = customerRepository.findOne(customerId);
		return result;
	}
	
	public Collection<Customer> findAll(){
		Collection<Customer> result;
		result = customerRepository.findAll();
		return result;
	}
	
	public Customer create(){
		UserAccount account = new UserAccount();
		List<Authority> authorities = new ArrayList<Authority>();
		Authority auth = new Authority();
		
		auth.setAuthority("CUSTOMER");
		authorities.add(auth);
		account.setAuthorities(authorities);
		
		Customer result = new Customer();
		result.setUserAccount(account);
		result.setComments(new ArrayList<Comment>());
		result.setIsBanned(false);
		result.setMarks(new ArrayList<Mark>());
		result.setRecomendations(new ArrayList<Comment>());
		
		return result;
	}
	
	public void save(Customer customer){
		Assert.notNull(customer);
		
		UserAccount account = customer.getUserAccount();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = account.getPassword();
		
		password = encoder.encodePassword(password, null);
		account.setPassword(password);
		
		customerRepository.save(customer);
	}
	
	public Long count(){
		Long result = customerRepository.count();
		return result;
	}
	
	public void flush(){ customerRepository.flush(); }
	
	// Other business methods
	
	public void toggleBan(int customerId){
		Customer customer = findOne(customerId);
		Assert.notNull(customer, "acme.invalid.identifier");
		actorService.checkAuhtority("ADMIN");
		
		customer.setIsBanned(!customer.getIsBanned());
		customerRepository.save(customer);
	}
	
	public Customer findByUserAccount(UserAccount account){
		Customer result;
		result = customerRepository.findByUserAccount(account);
		return result;
	}
	
	public Customer findByPrincipal(){
		UserAccount account;
		Customer result;
		
		account = LoginService.getPrincipal();
		result = findByUserAccount(account);
		
		Assert.notNull(result, "no customer with principal's account");
		return result;
	}
	
	public Customer reconstruct(ActorForm form){
		Assert.isTrue(form.getPassword().equals(form.getConfirmPassword()), "actor.error.passwordmatch");
		Assert.isTrue(form.getAcceptConditions(), "actor.error.conditionsaccept");
		
		Customer result = create();
		result.getUserAccount().setUsername(form.getUsername());
		result.getUserAccount().setPassword(form.getPassword());
		result.setName(form.getName());
		result.setSurname(form.getSurname());
		result.setEmail(form.getEmail());
		result.setPhone(form.getPhone());
		
		return result;
	}
	
	// Producer Dashboard methods ------------------------------------
	public Long countByContentViewedOfCompany(ProducerCompany company){
		Assert.notNull(company);
		actorService.checkAuhtority("PRODUCER");
		Long result = customerRepository.countByContentViewedOfCompany(company);
		return result;
	}
	
	// Admin Dashboard methods ------------------------------------
	public Collection<Customer> findByMoreCommentsCreated(){
		actorService.checkAuhtority("ADMIN");
		Collection<Customer> result = customerRepository.findByMoreCommentsCreated();
		return result;
	}
	
	public Collection<Customer> findByMoreEpisodesViewed(){
		actorService.checkAuhtority("ADMIN");
		Collection<Customer> result = customerRepository.findByMoreEpisodesViewed();
		return result;
	}
	
	public Collection<Customer> findByMoreTVSeriesViewed(){
		actorService.checkAuhtority("ADMIN");
		Collection<Customer> result = customerRepository.findByMoreTVSeriesViewed();
		return result;
	}
	
	public Collection<Customer> findByMoreFilmsViewed(){
		actorService.checkAuhtority("ADMIN");
		Collection<Customer> result = customerRepository.findByMoreFilmsViewed();
		return result;
	}
}
