package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProducerCompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Multimedia;
import domain.ProducerCompany;
import forms.ProducerCompanyForm;

@Transactional
@Service
public class ProducerCompanyService {

	// Managed repository --------------------------------------------------
	@Autowired
	private ProducerCompanyRepository producerRepository;
	
	// Supporting services -------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private MultimediaService multimediaService;
	
	// Simple CRUD methods -------------------------------------------------
	public ProducerCompany create(){
		UserAccount account = new UserAccount();
		List<Authority> authorities = new ArrayList<Authority>();
		Authority auth = new Authority();
		
		auth.setAuthority("PRODUCER");
		authorities.add(auth);
		account.setAuthorities(authorities);
		
		ProducerCompany result = new ProducerCompany();
		result.setUserAccount(account);
		result.setMultimedias(new ArrayList<Multimedia>());
		
		return result;
	}
	
	public void save(ProducerCompany producer){
		Assert.notNull(producer);
		
		UserAccount account = producer.getUserAccount();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = account.getPassword();
		
		password = encoder.encodePassword(password, null);
		account.setPassword(password);
		
		producerRepository.saveAndFlush(producer);
	}
	
	public long count(){
		long result = producerRepository.count();
		return result;
	}
	// Other business methods ----------------------------------------------
	public ProducerCompany findByUserAccount(UserAccount account){
		ProducerCompany result;
		result = producerRepository.findByUserAccount(account);
		return result;
	}
	
	public ProducerCompany findByPrincipal(){
		UserAccount account;
		ProducerCompany result;
		
		account = LoginService.getPrincipal();
		result = findByUserAccount(account);
		
		Assert.notNull(result, "no producer with principal's account");
		return result;
	}
	
	public ProducerCompany reconstruct(ProducerCompanyForm form){
		Assert.isTrue(form.getPassword().equals(form.getConfirmPassword()), "actor.error.passwordmatch");
		Assert.isTrue(form.getAcceptConditions(), "actor.error.conditionsaccept");
		
		ProducerCompany result = create();
		result.getUserAccount().setUsername(form.getUsername());
		result.getUserAccount().setPassword(form.getPassword());
		result.setName(form.getName());
		result.setSurname(form.getSurname());
		result.setEmail(form.getEmail());
		result.setPhone(form.getPhone());
		result.setFundationDate(form.getFundationDate());
		result.setWebpage(form.getWebpage());
		
		return result;
	}
	
	// Admin Dashboard methods ---------------------------------------------------
	public Collection<ProducerCompany> findByMoreMultimediaCreated(){
		actorService.checkAuhtority("ADMIN");
		Collection<ProducerCompany> result = producerRepository.findByMoreMultimediaCreated();
		return result;
	}
	
	public Collection<ProducerCompany> findByBestRatedMultimedia(){
		actorService.checkAuhtority("ADMIN");
		Collection<Object[]> multimediaPlusAverageRating =
				multimediaService.findOrderedByAverageScore();
		Object[] first = (Object[]) multimediaPlusAverageRating.toArray()[0];
		Double betterAverageRating = (Double) first[1];
		List<ProducerCompany> result = new ArrayList<ProducerCompany>();
		for(Object[] elem : multimediaPlusAverageRating){
			if(elem[1] == betterAverageRating){
				Multimedia mu = (Multimedia) elem[0];
				result.add(mu.getProducerCompany());
			}
		}
		return result;
	}
	
	public Collection<ProducerCompany> findByWorstRatedMultimedia(){
		actorService.checkAuhtority("ADMIN");
		Collection<Object[]> multimediaPlusAverageRating =
				multimediaService.findOrderedByAverageScore();
		int lastIndex = multimediaPlusAverageRating.size() - 1;
		Object[] last = (Object[]) multimediaPlusAverageRating.toArray()[lastIndex];
		Double worstAverageRating = (Double) last[1];
		List<ProducerCompany> result = new ArrayList<ProducerCompany>();
		for(Object[] elem : multimediaPlusAverageRating){
			if(elem[1] == worstAverageRating){
				Multimedia mu = (Multimedia) elem[0];
				result.add(mu.getProducerCompany());
			}
		}
		return result;
	}
}
